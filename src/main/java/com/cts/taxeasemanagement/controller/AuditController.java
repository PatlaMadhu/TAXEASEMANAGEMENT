package com.cts.taxeasemanagement.controller;

import com.cts.taxeasemanagement.dto.responsedto.AuditResponse;
import com.cts.taxeasemanagement.dto.requestdto.CloseAuditRequest;
import com.cts.taxeasemanagement.service.AuditLogService;
import com.cts.taxeasemanagement.service.AuditService;
import jakarta.validation.Valid; // Don't forget this import!
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditResponse>> getAllAudits() {
        log.info("START: Fetching all audits");
        List<AuditResponse> response = auditService.getAllAudits();
        log.info("END: Fetched {} audits", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponse> getAuditById(@PathVariable Long id) {
        log.info("START: Fetching audit details for ID: {}", id);
        AuditResponse response = auditService.getAuditById(id);
        log.info("END: Successfully retrieved audit ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Closes an audit.
     * Added @Valid to ensure 'findings' is not blank as per our DTO rules.
     */
    @PutMapping("/{id}/close")
    public ResponseEntity<AuditResponse> closeAudit(
            @PathVariable Long id,
            @Valid @RequestBody CloseAuditRequest request) {
        log.info("START: Closing audit ID: {} | Findings: {}", id, request.getFindings());
        AuditResponse response = auditService.closeAudit(id, request);
        log.info("END: Audit ID: {} closed successfully | Status: {}", id, response.getStatus());
        return ResponseEntity.ok(response);
    }
}