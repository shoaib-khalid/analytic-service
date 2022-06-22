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
    
     @Value("${generate.summary.scheduler.cron:0 0 1 * * ?}")
    private String cronTime;
   
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
            for (int i=0;i<userList.size();i++) {
                Object[] data = userList.get(i);
                int totalUnique = ((BigInteger)data[0]).intValue();
                Date dt = (Date)data[1];
                String storeId = (String)data[2];
                TotalUniqueUser summaryUser = new TotalUniqueUser();
                summaryUser.setDt(dt);
                summaryUser.setTotalUnique(totalUnique);
                summaryUser.setStoreId(storeId);
                try {
                    totalUniqueUserRepository.save(summaryUser);
                } catch (Exception ex) {
                    Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Exception for date:"+date, ex);
                }
            }
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Completed generate summary for date:"+date);                    
        }
    }
}

