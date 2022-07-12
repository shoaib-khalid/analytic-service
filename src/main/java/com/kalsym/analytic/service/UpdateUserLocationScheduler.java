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
public class UpdateUserLocationScheduler {
    
    @Autowired
    CustomerActivityRepository customerActivityRepository;
    
    @Autowired
    CustomerSessionRepository customerSessionRepository;
    
    @Autowired
    GoogleLocationService googleLocationService;
    
    @Value("${update.location.scheduler.enabled:false}")
    private boolean isEnabled;
   
    @Scheduled(fixedRate = 300000)
    public void updateUserLocation() throws Exception {
        String logprefix = "UpdateUserLocationScheduler()";
        if (isEnabled) { 
            List<Object[]> sessionList = customerActivityRepository.getUnknownUserLocation();
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "sessionList:  " + sessionList.size());
            
            for (int i=0;i<sessionList.size();i++) {
              Object[] data = sessionList.get(i);
              
              String sessionId = (String)data[0];
              String latitude = (String)data[1];
              String longitude = (String)data[2];
              
              Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "sessionId:  " + sessionId+" lat:"+latitude+" long:"+longitude);
              
              //query google
              //https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyB-WKjTtvxRRQ5ZQnQAnlUa8xlXjDnSgG4&latlng=2.9436091,101.5900234
            
              Address address = googleLocationService.getLocationDetails(latitude, longitude);
              Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Address details : "+address);
              
              if (address!=null) {
                  CustomerSession customerSession = new CustomerSession();
                  customerSession.setSessionId(sessionId);
                  customerSession.setLatitude(latitude);
                  customerSession.setLongitude(longitude);
                  customerSession.setAddress(address.getAddress());
                  customerSession.setCity(address.getCity());
                  customerSession.setPostcode(address.getPostcode());
                  customerSession.setState(address.getState());
                  customerSession.setCountry(address.getCountry());
                  customerSession.setCreated(new Date());
                  customerSessionRepository.save(customerSession);
              }
            }
        }
    }
}

