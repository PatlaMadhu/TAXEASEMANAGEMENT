package com.cts.taxeasemanagement.controller;

import com.cts.taxeasemanagement.dto.responsedto.ComplianceResponse;
import com.cts.taxeasemanagement.dto.requestdto.CreateComplianceRequest;
import com.cts.taxeasemanagement.dto.requestdto.UpdateComplianceRequest;
import com.cts.taxeasemanagement.service.ComplianceService;
import jakarta.validation.Valid; // Required for validation enforcement
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
@Slf4j
public class ComplianceController {

    private final ComplianceService complianceService;

    @PostMapping
    public ResponseEntity<ComplianceResponse> createCompliance(
            @Valid @RequestBody CreateComplianceRequest request) {
        log.info("START: Creating compliance for taxpayer ID: {}", request.getTaxpayerId());
        ComplianceResponse response = complianceService.createCompliance(request);
        log.info("END: Compliance created with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ComplianceResponse>> getAllCompliance() {
        log.info("START: Fetching all compliance records");
        List<ComplianceResponse> response = complianceService.getAllCompliance();
        log.info("END: Fetched {} compliance records", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplianceResponse> getComplianceById(@PathVariable Long id){
        log.info("START: Fetching compliance ID: {}", id);
        ComplianceResponse response = complianceService.getComplianceById(id);
        log.info("END: Retrieved compliance ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplianceResponse> updateCompliance(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComplianceRequest request) {
        log.info("START: Updating compliance ID: {}", id);
        ComplianceResponse response = complianceService.updateCompliance(id, request);
        log.info("END: Updated compliance ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/taxpayer/{taxpayerId}")
    public ResponseEntity<List<ComplianceResponse>> getComplianceByTaxpayerId(@PathVariable Long taxpayerId){
        log.info("START: Fetching compliance for taxpayer ID: {}", taxpayerId);
        List<ComplianceResponse> response = complianceService.getComplianceByTaxpayerId(taxpayerId);
        log.info("END: Retrieved {} compliance records", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/result/{result}")
    public ResponseEntity<List<ComplianceResponse>> getByResult(@PathVariable String result){
        log.info("START: Fetching compliance by result: {}", result);
        List<ComplianceResponse> response = complianceService.getByResult(result);
        log.info("END: Retrieved {} records for result: {}", response.size(), result);
        return ResponseEntity.ok(response);
    }
}
