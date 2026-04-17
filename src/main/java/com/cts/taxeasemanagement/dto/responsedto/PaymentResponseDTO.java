package com.cts.taxeasemanagement.dto.responsedto;


import com.cts.taxeasemanagement.entity.entityEnum.PaymentMethod;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long filingId;
    private BigDecimal amount;
    private PaymentMethod method;
    private StatusBasic status;
    private Instant date;
}
