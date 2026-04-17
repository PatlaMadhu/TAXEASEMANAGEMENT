package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dao.UserRepository;
import com.cts.taxeasemanagement.dto.requestdto.FillingDocumentRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.FillingDocumentResponseDTO;
import com.cts.taxeasemanagement.entity.FillingDocument;
import com.cts.taxeasemanagement.entity.TaxFilling;
import com.cts.taxeasemanagement.dao.FillingDocumentRepository;
import com.cts.taxeasemanagement.dao.TaxFillingRepository;
import com.cts.taxeasemanagement.entity.Taxpayer;
import com.cts.taxeasemanagement.entity.User;
import com.cts.taxeasemanagement.entity.entityEnum.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillingDocumentServiceImpl implements FillingDocumentService {

    private final FillingDocumentRepository documentRepository;
    private final TaxFillingRepository taxFilingRepository;
    private final AuditLogService auditLogService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public FillingDocumentResponseDTO addDocument(FillingDocumentRequestDTO dto) {
        TaxFilling filing = taxFilingRepository.findById(dto.getFilingId())
                .orElseThrow(() -> new RuntimeException("Filing not found"));

        FillingDocument document = FillingDocument.builder()
                .filing(filing)
                .fileUrl(dto.getFileUrl())
                .build();

        FillingDocument savedDoc = documentRepository.save(document);

        auditLogService.record("DOCUMENT_UPLOAD", "filing_documents/" + savedDoc.getId());
        return FillingDocumentResponseDTO.builder()
                .id(savedDoc.getId())
                .filingId(savedDoc.getFiling().getId())
                .fileUrl(savedDoc.getFileUrl())
                .uploadedDate(savedDoc.getUploadedDate())
                .build();
    }

    @Override
    @Transactional
    public List<FillingDocumentResponseDTO> getDocumentsByFiling(Long filingId) {
        auditLogService.record("DOCUMENT_LIST_VIEW", "filings/" + filingId + "/documents");
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        if(user.getRole().equals(UserRole.TAXPAYER)) {
            Taxpayer taxpayer = user.getTaxpayer();
            List<TaxFilling> taxfilings = taxpayer.getTaxFillings();
            long c = taxfilings.stream().filter(filing -> filing.getId().equals(filingId)).count();
            if (c == 0) {
                throw new AccessDeniedException("Access Denied");
            }
        }
        return documentRepository.findByFilingId(filingId).stream()
                .map(doc -> FillingDocumentResponseDTO.builder()
                        .id(doc.getId())
                        .filingId(doc.getFiling().getId())
                        .fileUrl(doc.getFileUrl())
                        .uploadedDate(doc.getUploadedDate())
                        .build())
                .collect(Collectors.toList());
    }
}
