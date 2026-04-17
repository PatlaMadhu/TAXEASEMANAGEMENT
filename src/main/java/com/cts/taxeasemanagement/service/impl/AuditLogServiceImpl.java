package com.cts.taxeasemanagement.service.impl;

import com.cts.taxeasemanagement.dao.AuditLogRepository;
import com.cts.taxeasemanagement.dao.UserRepository;
import com.cts.taxeasemanagement.entity.AuditLog;
import com.cts.taxeasemanagement.entity.User;
import com.cts.taxeasemanagement.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    public void record(String action, String resource) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + email));
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(action)
                .resource(resource)
                .build());
    }

    @Override
    public void recordRegistration(User newUser, String action, String resource) {
        auditLogRepository.save(AuditLog.builder()
                .user(newUser)
                .action(action)
                .resource(resource)
                .build());
    }

    @Override
    public List<AuditLog> list() {
        return auditLogRepository.findAll();
    }

    @Override
    public AuditLog get(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("AuditLog not found: " + id));
    }
}
