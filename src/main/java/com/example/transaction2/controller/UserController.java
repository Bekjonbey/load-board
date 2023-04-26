package com.example.transaction2.controller;

import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }
    @GetMapping("/yopiq")
    public ApiResult<Boolean> yopiq(@PathVariable UUID id) {
        return ApiResult.successResponse("yopiq yol",true);
    }
}
