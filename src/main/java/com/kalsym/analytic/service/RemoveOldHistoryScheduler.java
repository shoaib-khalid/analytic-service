/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service;

import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;
import com.kalsym.analytic.service.utils.Logger;

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
public class RemoveOldHistoryScheduler {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
    
    @Value("${remove.history.scheduler.enabled:false}")
    private boolean isEnabled;
   
    @Scheduled(fixedRate = 600000)
    public void removeHistory() throws Exception {
        if (isEnabled) {
            String logprefix = "RemoveOldHistoryScheduler"; 
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Starting remove history...");        
            customerActivityRepository.RemoveOldRecord();
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Remove history completed");        
        }
    }
}

