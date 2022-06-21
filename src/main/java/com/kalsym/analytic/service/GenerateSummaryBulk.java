/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service;

import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;
import com.kalsym.analytic.service.model.repository.CustomerActivitySummaryRepository;
import com.kalsym.analytic.service.model.CustomerActivitySummary;
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
public class GenerateSummaryBulk {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
    
    @Autowired
    CustomerActivitySummaryRepository customerActivitySummaryRepository;
     
    @Value("${generate.summary.scheduler.enabled:false}")
    private boolean isEnabled;
    
     @Value("${generate.summary.scheduler.cron:0 0 1 * * ?}")
    private String cronTime;
   
    @Scheduled(cron = "${generate.summary.scheduler.bulk}")
    public void generateSummary() throws Exception {
        if (isEnabled) {
            String logprefix = "GenerateSummaryScheduler"; 
            String[] dateList = new String[50];
            dateList[0]="2022-06-01";
            dateList[1]="2022-06-02";
            dateList[2]="2022-06-03";
            dateList[3]="2022-06-04";
            dateList[4]="2022-06-05";
            dateList[5]="2022-06-06";
            dateList[6]="2022-06-07";
            dateList[7]="2022-06-08";
            dateList[8]="2022-06-09";
            dateList[9]="2022-06-10";
            dateList[10]="2022-06-11";
            dateList[11]="2022-06-12";
            dateList[12]="2022-06-13";
            dateList[13]="2022-06-14";
            dateList[14]="2022-06-15";
            dateList[15]="2022-06-16";
            dateList[16]="2022-06-17";
            dateList[17]="2022-06-18";
            dateList[18]="2022-06-19";
            dateList[19]="2022-05-01";
            dateList[20]="2022-05-02";
            dateList[21]="2022-05-03";
            dateList[22]="2022-05-04";
            dateList[23]="2022-05-05";
            dateList[24]="2022-05-06";
            dateList[25]="2022-05-07";
            dateList[26]="2022-05-08";
            dateList[27]="2022-05-09";
            dateList[28]="2022-05-10";
            dateList[29]="2022-05-11";
            dateList[30]="2022-05-12";
            dateList[31]="2022-05-13";
            dateList[32]="2022-05-14";
            dateList[33]="2022-05-15";
            dateList[34]="2022-05-16";
            dateList[35]="2022-05-17";
            dateList[36]="2022-05-18";
            dateList[37]="2022-05-19";
            dateList[38]="2022-05-20";
            dateList[39]="2022-05-21";
            dateList[40]="2022-05-22";
            dateList[41]="2022-05-23";
            dateList[42]="2022-05-24";
            dateList[43]="2022-05-25";
            dateList[44]="2022-05-26";
            dateList[45]="2022-05-27";
            dateList[46]="2022-05-28";
            dateList[47]="2022-05-29";
            dateList[48]="2022-05-30";
            dateList[49]="2022-05-31";
            for (int x=0;x<dateList.length;x++) {
                String date = dateList[x];
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
                    customerActivitySummaryRepository.save(summary);
                }
                Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Completed generate summary for date:"+date);                    
            }
        }
    }
}
