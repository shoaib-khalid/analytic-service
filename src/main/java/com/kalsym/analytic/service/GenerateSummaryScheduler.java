/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service;

import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;
import com.kalsym.analytic.service.model.repository.CustomerActivitySummaryRepository;
import com.kalsym.analytic.service.model.repository.TotalUniqueUserRepository;
import com.kalsym.analytic.service.model.CustomerActivitySummary;
import com.kalsym.analytic.service.model.TotalUniqueUser;
import com.kalsym.analytic.service.utils.Logger;
import java.util.List;
import java.util.Date;
import java.util.Arrays;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author taufik
 */

@Component
public class GenerateSummaryScheduler {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
    
    @Autowired
    CustomerActivitySummaryRepository customerActivitySummaryRepository;
    
    @Autowired
    TotalUniqueUserRepository totalUniqueUserRepository;
     
    @Value("${generate.summary.scheduler.enabled:false}")
    private boolean isEnabled;
   
    @Scheduled(cron = "${generate.summary.scheduler.cron}")
    public void generateSummary() throws Exception {
        if (isEnabled) {
            String logprefix = "GenerateSummaryScheduler"; 
            String date = com.kalsym.analytic.service.utils.DateTimeUtil.yesterdayDate();
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Starting generate summary for date:"+date);                    
            List<Object[]> dataList = customerActivityRepository.getCountSummary(date);
            for (int i=0;i<dataList.size();i++) {
                Object[] data = dataList.get(i);
                int totalCount = ((BigInteger)data[0]).intValue();
                int totalUnique = ((BigInteger)data[1]).intValue();
                Date dt = (Date)data[2];
                String storeId = (String)data[3];
                String browserType = (String)data[4];
                String deviceModel = (String)data[5];
                String os = (String)data[6];
                String pageVisited = (String)data[7];
                CustomerActivitySummary summary = new CustomerActivitySummary();
                summary.setDt(dt);
                summary.setTotalCount(totalCount);
                summary.setTotalUniqueUser(totalUnique);
                summary.setStoreId(storeId);
                summary.setBrowser(browserType);
                summary.setDevice(deviceModel);
                summary.setOs(os);
                summary.setPage(pageVisited);
                try {
                    customerActivitySummaryRepository.save(summary);
                } catch (Exception ex) {
                    Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Exception for date:"+date, ex);
                }
            }
            
            List<Object[]> userList = customerActivityRepository.getUniqueUserSummary(date);
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "UserList size:"+userList.size());                    
            for (int i=0;i<userList.size();i++) {
                Object[] data = userList.get(i);
                int totalUnique = ((BigInteger)data[0]).intValue();
                Date dt = (Date)data[1];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dtString = sdf.format(dt);
                String storeId = (String)data[2];
                
                List<Object[]> existingRecord  = totalUniqueUserRepository.checkExistingRecord(dtString, storeId);
                int totalRecord = 0;
                if (existingRecord.size()>0) {
                    Object[] dataExisting = existingRecord.get(i);
                    totalRecord = (Integer)dataExisting[0];
                }
                Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Existing record for date:"+dt+" storeId:"+storeId+" = "+totalRecord);
                
                try {
                    if (totalRecord==0) {
                        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Create new record for date:"+dt+" storeId:"+storeId);
                        TotalUniqueUser summaryUser = new TotalUniqueUser();
                        summaryUser.setDt(dt);
                        summaryUser.setTotalUnique(totalUnique);
                        summaryUser.setStoreId(storeId);
                        totalUniqueUserRepository.save(summaryUser);
                    } else {
                        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Update existing record for date:"+dt+" storeId:"+storeId);
                        totalUniqueUserRepository.updateTotalUniqueCustomer(dtString, storeId, totalUnique);
                    }                                
                } catch (Exception ex) {
                    Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Exception for date:"+date, ex);
                }
            }
            
            List<Object[]> guestList = customerActivityRepository.getUniqueGuestummary(date);
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "guestList size:"+guestList.size());                    
            for (int i=0;i<guestList.size();i++) {
                Object[] data = guestList.get(i);
                int totalUniqueGuest = ((BigInteger)data[0]).intValue();
                Date dt = (Date)data[1];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dtString = sdf.format(dt);
                String storeId = (String)data[2]; 
                
                List<Object[]> existingRecord  = totalUniqueUserRepository.checkExistingRecord(dtString, storeId);
                int totalRecord = 0;
                if (existingRecord.size()>0) {
                    Object[] dataExisting = existingRecord.get(i);
                    totalRecord = (Integer)dataExisting[0];
                }
                Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Existing record for guest for date:"+dt+" storeId:"+storeId+" = "+totalRecord);
                
                try {
                    if (totalRecord==0) {
                        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Create new record for guest for date:"+dt+" storeId:"+storeId);
                        TotalUniqueUser summaryUser = new TotalUniqueUser();
                        summaryUser.setDt(dt);
                        summaryUser.setTotalUniqueGuest(totalUniqueGuest);
                        summaryUser.setStoreId(storeId);
                        totalUniqueUserRepository.save(summaryUser);
                    } else {
                        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Update existing record for guest for date:"+dt+" storeId:"+storeId);
                        totalUniqueUserRepository.updateTotalUniqueGuest(dtString, storeId, totalUniqueGuest);
                    }
                } catch (Exception ex) {
                    Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Exception for date:"+date, ex);
                }
            }
            
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Completed generate summary for date:"+date);                    
        }
    }
}

