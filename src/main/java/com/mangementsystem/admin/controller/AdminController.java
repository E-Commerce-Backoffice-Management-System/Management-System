package com.mangementsystem.admin.controller;

import com.mangementsystem.admin.dto.AdminLoginRequest;
import com.mangementsystem.admin.dto.AdminSignupResponse;
import com.mangementsystem.admin.dto.AdminSignupRequest;
import com.mangementsystem.admin.service.AdminService;
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

    @PostMapping("/admin/signup")
    public ResponseEntity<AdminSignupResponse> adminSignup(@RequestBody AdminSignupRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

//    @PostMapping("/admin/login")
//    public ResponseEntity<Void> adminLogin(@RequestBody AdminLoginRequest){
//        return ResponseEntity.status(HttpStatus.OK).body();
//    }
//    }
}
