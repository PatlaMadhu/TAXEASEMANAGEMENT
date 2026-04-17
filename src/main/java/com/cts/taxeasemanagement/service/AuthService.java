package com.cts.taxeasemanagement.service;

import com.cts.taxeasemanagement.dto.requestdto.LoginRequestDto;
import com.cts.taxeasemanagement.dto.responsedto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);
}
