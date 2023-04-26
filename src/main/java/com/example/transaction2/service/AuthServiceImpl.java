package com.example.transaction2.service;

import com.example.transaction2.entity.Role;
import com.example.transaction2.entity.User;
import com.example.transaction2.entity.enums.RoleEnum;
import com.example.transaction2.exception.RestException;
import com.example.transaction2.payload.ErrorData;
import com.example.transaction2.payload.SignDTO;
import com.example.transaction2.payload.TokenDTO;
import com.example.transaction2.payload.VerificationDTO;
import com.example.transaction2.repository.RoleRepository;
import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.security.JwtTokenProvider;
import com.example.transaction2.util.StringHelper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ValidatorService validator;

    public AuthServiceImpl(@Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, ValidatorService validator) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.validator = validator;
    }


    @Override
    @Transactional
    public ApiResult<VerificationDTO> signUp(SignDTO signDTO) {
        List<ErrorData> errors = validator.validateUser(signDTO);
        if(!errors.isEmpty())
            throw RestException.restThrow("username or password is not valid",HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setPhone(signDTO.getPhone());
        user.setPassword(passwordEncoder.encode(signDTO.getPassword()));
        user.setEnabled(true);
        Role role  = roleRepository.findByName(RoleEnum.USER.name())
                .orElseThrow(()->RestException.restThrow("ROLE_NOT_FOUND",HttpStatus.BAD_REQUEST));
        user.setRole(role);

        String verificationCode = StringHelper.generateVerificationCode();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
        System.out.println(user);
            return ApiResult.successResponse(VerificationDTO.builder().phoneNumber(user.getPhone()).verificationCode(verificationCode).build());
        }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return userRepository
                .findByPhone(phone)
                .orElseThrow(
                        () -> RestException.restThrow(String.format("%s phone not found", phone), HttpStatus.UNAUTHORIZED));
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignDTO signDTO) {
        User user;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signDTO.getPhone(),
                            signDTO.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

              user = (User) authentication.getPrincipal();
        } catch (DisabledException | LockedException | CredentialsExpiredException disabledException) {
            throw RestException.restThrow("USER_NOT_FOUND_OR_DISABLED", HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException | UsernameNotFoundException badCredentialsException) {
            throw RestException.restThrow("LOGIN_OR_PASSWORD_ERROR", HttpStatus.FORBIDDEN);
        }
        LocalDateTime tokenIssuedAt = LocalDateTime.now();
        String accessToken = jwtTokenProvider.generateAccessToken(user, Timestamp.valueOf(tokenIssuedAt));
        System.out.println(accessToken);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

            user.setTokenIssuedAt(tokenIssuedAt);
            userRepository.save(user);


            TokenDTO tokenDTO = TokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
            return ApiResult.successResponse(
                "SUCCESSFULLY_TOKEN_GENERATED",tokenDTO);
    }
    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();
        try {
            jwtTokenProvider.checkToken(accessToken, true);
        } catch (ExpiredJwtException ex) {
            try {
                String userId = jwtTokenProvider.getUserIdFromToken(refreshToken, false);
                User user = getUserById(UUID.fromString(userId)).orElseThrow(() -> RestException.restThrow("Conflict",HttpStatus.CONFLICT));

                if (!user.isEnabled()
                    || !user.isAccountNonExpired()
                        || !user.isAccountNonLocked()
                        || !user.isCredentialsNonExpired())
                    throw RestException.restThrow("Unauthorized", HttpStatus.UNAUTHORIZED);


                LocalDateTime tokenIssuedAt = LocalDateTime.now();
                String newAccessToken = jwtTokenProvider.generateAccessToken(user, Timestamp.valueOf(tokenIssuedAt));
                String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

                user.setTokenIssuedAt(tokenIssuedAt);
                userRepository.save(user);

                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                return ApiResult.successResponse(tokenDTO);
            } catch (Exception e) {
                throw RestException.restThrow("REFRESH_TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow("WRONG_ACCESS_TOKEN", HttpStatus.UNAUTHORIZED);
        }

        throw RestException.restThrow("ACCESS_TOKEN_NOT_EXPIRED", HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ApiResult<?> delete(User user) {

        userRepository.delete(user);
        return ApiResult.successResponse("SUCCESSFULLY_DELETED");
    }

}
