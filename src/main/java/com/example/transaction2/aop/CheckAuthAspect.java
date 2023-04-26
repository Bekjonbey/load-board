package com.example.transaction2.aop;


import com.example.transaction2.entity.User;
import com.example.transaction2.entity.enums.PermissionEnum;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

import static com.example.transaction2.security.CurrentUserHelper.currentRequest;


@Slf4j
@Order(value = 1)
@Aspect
@Component
@RequiredArgsConstructor
public class CheckAuthAspect {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Before(value = "@annotation(checkAuth)")
    public void checkAuthExecutor(CheckAuth checkAuth) {
        check(checkAuth);
    }


    public void check(CheckAuth checkAuth) {

        HttpServletRequest httpServletRequest = currentRequest();

        String token = getTokenFromRequest(httpServletRequest);

        User user = jwtAuthenticationFilter.getUserFromBearerToken(token);

        if (user != null && user.getId()!=null) {

            PermissionEnum[] permission = checkAuth.permission();
            if (permission.length > 0 && notPermission(user.getRole().getPermissions(), permission)) {
                throw RestException.restThrow("FORBIDDEN", HttpStatus.BAD_REQUEST);
            }

            httpServletRequest.setAttribute("REQUEST_ATTRIBUTE_CURRENT_USER", user);
        } else
            throw RestException.restThrow("FORBIDDEN", HttpStatus.BAD_REQUEST);

    }


    private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
        try {
            String token = httpServletRequest.getHeader("AUTHORIZATION_HEADER");
            if (Objects.isNull(token) || token.isEmpty()) {
                throw RestException.restThrow("FORBIDDEN", HttpStatus.FORBIDDEN);
            }
            return token;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private boolean notPermission(Set<PermissionEnum> hasPermission, PermissionEnum[] mustPermission) {
        if (Objects.isNull(hasPermission) || hasPermission.isEmpty()) {
            return true;
        }
        for (PermissionEnum permissionEnum : mustPermission) {
            if (hasPermission.contains(permissionEnum))
                return false;
        }
        return true;
    }

    private boolean notPermission(String permission, PermissionEnum[] mustPermission) {
        if (permission == null || permission.isEmpty())
            return true;
        for (PermissionEnum permissionEnum : mustPermission) {
            if (permission.contains(permissionEnum.name()))
                return false;
        }
        return true;
    }

}
