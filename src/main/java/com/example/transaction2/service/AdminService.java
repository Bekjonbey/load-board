package com.example.transaction2.service;

import com.example.transaction2.entity.Admin;
import com.example.transaction2.payload.AdminLoginDto;
import com.example.transaction2.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin login(AdminLoginDto adminLoginDto) {
        if (adminRepository.findByUsernameAndPassword(adminLoginDto.getUsername(), adminLoginDto.getPassword()).isPresent()) {
            return adminRepository.findByUsernameAndPassword(adminLoginDto.getUsername(), adminLoginDto.getPassword()).get();
        }
        else return null;
    }
}
