package com.mangementsystem.admin.controller;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin/signup")
    public ResponseEntity<AdminSignupResponse> adminSignup(@RequestBody AdminSignupRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    @PostMapping("/admins/login")
    public ResponseEntity<Void> adminLogin(@Valid @RequestBody AdminLoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    @PostMapping("/admin/login")
//    public ResponseEntity<Void> adminLogin(@RequestBody AdminLoginRequest){
//        return ResponseEntity.status(HttpStatus.OK).body();
//    }
//    }

    // 단건 조회
    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminGetOneResponse> getOneAdmin(
            @PathVariable Long adminId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdmin(adminId));
    }

    // 모두 조회
    @GetMapping("/admins")
    public ResponseEntity<List<AdminGetOneResponse>> getAllAdmin(

    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmin());
    }


    // 관리자 수정
    @PutMapping("/admins/{adminId}")
    public ResponseEntity<AdminUpdateResponse> UpdateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(adminId,request));
    }

    // 관리자 비밀번호 변경
    @PatchMapping("/admins/{adminId}")
    public ResponseEntity<AdminUpdatePasswordResponse> UpdateAdminPassword(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdatePasswordRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminPassword(adminId,request));

    }


    // 관리자 권한 변경
    @PatchMapping("/admins/{adminId}/role")
    public ResponseEntity<AdminRoleUpdateResponse> updateAdminRole(
            @PathVariable Long adminId,
            @RequestBody AdminRoleUpdateRequest request
    ) {
        adminService.updateRole(adminId, request);
        return ResponseEntity.ok().build();
    }

    // 관리자 상태 변경
    @PatchMapping("/admins/{adminId}/status")
    public ResponseEntity<AdminRoleUpdateResponse> updateAdminStatus(
            @PathVariable Long adminId,
            @RequestBody AdminStatusUpdateRequest request
    ) {
        adminService.updateStatus(adminId, request);
        return ResponseEntity.ok().build();
    }

    // 관리자 프로필 조회
    @GetMapping("/admins/{adminId}/profile")
    public ResponseEntity<AdminGetOneProfileResponse> getOneAdminProfile(
            @PathVariable Long adminId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdminProfile(adminId));
    }

    // 관리자 프로필 수정
    @PutMapping("/admins/{adminId}/profile")
    public ResponseEntity<AdminUpdateProfileResponse> getUpdateAdminProfile(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateProfileRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUpdateAdminProfile(adminId,request));
    }


    // 관리자 삭제
    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<Void> deleteUsers(
            @PathVariable Long adminId
    ) {

        adminService.Admindelete(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
