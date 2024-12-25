package com.example.transaction2.controller;

import com.example.transaction2.entity.Admin;
import com.example.transaction2.payload.AdminLoginDto;
import com.example.transaction2.payload.NewsCreateDto;
import com.example.transaction2.payload.NewsDto;
import com.example.transaction2.response.ApiResult;
import com.example.transaction2.service.AdminService;
import com.example.transaction2.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminLoginDto adminLoginDto) {
        Admin login = adminService.login(adminLoginDto);
        if (login != null) {
            return ResponseEntity.ok(login.getKey().toString());
        } else
            return ResponseEntity.ok("login failed");
    }
}
