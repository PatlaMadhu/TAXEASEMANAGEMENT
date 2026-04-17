package com.cts.taxeasemanagement.service.impl;

import com.cts.taxeasemanagement.dto.requestdto.LoginRequestDto;
import com.cts.taxeasemanagement.dto.responsedto.LoginResponseDto;
import com.cts.taxeasemanagement.service.AuthService;
import com.cts.taxeasemanagement.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        String token = authUtil.generateToken(dto.getEmail());
        return new LoginResponseDto(token);
    }
}
