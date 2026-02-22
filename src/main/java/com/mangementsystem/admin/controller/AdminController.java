package com.mangementsystem.admin.controller;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/admin/signup")
    public ResponseEntity<AdminSignupResponse> adminSignup(@Valid @RequestBody AdminSignupRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    @PostMapping("/admins/login")
    public ResponseEntity<Void> adminLogin(@Valid @RequestBody AdminLoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/admins/{adminId}/approve")
    public ResponseEntity<Void> adminApprove(@PathVariable Long adminId, HttpSession session) {
        SessionAdmin loginAdmin = (SessionAdmin) session.getAttribute("sessionAdmin");
        if (loginAdmin == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        adminService.approveAdmin(adminId, loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 단건 조회
    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminGetOneResponse> getOneAdmin(
            @PathVariable Long adminId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getOneAdmin(adminId));
    }

    @PostMapping("/admins/logout")
    public ResponseEntity<Void> adminLogout(
            @SessionAttribute(name = "sessionAdmin", required = false ) SessionAdmin sessionAdmin, HttpSession session) {
        if(sessionAdmin == null){
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 모두 조회 페이징.
    @GetMapping("/admins")
    public ResponseEntity<Page<AdminGetOneResponse>> getAllAdmin(
            @RequestParam (defaultValue = "1") int page,
            @RequestParam (defaultValue = "10") int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmin(page,size));
    }


    // 관리자 수정
    @PutMapping("/admins/{adminId}")
    public ResponseEntity<AdminUpdateResponse> UpdateAdmin(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateRequest request

    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(sessionAdmin,adminId,request));
    }

    // 관리자 비밀번호 변경
    @PatchMapping("/admins/{adminId}")
    public ResponseEntity<AdminUpdatePasswordResponse> UpdateAdminPassword(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdatePasswordRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminPassword(sessionAdmin,adminId,request));

    }


    // 관리자 권한 변경
    @PatchMapping("/admins/{adminId}/role")
    public ResponseEntity<AdminRoleUpdateResponse> updateAdminRole(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @RequestBody AdminRoleUpdateRequest request
    ) {
        adminService.updateRole(sessionAdmin,adminId, request);
        return ResponseEntity.ok().build();
    }

    // 관리자 상태 변경
    @PatchMapping("/admins/{adminId}/status")
    public ResponseEntity<AdminRoleUpdateResponse> updateAdminStatus(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @RequestBody AdminStatusUpdateRequest request
    ) {
        adminService.updateStatus(sessionAdmin,adminId, request);
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
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateProfileRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getUpdateAdminProfile(sessionAdmin,adminId,request));
    }


    // 관리자 삭제
    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<Void> deleteUsers(
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long adminId
    ) {

        adminService.AdminDelete(sessionAdmin,adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
