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
    @Query(value = "UPDATE total_unique_user_overall SET totalUniqueGuest =:totalUnique WHERE dt=:dt", nativeQuery = true) 
    void updateTotalUniqueGuestOverall(String dt, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user_overall SET totalUnique =:totalUnique WHERE dt=:dt", nativeQuery = true) 
    void updateTotalUniqueCustomerOverall(String dt, int totalUnique);
    
    @Query(value = "SELECT COUNT(*) FROM total_unique_user_overall"
            + " WHERE dt=:dt", nativeQuery = true)
    List<Object[]> checkExistingRecordOverall(@Param("dt") String dt);
     
}
