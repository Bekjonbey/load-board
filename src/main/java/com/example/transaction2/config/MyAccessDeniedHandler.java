package com.example.transaction2.config;

import com.example.transaction2.exception.ExceptionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private final ExceptionHelper exceptionHelper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        System.out.println("___ ".repeat(8));
        ResponseEntity<?> responseEntity = exceptionHelper.handleException(accessDeniedException);
        System.out.println(responseEntity);
    }
}
