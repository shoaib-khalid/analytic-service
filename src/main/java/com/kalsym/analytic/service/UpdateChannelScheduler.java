/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service;

import java.util.List;
import java.util.Date;
import java.util.Arrays;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;
import com.kalsym.analytic.service.model.repository.CustomerSessionRepository;
import com.kalsym.analytic.service.utils.Logger;
import com.kalsym.analytic.service.service.GoogleLocationService;
import com.kalsym.analytic.service.model.Address;
import com.kalsym.analytic.service.model.CustomerSession;
import java.math.BigInteger;

/**
 *
 * @author taufik
 */

@Component
public class UpdateChannelScheduler {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
   
    @Autowired
    GoogleLocationService googleLocationService;
    
    @Value("${update.channel.scheduler.enabled:true}")
    private boolean isEnabled;
       
    @Scheduled(fixedRate = 300000)
    public void updateChannel() throws Exception {
        String logprefix = "UpdateChannelScheduler()";
        if (isEnabled) { 
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Start Update");
            customerActivityRepository.UpdateGoogleChannel();
            customerActivityRepository.UpdateFacebookChannel();
        }   Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Finish Update");
    }
}

