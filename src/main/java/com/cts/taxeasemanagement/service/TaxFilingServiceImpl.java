package com.cts.taxeasemanagement.service;


import com.cts.taxeasemanagement.dto.requestdto.TaxFillingRequestDTO;
import com.cts.taxeasemanagement.dto.responsedto.TaxFillingResponseDTO;
import com.cts.taxeasemanagement.entity.TaxFilling;
import com.cts.taxeasemanagement.entity.Taxpayer;
import com.cts.taxeasemanagement.entity.User;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import com.cts.taxeasemanagement.dao.TaxFillingRepository;
import com.cts.taxeasemanagement.dao.TaxpayerRepository;
import com.cts.taxeasemanagement.dao.UserRepository;
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
public class TaxFilingServiceImpl implements TaxFillingService {

    private final TaxFillingRepository taxFilingRepository;
    private final TaxpayerRepository taxpayerRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public TaxFillingResponseDTO submitFiling(TaxFillingRequestDTO dto) {
        Taxpayer taxpayer = taxpayerRepository.findById(dto.getTaxpayerId())
                .orElseThrow(() -> new RuntimeException("Taxpayer not found"));

        TaxFilling filing = TaxFilling.builder()
                .taxpayer(taxpayer)
                .period(dto.getPeriod())
                .amountDeclared(dto.getAmountDeclared())
                .status(StatusBasic.Pending)
                .build();

        TaxFilling savedFiling = taxFilingRepository.save(filing);
        auditLogService.record("FILING_SUBMIT", "tax_filings/" + savedFiling.getId());
        return mapToDTO(savedFiling);
    }

    @Override
    @Transactional
    public List<TaxFillingResponseDTO> getFilingHistory(Long taxpayerId) {
        UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        if(user.getRole().equals(UserRole.TAXPAYER)) {
            Taxpayer taxpayer = user.getTaxpayer();

            if (!taxpayer.getId().equals(taxpayerId)) {
                throw new AccessDeniedException("Access Denied");
            }
        }
        auditLogService.record("FILING_HISTORY_VIEW", "taxpayer/" + taxpayerId + "/filings");
        return taxFilingRepository.findByTaxpayerId(taxpayerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaxFillingResponseDTO updateFilingStatus(Long filingId, String newStatus, Long officerId) {
        TaxFilling filing = taxFilingRepository.findById(filingId)
                .orElseThrow(() -> new RuntimeException("Filing not found"));

        filing.setStatus(StatusBasic.valueOf(newStatus));

        if (officerId != null) {
            User officer = userRepository.findById(officerId)
                    .orElseThrow(() -> new RuntimeException("Officer not found"));
            filing.setOfficer(officer);
        }
        TaxFilling updated = taxFilingRepository.save(filing);
        auditLogService.record("FILING_STATUS_UPDATE", "tax_filings/" + updated.getId());
        return mapToDTO(updated);
    }

    private TaxFillingResponseDTO mapToDTO(TaxFilling filing) {
        return TaxFillingResponseDTO.builder()
                .id(filing.getId())
                .taxpayerId(filing.getTaxpayer().getId())
                .period(filing.getPeriod())
                .amountDeclared(filing.getAmountDeclared())
                .status(filing.getStatus().name())
                .submittedDate(filing.getSubmittedDate())
                .build();
    }
}
