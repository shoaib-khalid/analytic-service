/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service;

import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;
import com.kalsym.analytic.service.model.repository.CustomerActivitySummaryRepository;
import com.kalsym.analytic.service.model.CustomerActivitySummary;
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
     
    @Value("${generate.summary.scheduler.enabled:false}")
    private boolean isEnabled;
    
     @Value("${generate.summary.scheduler.cron:0 0 1 * * ?}")
    private String cronTime;
   
    @Scheduled(cron = "${generate.summary.scheduler.cron}")
    public void generateSummary() throws Exception {
        if (isEnabled) {
            String logprefix = "GenerateSummaryScheduler"; 
            String date = com.kalsym.analytic.service.utils.DateTimeUtil.yesterdayDate();
            List<Object[]> dataList = customerActivityRepository.getCountSummary(date);
            for (int i=0;i<dataList.size();i++) {
                Object[] data = dataList.get(i);
                int totalCount = (int)data[0];
                int totalUnique = (int)data[1];
                String dt = (String)data[2];
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
           
        }
    }
}

