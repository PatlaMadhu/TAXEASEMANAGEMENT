package com.cts.taxeasemanagement.entity;

import com.cts.taxeasemanagement.entity.entityEnum.TaxpayerType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "taxpayer")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Taxpayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taxpayer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "taxpayer_id_number", length = 50)
    private String taxpayerIdNumber;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "pan_number", length = 20)
    private String panNumber;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "contact_info", length = 200)
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaxpayerType type;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
