package com.cts.taxeasemanagement.dao;


import com.cts.taxeasemanagement.entity.Payment;
import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Fulfills TAXFR-11: Get payments for a specific taxpayer
    List<Payment> findByFiling_Taxpayer_Id(Long taxpayerId);
    long countByStatus(StatusBasic status);
    long countByStatusAndPaymentMethod(StatusBasic status, PaymentMethod paymentMethod);
    long countByPaymentMethod(PaymentMethod paymentMethod);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'Pending'")
    BigDecimal sumOutstandingPayments();
}
