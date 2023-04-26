package com.example.transaction2.service;

import com.example.transaction2.entity.User;
import com.example.transaction2.payload.SignDTO;
import com.example.transaction2.payload.TokenDTO;
import com.example.transaction2.payload.VerificationDTO;
import com.example.transaction2.response.ApiResult;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface AuthService extends UserDetailsService {
    ApiResult<VerificationDTO> signUp(SignDTO signDTO);
    Optional<User> getUserById(UUID id);

    ApiResult<TokenDTO> signIn(SignDTO signDTO);
    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);
    ApiResult<?> delete(User user);
}
