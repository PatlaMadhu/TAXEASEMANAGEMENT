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
public class CloseAuditRequest {

    @NotBlank(message = "Audit findings are required to close an audit")
    @Size(max = 2000, message = "Findings cannot exceed 2000 characters")
    private String findings;
}
