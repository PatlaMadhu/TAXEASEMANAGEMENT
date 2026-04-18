package com.cts.taxeasemanagement.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateComplianceRequest {

    @NotBlank(message = "Compliance result is required (e.g., COMPLIANT, NON_COMPLIANT)")
    @Size(max = 50, message = "Result status cannot exceed 50 characters")
    private String result;

    // Notes are usually optional, so we drop @NotBlank but keep @Size to protect the database
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
}
