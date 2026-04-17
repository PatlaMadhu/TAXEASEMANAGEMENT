package com.cts.taxeasemanagement.controller;

import com.cts.taxeasemanagement.dto.requestdto.LoginRequestDto;
import com.cts.taxeasemanagement.dto.requestdto.TaxpayerRegistrationRequestDto;
import com.cts.taxeasemanagement.dto.responsedto.LoginResponseDto;
import com.cts.taxeasemanagement.dto.responsedto.TaxpayerRegistrationResponseDto;
import com.cts.taxeasemanagement.service.AuthService;
import com.cts.taxeasemanagement.service.TaxpayerRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthController {

    private final TaxpayerRegistrationService registrationService;
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<TaxpayerRegistrationResponseDto> register(
            @Valid @RequestBody TaxpayerRegistrationRequestDto request) {
        log.info("START: Registering taxpayer with email: {}", request.getEmail());
        TaxpayerRegistrationResponseDto response = registrationService.registerTaxpayer(request);
        log.info("END: Registration successful for user ID: {}", response.getTaxpayerIdNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {
        log.info("START: Login attempt for user: {}", request.getEmail());
        LoginResponseDto response = authService.login(request);
        log.info("END: Login successful");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }
}
