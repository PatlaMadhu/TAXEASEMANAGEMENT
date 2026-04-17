package com.cts.taxeasemanagement.dao;

import com.cts.taxeasemanagement.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
