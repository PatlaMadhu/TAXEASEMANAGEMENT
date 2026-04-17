package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.dto.responsedto.TaxpayerDocumentResponseDto;
import com.cts.taxeasemanagement.dto.responsedto.TaxpayerProfileResponseDto;
import com.cts.taxeasemanagement.dto.requestdto.UpdateTaxpayerProfileRequestDto;
import com.cts.taxeasemanagement.entity.entityEnum.DocTypeTaxpayer;

import java.util.List;

public interface TaxpayerProfileService {
    TaxpayerProfileResponseDto getProfile(String email);
    TaxpayerProfileResponseDto updateProfile(String email, UpdateTaxpayerProfileRequestDto request);
    List<TaxpayerDocumentResponseDto> getDocuments(String email);
    TaxpayerDocumentResponseDto uploadDocument(String email, String fileUri, DocTypeTaxpayer docType);
    void deleteDocument(String email, Long documentId);
    TaxpayerDocumentResponseDto updateDocument(String email, Long documentId, String fileUri);
}