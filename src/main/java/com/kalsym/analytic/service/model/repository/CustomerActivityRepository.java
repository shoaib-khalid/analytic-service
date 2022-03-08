package com.kalsym.analytic.service.model.repository;

import com.kalsym.analytic.service.model.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, String> {


}
