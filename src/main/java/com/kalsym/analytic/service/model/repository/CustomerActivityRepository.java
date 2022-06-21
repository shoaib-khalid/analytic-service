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
     
    @Transactional 
    @Modifying
    @Query(value = "DELETE FROM customer_activities WHERE DATE_ADD(created, INTERVAL 30 DAY) < NOW()", nativeQuery = true) 
    void RemoveOldRecord();
    
}
