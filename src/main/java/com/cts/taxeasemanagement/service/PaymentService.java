package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dto.responsedto.PaymentMetricsResponse;
import com.cts.taxeasemanagement.dto.responsedto.PaymentResponseDTO;
import com.cts.taxeasemanagement.dto.responsedto.RevenueDashboardResponse;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO makePayment(Long filingId, PaymentMethod method, BigDecimal amount, StatusBasic status);
    List<PaymentResponseDTO> getPaymentsByTaxpayer(Long taxpayerId);
    PaymentResponseDTO retryPayment(Long oldPaymentId, PaymentMethod newMethod);

    // Admin dashboard methods
    PaymentMetricsResponse getPaymentMetrics();
    RevenueDashboardResponse getRevenueDashboard();
}
