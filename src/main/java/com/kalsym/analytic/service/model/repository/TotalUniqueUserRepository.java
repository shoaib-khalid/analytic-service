package com.kalsym.analytic.service.model.repository;

import com.kalsym.analytic.service.model.TotalUniqueUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TotalUniqueUserRepository extends JpaRepository<TotalUniqueUser, Long> {
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user SET totalUniqueGuest =:totalUnique WHERE dt=:dt AND storeId=:storeId AND country", nativeQuery = true) 
    void updateTotalUniqueGuestMYS(String dt, String storeId, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user SET totalUniqueGuest =:totalUnique WHERE dt=:dt AND storeId=:storeId", nativeQuery = true) 
    void updateTotalUniqueGuestPAK(String dt, String storeId, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user SET totalUnique =:totalUnique WHERE dt=:dt AND storeId=:storeId AND country='MYS'", nativeQuery = true) 
    void updateTotalUniqueCustomerMYS(String dt, String storeId, int totalUnique);
    
    @Transactional 
    @Modifying
    @Query(value = "UPDATE total_unique_user SET totalUnique =:totalUnique WHERE dt=:dt AND storeId=:storeId AND country='PAK'", nativeQuery = true) 
    void updateTotalUniqueCustomerPAK(String dt, String storeId, int totalUnique);
    
    @Query(value = "SELECT COUNT(*) FROM total_unique_user"
            + " WHERE dt=:dt AND storeId=:storeId AND country = 'MYS'", nativeQuery = true)
    List<Object[]> checkExistingRecordMYS(@Param("dt") String dt, @Param("storeId") String storeId);
    
    @Query(value = "SELECT COUNT(*) FROM total_unique_user"
            + " WHERE dt=:dt AND storeId=:storeId AND country='PAK'", nativeQuery = true)
    List<Object[]> checkExistingRecordPAK(@Param("dt") String dt, @Param("storeId") String storeId);
     
}
