package com.cts.taxeasemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filing_id", nullable = false)
    private TaxFilling filing;

    @Column(name = "amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    @CreationTimestamp
    @Column(name = "payment_date", updatable = false, nullable = false)
    private Instant paymentDate;
}
