package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dao.PaymentRepository;
import com.cts.taxeasemanagement.dao.RevenueRecordRepository;
import com.cts.taxeasemanagement.dao.TaxFillingRepository;
import com.cts.taxeasemanagement.dto.responsedto.PaymentMetricsResponse;
import com.cts.taxeasemanagement.dto.responsedto.PaymentResponseDTO;
import com.cts.taxeasemanagement.dto.responsedto.RevenueDashboardResponse;
import com.cts.taxeasemanagement.entity.Payment;
import com.cts.taxeasemanagement.entity.RevenueRecord;
import com.cts.taxeasemanagement.entity.TaxFilling;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TaxFillingRepository taxFilingRepository;

    @Autowired
    private RevenueRecordRepository revenueRecordRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    @Transactional
    public PaymentResponseDTO makePayment(Long filingId, PaymentMethod method, BigDecimal amount, StatusBasic status) {
        TaxFilling filing = taxFilingRepository.findById(filingId)
                .orElseThrow(() -> new RuntimeException("Filing not found"));

        Payment payment = Payment.builder()
                .filing(filing)
                .paymentMethod(method)
                .amount(amount)
                .status(status)
                .build();

        payment = paymentRepository.save(payment);

        if (status == StatusBasic.Completed) {
            RevenueRecord revenueRecord = RevenueRecord.builder()
                    .taxpayer(filing.getTaxpayer())
                    .payment(payment)
                    .amount(amount)
                    .status(StatusBasic.Completed)
                    .build();
            revenueRecordRepository.save(revenueRecord);

            auditLogService.record("PAYMENT_CREATE", "payments/" + payment.getId());
        }

        return mapToDto(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentsByTaxpayer(Long taxpayerId) {
        List<Payment> payments = paymentRepository.findByFiling_Taxpayer_Id(taxpayerId);
        return payments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentResponseDTO retryPayment(Long oldPaymentId, PaymentMethod newMethod) {
        Payment oldPayment = paymentRepository.findById(oldPaymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (oldPayment.getStatus() != StatusBasic.Failed) {
            throw new RuntimeException("Only failed payments can be retried");
        }

        auditLogService.record("PAYMENT_RETRY", "payments/" + oldPaymentId);
        return makePayment(
                oldPayment.getFiling().getId(),
                newMethod,
                oldPayment.getAmount(),
                StatusBasic.Completed // Assuming success for the retry
        );
    }

    @Override
    public PaymentMetricsResponse getPaymentMetrics() {
        long successful = paymentRepository.countByStatus(StatusBasic.Completed);
        long failed = paymentRepository.countByStatus(StatusBasic.Failed);
        long total = paymentRepository.count();

        return PaymentMetricsResponse.builder()
                .successfulTransactions(successful)
                .failedTransactions(failed)
                .totalTransactions(total)
                .build();
    }

    @Override
    public RevenueDashboardResponse getRevenueDashboard() {
        BigDecimal collected = revenueRecordRepository.sumCollectedRevenue();
        BigDecimal outstanding = paymentRepository.sumOutstandingPayments();

        long total = paymentRepository.count();

        return RevenueDashboardResponse.builder()
                .totalRevenue(collected != null ? collected : BigDecimal.ZERO)
                .successfulRevenue(collected != null ? collected : BigDecimal.ZERO)
                .pendingRevenue(outstanding != null ? outstanding : BigDecimal.ZERO)
                .totalTransactions(total)
                .build();
    }

    // Helper method to map Payment entity to PaymentResponseDto
    private PaymentResponseDTO mapToDto(Payment payment) {
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .filingId(payment.getFiling().getId())
                .amount(payment.getAmount())
                .method(payment.getPaymentMethod())
                .status(payment.getStatus())
                .date(payment.getPaymentDate())
                .build();
    }
}
