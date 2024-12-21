package com.example.transaction2.service;

import com.example.transaction2.payload.AdminLoginDto;
import com.example.transaction2.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean login(AdminLoginDto adminLoginDto) {
        return adminRepository.findByUsernameAndPassword(adminLoginDto.getUsername(), adminLoginDto.getPassword()).isPresent();
    }
}
