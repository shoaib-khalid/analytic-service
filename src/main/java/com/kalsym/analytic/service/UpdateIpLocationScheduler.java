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

/**
 *
 * @author taufik
 */

@Component
public class UpdateIpLocationScheduler {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
    
    @Value("${update.location.scheduler.enabled:false}")
    private boolean isEnabled;
   
    @Scheduled(fixedRate = 300000)
    public void updateUserLocation() throws Exception {
        if (isEnabled) {            
        }
    }
}

