package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.dto.requestdto.TaxpayerRegistrationRequestDto;
import com.cts.taxeasemanagement.dto.responsedto.TaxpayerRegistrationResponseDto;

public interface TaxpayerRegistrationService {
    TaxpayerRegistrationResponseDto registerTaxpayer(TaxpayerRegistrationRequestDto request);
}
