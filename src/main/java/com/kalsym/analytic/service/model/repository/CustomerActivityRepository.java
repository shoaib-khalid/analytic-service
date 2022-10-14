package com.kalsym.analytic.service.model.repository;

import com.kalsym.analytic.service.model.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, String> {

    @Query(value = "SELECT COUNT(*) AS bil, COUNT(DISTINCT(sessionId)) AS totalUnique, DATE(created), storeId, browserType, deviceModel, os, pageVisited FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate GROUP BY DATE(created), storeId, browserType, deviceModel, os, pageVisited", nativeQuery = true)
    List<Object[]> getCountSummary(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(customerId)) AS totalUniqueUser, DATE(created), storeId FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NOT NULL "
            + " AND (pageVisited LIKE '%deliverin.my%' OR pageVisited LIKE '%dev-my%')"
            + " GROUP BY DATE(created), storeId", nativeQuery = true)
    List<Object[]> getUniqueUserSummaryMYS(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(customerId)) AS totalUniqueUser, DATE(created), storeId FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NOT NULL "
            + " AND (pageVisited LIKE '%easydukan.co%' OR pageVisited LIKE '%dev-pk%')"
            + " GROUP BY DATE(created), storeId", nativeQuery = true)
    List<Object[]> getUniqueUserSummaryPAK(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(customerId)) AS totalUniqueUser, DATE(created) FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NOT NULL "
            + " AND (pageVisited LIKE '%deliverin.my%' OR pageVisited LIKE '%dev-my%')"
            + " GROUP BY DATE(created)", nativeQuery = true)
    List<Object[]> getUniqueUserSummaryOverallMYS(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(customerId)) AS totalUniqueUser, DATE(created) FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NOT NULL "
            + " AND (pageVisited LIKE '%easydukan.co%' OR pageVisited LIKE '%dev-pk%')"
            + " GROUP BY DATE(created)", nativeQuery = true)
    List<Object[]> getUniqueUserSummaryOverallPAK(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(sessionId)) AS totalUniqueGuest, DATE(created), storeId FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NULL "
            + " AND (pageVisited LIKE '%deliverin.my%' OR pageVisited LIKE '%dev-my%')"
            + " GROUP BY DATE(created), storeId", nativeQuery = true)
    List<Object[]> getUniqueGuestummaryMYS(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(sessionId)) AS totalUniqueGuest, DATE(created), storeId FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NULL "
            + " AND (pageVisited LIKE '%easydukan.co%' OR pageVisited LIKE '%dev-pk%')"
            + " GROUP BY DATE(created), storeId", nativeQuery = true)
    List<Object[]> getUniqueGuestummaryPAK(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(sessionId)) AS totalUniqueGuest, DATE(created) FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NULL "
            + " AND (pageVisited LIKE '%deliverin.my%' OR pageVisited LIKE '%dev-my%')"
            + " GROUP BY DATE(created)", nativeQuery = true)
    List<Object[]> getUniqueGuestummaryOverallMYS(@Param("yesterdayDate") String currentDate);
    
    @Query(value = "SELECT COUNT(DISTINCT(sessionId)) AS totalUniqueGuest, DATE(created) FROM customer_activities"
            + " WHERE DATE(created)=:yesterdayDate AND customerId IS NULL "
            + " AND (pageVisited LIKE '%easydukan.co%' OR pageVisited LIKE '%dev-pk%')"
            + " GROUP BY DATE(created)", nativeQuery = true)
    List<Object[]> getUniqueGuestummaryOverallPAK(@Param("yesterdayDate") String currentDate);
     
    
    @Transactional 
    @Modifying
    @Query(value = "DELETE FROM customer_activities WHERE DATE_ADD(created, INTERVAL 60 DAY) < NOW()", nativeQuery = true) 
    void RemoveOldRecord();
    
    @Query(value = "SELECT DISTINCT(sessionId), latitude, longitude FROM customer_activities"
            + " WHERE latitude IS NOT NULL "
             + "AND latitude <>'0' "
             + "AND sessionId IS NOT NULL "
             + "AND sessionId NOT IN (SELECT sessionId FROM customer_session)", nativeQuery = true)
    List<Object[]> getUnknownUserLocation();
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE customer_activities SET CHANNEL='Google' WHERE pageVisited LIKE '%gclid%' AND CHANNEL IS NULL", nativeQuery = true) 
    void UpdateGoogleChannel();
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE customer_activities SET CHANNEL='Facebook' WHERE pageVisited LIKE '%fbclid%' AND CHANNEL IS NULL", nativeQuery = true) 
    void UpdateFacebookChannel();
    
   
}
