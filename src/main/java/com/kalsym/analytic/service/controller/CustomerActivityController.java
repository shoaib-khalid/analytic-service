package com.kalsym.analytic.service.controller;

import com.kalsym.analytic.service.model.repository.StoreRepository;
import com.kalsym.analytic.service.model.CustomerActivity;
import com.kalsym.analytic.service.model.Response;
import com.kalsym.analytic.service.model.Store;
import com.kalsym.analytic.service.AnalyticServiceApplication;
import com.kalsym.analytic.service.service.enums.OrderStatus;
import com.kalsym.analytic.service.utils.HttpResponse;
import com.kalsym.analytic.service.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import com.kalsym.analytic.service.model.repository.CustomerActivityRepository;

@RestController
@RequestMapping(path = "/customeractivity")
public class CustomerActivityController {

    @Autowired
    CustomerActivityRepository customerActivityRepository;

    @Autowired
    StoreRepository storeRepository;    

    @PostMapping(path = {""}, name = "customer-activity-post")   
    public ResponseEntity<HttpResponse> postActivity(HttpServletRequest request,
            @Valid @RequestBody CustomerActivity bodyActivity) throws Exception {
        String logprefix = request.getRequestURI() + " ";
        HttpResponse response = new HttpResponse(request.getRequestURI());

        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "customer-activity-post, URL:  " + request.getRequestURI());
        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "customer-activity-post, bodyCart: ", bodyActivity.toString());

        try {
            bodyActivity.setCreated(new Date());
            if (bodyActivity.getErrorOccur()!=null) {
                if (bodyActivity.getErrorOccur().length()>1000) {
                    bodyActivity.setErrorOccur(bodyActivity.getErrorOccur().substring(0,1000));
                }
            }
            customerActivityRepository.save(bodyActivity);
            response.setSuccessStatus(HttpStatus.CREATED);
        } catch (Exception exp) {
            Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Error saving customer activity", exp);
            response.setMessage(exp.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(response);
        }
        Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "customer activity created with id: " + bodyActivity.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
