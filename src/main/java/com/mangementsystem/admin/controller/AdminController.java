package com.mangementsystem.admin.controller;

import com.mangementsystem.admin.dto.AdminLoginRequest;
import com.mangementsystem.admin.dto.AdminSignupResponse;
import com.mangementsystem.admin.dto.AdminSignupRequest;
import com.mangementsystem.admin.dto.SessionAdmin;
import com.mangementsystem.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admins/signup")
    public ResponseEntity<AdminSignupResponse> adminSignup(@Valid @RequestBody AdminSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    @PostMapping("/admins/login")
    public ResponseEntity<Void> adminLogin(@Valid @RequestBody AdminLoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}

