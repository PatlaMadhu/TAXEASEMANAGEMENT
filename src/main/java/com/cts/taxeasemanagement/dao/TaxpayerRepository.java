package com.cts.taxeasemanagement.dao;

import com.cts.taxeasemanagement.entity.Taxpayer;
import com.cts.taxeasemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxpayerRepository extends JpaRepository<Taxpayer, Long> {
    Optional<Taxpayer> findByUser(User user);
}
