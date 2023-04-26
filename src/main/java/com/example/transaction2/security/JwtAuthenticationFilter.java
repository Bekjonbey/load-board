package com.example.transaction2.security;

import com.example.transaction2.entity.User;
import com.example.transaction2.service.AuthService;
import com.example.transaction2.util.RestConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   @Lazy AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            setUserPrincipalIfAllOk(httpServletRequest);
        } catch (Exception e) {
            log.error("Error in JwtAuthenticationFilter setUserPrincipalIfAllOk method: ", e);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setUserPrincipalIfAllOk(HttpServletRequest request) {
        String authorization = request.getHeader(RestConstants.AUTHENTICATION_HEADER);
        if (authorization != null) {
            User user = getUserFromBearerToken(authorization);
            if (user != null) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null,null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }


    public User getUserFromBearerToken(String token) {
        try {
            token = token.substring("Bearer".length()).trim();
            if (jwtTokenProvider.validToken(token, true)) {
                String userId = jwtTokenProvider.getUserIdFromToken(token, true);
                return authService.getUserById(UUID.fromString(userId)).orElseThrow(RuntimeException::new);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
