package com.example.transaction2.controller;

import com.example.transaction2.entity.User;
import com.example.transaction2.payload.SignDTO;
import com.example.transaction2.payload.SignInDTO;
import com.example.transaction2.payload.TokenDTO;
import com.example.transaction2.payload.VerificationDTO;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthControllerImpl implements AuthController{
    private final AuthService authService;

    @Override
    public ApiResult<VerificationDTO> signUp(@RequestBody @Valid SignDTO signDTO) {
        return authService.signUp(signDTO);
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signDTO) {
        return authService.signIn(signDTO);
    }

    @Override
    public ApiResult<?> delete(User user) {
        return authService.delete(user);
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        return authService.refreshToken(accessToken,refreshToken);
    }

}
