package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dto.requestdto.TaxFillingRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.TaxFillingResponseDTO;
import java.util.List;

/**
 * Service contract for managing tax filings.
 */
public interface TaxFillingService {

    /**
     * Submit a new tax filing.
     * @param dto the filing data
     * @return the created filing response
     */
    TaxFillingResponseDTO submitFiling(TaxFillingRequestDTO dto);

    /**
     * Retrieve all filings for a specific taxpayer.
     * @param taxpayerId the ID of the taxpayer
     * @return list of filing history
     */
    List<TaxFillingResponseDTO> getFilingHistory(Long taxpayerId);

    /**
     * Update the status of a filing (Officer Action).
     * @param filingId the ID of the filing
     * @param newStatus the new status (APPROVED, REJECTED, etc.)
     * @param officerId the ID of the officer performing the update
     * @return the updated filing response
     */
    TaxFillingResponseDTO updateFilingStatus(Long filingId, String newStatus, Long officerId);
}
