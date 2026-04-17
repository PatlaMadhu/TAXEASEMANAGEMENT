package com.cts.taxeasemanagement.service;



import com.cts.taxeasemanagement.dto.requestdto.FillingDocumentRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.FillingDocumentResponseDTO;
import java.util.List;

/**
 * Service contract for managing filing documents.
 */
public interface FillingDocumentService {

    /**
     * Upload and link a document to a filing.
     */
    FillingDocumentResponseDTO addDocument(FillingDocumentRequestDTO dto);

    /**
     * Get all documents for a specific filing.
     */
    List<FillingDocumentResponseDTO> getDocumentsByFiling(Long filingId);
}
