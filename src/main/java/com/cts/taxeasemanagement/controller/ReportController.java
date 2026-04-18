package com.cts.taxeasemanagement.controller;

import com.cts.taxeasemanagement.dto.responsedto.PaymentMetricsResponse;
import com.cts.taxeasemanagement.dto.responsedto.AuditDashboardResponse;
import com.cts.taxeasemanagement.dto.responsedto.RevenueDashboardResponse;
import com.cts.taxeasemanagement.entity.Audit;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.service.ReportService;
import jakarta.validation.Valid; // Required for DTO validation
import jakarta.validation.constraints.NotEmpty; // Example for list validation
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; // Required for parameter validation
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Validated // Enables validation for method parameters like @RequestParam
@Slf4j
public class ReportController {

    private final ReportService reportService;

    // Story 1: Payment Success Metrics
    @GetMapping("/payments/metrics")
    public ResponseEntity<PaymentMetricsResponse> getPaymentMetrics(
            @RequestParam(required = false) PaymentMethod method) {
        log.info("START: Fetching payment metrics for method: {}", method);
        PaymentMetricsResponse response = reportService.getPaymentMetrics(method);
        log.info("END: Payment metrics retrieved");
        return ResponseEntity.ok(response);
    }

    // Story 4: Audit Dashboard
    @GetMapping("/audits/dashboard")
    public ResponseEntity<AuditDashboardResponse> getAuditDashboard() {
        log.info("START: Fetching audit dashboard");
        AuditDashboardResponse response = reportService.getAuditDashboard();
        log.info("END: Audit dashboard retrieved");
        return ResponseEntity.ok(response);
    }

    // Story 3: Read-Only Audit Reports
    @GetMapping("/audits/completed")
    public ResponseEntity<List<Audit>> getCompletedAudits() {
        log.info("START: Fetching completed audits");
        List<Audit> r=reportService.getCompletedAudits();
        log.info("END: Completed audits retrieved");
        return ResponseEntity.ok(r);
    }

    // Story 5: Revenue Collection Dashboard
    @GetMapping("/revenue/dashboard")
    public ResponseEntity<RevenueDashboardResponse> getRevenueDashboard(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String taxpayerType) {
        log.info("START: Fetching revenue dashboard for period: {} | Type: {}", period, taxpayerType);
        RevenueDashboardResponse response = reportService.getRevenueDashboard(period, taxpayerType);
        log.info("END: Revenue dashboard retrieved");
        return ResponseEntity.ok(response);
    }

    // Story 2: Generate Downloadable Custom Report
    @GetMapping("/custom/download")
    public ResponseEntity<byte[]> downloadCustomReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String reportType,
            @Valid @NotEmpty(message = "At least one metric must be selected") @RequestParam List<String> metrics) {

        log.info("START: Generating Custom Report [{}] | Period: {} to {}", reportType, startDate, endDate);
        byte[] reportData = reportService.generateCustomReport(startDate, endDate, reportType, metrics);
        log.info("END: Report generated | Size: {} bytes", reportData.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(reportData);
    }
}
