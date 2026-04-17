package com.cts.taxeasemanagement.controller;


import com.cts.taxeasemanagement.dto.requestdto.PaymentRequest;
import com.cts.taxeasemanagement.dto.responsedto.PaymentMetricsResponse;
import com.cts.taxeasemanagement.dto.responsedto.PaymentResponseDTO;
import com.cts.taxeasemanagement.dto.responsedto.RevenueDashboardResponse;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public PaymentResponseDTO makePayment(@RequestBody PaymentRequest request) {
        log.info("START: Initiating payment for Filing ID: {} | Amount: {}", request.getFilingId(), request.getAmount());
        PaymentResponseDTO response = paymentService.makePayment(request.getFilingId(), request.getMethod(), request.getAmount(), request.getStatus());
        log.info("END: Payment processed | Payment ID: {} | Status: {}", response.getId(), response.getStatus());
        return response;
    }

    @GetMapping("/history/{taxpayerId}")
    public List<PaymentResponseDTO> getPaymentHistory(@PathVariable Long taxpayerId) {
        log.info("START: Fetching payment history for taxpayer {}", taxpayerId);
        List<PaymentResponseDTO> response = paymentService.getPaymentsByTaxpayer(taxpayerId);
        log.info("END: Retrieved {} payment records", response.size());
        return response;
    }

    @PostMapping("/retry/{oldPaymentId}")
    public PaymentResponseDTO retryPayment(
            @PathVariable Long oldPaymentId,
            @RequestParam PaymentMethod newMethod) {
        log.info("START: Retrying payment for old ID: {} | New Method: {}", oldPaymentId, newMethod);
        PaymentResponseDTO response = paymentService.retryPayment(oldPaymentId, newMethod);
        log.info("END: Retry payment processed | New Payment ID: {}", response.getId());
        return response;
    }

    @GetMapping("/metrics")
    public PaymentMetricsResponse getPaymentMetrics() {
        log.info("START: Fetching payment metrics");
        return paymentService.getPaymentMetrics();
    }

    @GetMapping("/revenue")
    public RevenueDashboardResponse getRevenueDashboard() {
        log.info("START: Fetching revenue dashboard");
        return paymentService.getRevenueDashboard();
    }
}