package com.cts.taxeasemanagement.service.impl;

import com.cts.taxeasemanagement.dao.TaxpayerRepository;
import com.cts.taxeasemanagement.dao.UserRepository;
import com.cts.taxeasemanagement.dto.requestdto.TaxpayerRegistrationRequestDto;
import com.cts.taxeasemanagement.dto.responsedto.TaxpayerRegistrationResponseDto;
import com.cts.taxeasemanagement.entity.Taxpayer;
import com.cts.taxeasemanagement.entity.User;
import com.cts.taxeasemanagement.entity.entityEnum.StatusBasic;
import com.cts.taxeasemanagement.entity.entityEnum.UserRole;
import com.cts.taxeasemanagement.service.TaxpayerRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaxpayerRegistrationServiceImpl implements TaxpayerRegistrationService {

    private final UserRepository userRepository;
    private final TaxpayerRepository taxpayerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TaxpayerRegistrationResponseDto registerTaxpayer(TaxpayerRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.TAXPAYER)
                .status(StatusBasic.Active)
                .build();
        User saved = userRepository.save(user);

        Taxpayer taxpayer = Taxpayer.builder()
                .user(saved)
                .name(saved.getName())
                .build();
        taxpayerRepository.save(taxpayer);

        return TaxpayerRegistrationResponseDto.builder()
                .taxpayerIdNumber(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }
}
