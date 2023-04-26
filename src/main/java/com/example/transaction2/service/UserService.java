package com.example.transaction2.service;

import com.example.transaction2.repository.UserRepository;
import com.example.transaction2.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public ApiResult<Boolean> delete(UUID id) {
        userRepository.deleteById(id);
//        User user = userRepository.findByPhone(phone)
//                .orElseThrow(() -> RestException.restThrow("No such user", HttpStatus.NOT_FOUND));
//        System.out.println(user);
//        userRepository.delete(user);
        return ApiResult.successResponse();
    }
}
