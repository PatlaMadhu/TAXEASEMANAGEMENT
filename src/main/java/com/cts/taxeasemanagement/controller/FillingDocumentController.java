package com.cts.taxeasemanagement.controller;



import com.cts.taxeasemanagement.dto.requestdto.FillingDocumentRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.FillingDocumentResponseDTO;
import com.cts.taxeasemanagement.service.FillingDocumentService;
import jakarta.validation.Valid; // Required to activate DTO validation
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class FillingDocumentController {

    private final FillingDocumentService documentService;

    /**
     * Uploads a document link for a specific filing.
     * Added @Valid to ensure the RequestDTO meets all validation constraints.
     */
    @PostMapping("/upload")
    public ResponseEntity<FillingDocumentResponseDTO> uploadDocument(
            @Valid @RequestBody FillingDocumentRequestDTO dto) {
        log.info("START: Uploading document for filing ID: {}", dto.getFilingId());
        FillingDocumentResponseDTO response = documentService.addDocument(dto);
        log.info("END: Document upload successful | ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/filing/{filingId}")
    public ResponseEntity<List<FillingDocumentResponseDTO>> getDocuments(@PathVariable Long filingId) {
        log.info("START: Fetching Specific documents by id : {}", filingId);
        List<FillingDocumentResponseDTO> fdr=documentService.getDocumentsByFiling(filingId);
        log.info("END: Successfully fetched documents for filingId: {}",filingId);
        return ResponseEntity.ok(fdr);
    }
}
