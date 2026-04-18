package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.dto.responsedto.AuditResponse;
import com.cts.taxeasemanagement.dto.requestdto.CloseAuditRequest;

import java.util.List;

public interface AuditService {

    List<AuditResponse> getAllAudits();

    AuditResponse getAuditById(Long id);

    AuditResponse closeAudit(Long id, CloseAuditRequest request);
}
