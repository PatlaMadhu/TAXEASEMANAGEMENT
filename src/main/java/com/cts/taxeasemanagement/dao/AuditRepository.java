package com.cts.taxeasemanagement.dao;

import com.cts.taxeasemanagement.entity.Audit;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    long countByStatus(StatusBasic status);
    List<Audit> findByStatus(StatusBasic status);
}