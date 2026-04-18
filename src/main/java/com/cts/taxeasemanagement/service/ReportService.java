package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.entity.Audit;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.dto.responsedto.PaymentMetricsResponse;
import com.cts.taxeasemanagement.dto.responsedto.AuditDashboardResponse;
import com.cts.taxeasemanagement.dto.responsedto.RevenueDashboardResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    PaymentMetricsResponse getPaymentMetrics(PaymentMethod method);
    AuditDashboardResponse getAuditDashboard();
    RevenueDashboardResponse getRevenueDashboard(String period, String taxpayerType);
    List<Audit> getCompletedAudits();
    byte[] generateCustomReport(LocalDate startDate, LocalDate endDate, String reportType, List<String> metrics);
}