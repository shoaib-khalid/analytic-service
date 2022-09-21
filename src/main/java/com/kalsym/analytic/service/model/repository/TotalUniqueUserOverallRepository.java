package com.kalsym.analytic.service.model.repository;

import com.kalsym.analytic.service.model.TotalUniqueUserOverall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TotalUniqueUserOverallRepository extends JpaRepository<TotalUniqueUserOverall, Long> {
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user_overall SET totalUniqueGuest =:totalUnique WHERE dt=:dt AND country='MYS'", nativeQuery = true) 
    void updateTotalUniqueGuestOverallMYS(String dt, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user_overall SET totalUniqueGuest =:totalUnique WHERE dt=:dt AND country='PAK'", nativeQuery = true) 
    void updateTotalUniqueGuestOverallPAK(String dt, int totalUnique);
    
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user_overall SET totalUnique =:totalUnique WHERE dt=:dt AND country='MYS'", nativeQuery = true) 
    void updateTotalUniqueCustomerOverallMYS(String dt, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user_overall SET totalUnique =:totalUnique WHERE dt=:dt AND country='PAK'", nativeQuery = true) 
    void updateTotalUniqueCustomerOverallPAK(String dt, int totalUnique);
    
    @Query(value = "SELECT COUNT(*) FROM total_unique_user_overall"
            + " WHERE dt=:dt AND country='MYS'", nativeQuery = true)
    List<Object[]> checkExistingRecordOverallMYS(@Param("dt") String dt);
            
    @Query(value = "SELECT COUNT(*) FROM total_unique_user_overall"
            + " WHERE dt=:dt AND country='PAK'", nativeQuery = true)
    List<Object[]> checkExistingRecordOverallPAK(@Param("dt") String dt);
     
}
