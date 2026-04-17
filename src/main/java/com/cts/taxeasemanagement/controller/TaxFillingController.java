package com.cts.taxeasemanagement.controller;



import com.cts.taxeasemanagement.dto.requestdto.TaxFillingRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.TaxFillingResponseDTO;
import com.cts.taxeasemanagement.service.TaxFillingService;
import jakarta.validation.Valid; // Required for DTO validation
import jakarta.validation.constraints.NotBlank; // For parameter validation
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated; // Required for @RequestParam validation
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
@Slf4j// Enables validation for @RequestParam
public class TaxFillingController {

    private final TaxFillingService taxFilingService;

    /**
     * Submits a new tax filing.
     * @Valid activates the rules like @Positive on income and @NotNull on taxYear.
     */
    @PostMapping("/submit")
    public ResponseEntity<TaxFillingResponseDTO> submitFiling(
            @Valid @RequestBody TaxFillingRequestDTO dto) {
        log.info("START: New tax filing submission for year: {}", dto.getPeriod());
        TaxFillingResponseDTO response = taxFilingService.submitFiling(dto);
        log.info("END: Filing submission successful | Filing ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/taxpayer/{taxpayerId}")
    public ResponseEntity<List<TaxFillingResponseDTO>> getHistory(@PathVariable Long taxpayerId) {
        log.info("START: Fetching filing history for taxpayer ID: {}", taxpayerId);
        List<TaxFillingResponseDTO> response = taxFilingService.getFilingHistory(taxpayerId);
        log.info("END: Retrieved {} filings", response.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the status of a filing (e.g., to APPROVED or REJECTED).
     * @NotBlank ensures the status isn't an empty string.
     */
    @PutMapping("/{filingId}/status")
    public ResponseEntity<TaxFillingResponseDTO> updateStatus(
            @PathVariable Long filingId,
            @NotBlank(message = "Status is required") @RequestParam String status,
            @RequestParam(required = false) Long officerId) {
        log.info("START: Updating status for Filing ID: {} to {} | Officer: {}", filingId, status, officerId);
        TaxFillingResponseDTO response = taxFilingService.updateFilingStatus(filingId, status, officerId);
        log.info("END: Status update successful | Current Status: {}", response.getStatus());
        return ResponseEntity.ok(response);
    }
}
