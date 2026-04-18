package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.dto.requestdto.CreateComplianceRequest;
import com.cts.taxeasemanagement.dto.responsedto.ComplianceResponse;
import com.cts.taxeasemanagement.dto.requestdto.UpdateComplianceRequest;

import java.util.List;

public interface ComplianceService {

    List<ComplianceResponse> getAllCompliance();

    ComplianceResponse createCompliance(CreateComplianceRequest request);

    ComplianceResponse getComplianceById(Long id);

    ComplianceResponse updateCompliance(Long id, UpdateComplianceRequest request);

    List<ComplianceResponse>getComplianceByTaxpayerId(Long taxpayerId);

    List<ComplianceResponse> getByResult(String result);
}