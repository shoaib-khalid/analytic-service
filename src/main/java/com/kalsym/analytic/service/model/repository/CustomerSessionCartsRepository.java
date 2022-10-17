package com.kalsym.analytic.service.model.repository;

import com.kalsym.analytic.service.model.CustomerSessionCarts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerSessionCartsRepository extends JpaRepository<CustomerSessionCarts, Long> {
    List<CustomerSessionCarts> findBySessionIdAndCartId(@Param("sessionId") String sessionId, @Param("cartId") String cartId);
}
