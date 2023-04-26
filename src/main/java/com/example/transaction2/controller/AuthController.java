package com.example.transaction2.controller;

import com.example.transaction2.entity.User;
import com.example.transaction2.payload.SignDTO;
import com.example.transaction2.payload.SignInDTO;
import com.example.transaction2.payload.TokenDTO;
import com.example.transaction2.payload.VerificationDTO;
import com.example.transaction2.response.ApiResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = AuthController.AUTH_CONTROLLER_BASE_PATH)

public interface AuthController {
    String AUTH_CONTROLLER_BASE_PATH = "/api/auth";
    String SIGN_IN_PATH = "/sign-in";
    String REFRESH_TOKEN_PATH = "/refresh-token";
    String SIGN_UP_PATH = "/sign-up";
    String DELETE_PATH = "/delete";
    @PostMapping(value = SIGN_UP_PATH)
    ApiResult<VerificationDTO> signUp(@RequestBody @Valid SignDTO signDTO);
    @PostMapping(value = SIGN_IN_PATH)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignInDTO signDTO);
    @DeleteMapping(path = DELETE_PATH)
    ApiResult<?> delete(User user);


    @GetMapping(value = REFRESH_TOKEN_PATH)
    ApiResult<TokenDTO> refreshToken(@RequestHeader(value = "Authorization") String accessToken,
                                     @RequestHeader(value = "RefreshToken") String refreshToken);


}
