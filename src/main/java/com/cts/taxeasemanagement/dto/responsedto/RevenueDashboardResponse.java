package com.cts.taxeasemanagement.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDashboardResponse {
    private BigDecimal totalRevenue;
    private BigDecimal successfulRevenue;
    private BigDecimal pendingRevenue;
    private long totalTransactions;
}
