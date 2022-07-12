/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kalsym.analytic.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalsym.analytic.service.AnalyticServiceApplication;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kalsym.analytic.service.model.Address;
import com.kalsym.analytic.service.utils.Logger;
import org.springframework.stereotype.Service;
/**
 *
 * @author taufik
 */
@Service
public class GoogleLocationService {
    
    /**
     *
     * @param storeId
     * @param productId
     * @return
     */
    public Address getLocationDetails(String latitude, String longitude) {
        String logprefix = "getProductById";
        String apiKey = "AIzaSyB-WKjTtvxRRQ5ZQnQAnlUa8xlXjDnSgG4";
        try {
            String targetUrl = "https://maps.googleapis.com/maps/api/geocode/json?key=%apiKey%&latlng=%latitude%,%longitude%";
            targetUrl = targetUrl.replace("%apiKey%", apiKey);
            targetUrl = targetUrl.replace("%latitude%", latitude);
            targetUrl = targetUrl.replace("%longitude%", longitude);
            RestTemplate restTemplate = new RestTemplate();
            
            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Sending request to URL: " + targetUrl);
            ResponseEntity<String> res = restTemplate.exchange(targetUrl, HttpMethod.GET, null, String.class);

            Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Request sent to google, responseCode: " + res.getStatusCode() + ", responseBody:" + res.getBody());

            if (res.getStatusCode() == HttpStatus.OK) {
                Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "res : " + res);
                JSONObject jsonObject = new JSONObject(res.getBody());
                JSONArray addressArray = jsonObject.getJSONArray("results");
                Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "object of addressArray: " + addressArray);
                Address addressDetails = new Address();
                String street = null;
                for (int i=0;i<addressArray.length();i++) {
                    JSONObject jsonAddress = addressArray.getJSONObject(i);
                    JSONArray componentArray = jsonAddress.getJSONArray("address_components");
                    for (int x=0;x<componentArray.length();x++) {
                        JSONObject componentObject = componentArray.getJSONObject(x);
                        JSONArray typeArray = componentObject.getJSONArray("types");
                        for (int z=0;z<typeArray.length();z++) {
                            String type = typeArray.getString(z);
                            if (type.equalsIgnoreCase("route") && addressDetails.getAddress()==null) {
                                addressDetails.setAddress(componentObject.getString("long_name"));
                            } else if (type.equalsIgnoreCase("sublocality_level_1") && addressDetails.getAddress()!=null && street==null) {
                                street = componentObject.getString("long_name");
                                addressDetails.setAddress(addressDetails.getAddress()+ ", " +street);
                            } else if (type.equalsIgnoreCase("locality") && addressDetails.getCity()==null) {
                                addressDetails.setCity(componentObject.getString("long_name"));
                            } else if (type.equalsIgnoreCase("administrative_area_level_1")) {
                                addressDetails.setState(componentObject.getString("long_name"));
                            } else if (type.equalsIgnoreCase("country")) {
                                addressDetails.setCountry(componentObject.getString("long_name"));
                            } else if (type.equalsIgnoreCase("postal_code")) {
                                addressDetails.setPostcode(componentObject.getString("long_name"));                            
                            }
                        }
                    }
                    Logger.application.info(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "got addressDetails : " + addressDetails.toString());
                }
                return addressDetails;
            }
        } catch (Exception e) {
            Logger.application.error(Logger.pattern, AnalyticServiceApplication.VERSION, logprefix, "Error get location details ", e);
            return null;
        }
        return null;
    }
}
