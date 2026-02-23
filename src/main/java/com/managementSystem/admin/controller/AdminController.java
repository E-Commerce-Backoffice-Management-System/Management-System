package com.managementSystem.admin.controller;

import com.managementSystem.admin.dto.*;
import com.managementSystem.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 관리자 회원가입
    @PostMapping("/admin/signup")
    public ResponseEntity<AdminSignupResponse> adminSignup(@Valid @RequestBody AdminSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    // 관리자 로그인
    @PostMapping("/admins/login")
    public ResponseEntity<Void> adminLogin(@Valid @RequestBody AdminLoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("sessionAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 승인 전용 API(슈퍼관리자가 승인) (approve)
    @PostMapping("/admins/{adminId}/approve")
    public ResponseEntity<Void> adminApprove(
            @PathVariable Long adminId,
            @SessionAttribute(name = "sessionAdmin") SessionAdmin loginAdmin) {
        // SessionAdmin loginAdmin = (SessionAdmin) session.getAttribute("sessionAdmin);
        // if(loginAdmin == null) {
        // throw new IllegalStateException("로그인이 필요합니다.") }
        // @SessionAttribute 어노테이션을 사용하지 않으면, 원래는 session.getAttribute가 Object를 반환하기 때문에
        // 직접 null체크와 형변환을 해줘야한다. @SessionAttribute 어노테이션은 스프링이 대신 해준다.

        // 만약 로그인 되어있지 않은 사용자가 adminApprove 메서드를 호출하면 null이 반환됨 -> NullPointerException 발생
        // 이 상황을 방지하기 위해 null체크를 해주고, 서비스 코드에 들어가기 전에 막아주는 것이 좋다.

        adminService.approveAdmin(adminId, loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 승인대기 상태의 관리자를 활성/거부 상태로 변경 (상태 통합 변경)
    @PatchMapping("/admins/{adminId}/adminStatus")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long adminId,
            @RequestBody AdminStatusRequest request,
            @SessionAttribute(name = "sessionAdmin") SessionAdmin loginAdmin) {
        adminService.updateAdminStatus(adminId, request, loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 관리자 로그아웃
    @PostMapping("/admins/logout")
    public ResponseEntity<Void> adminLogout(
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin sessionAdmin, HttpSession session) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 관리자 단건 조회
    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminGetResponse> getAdmin(
            @PathVariable Long adminId,
            @SessionAttribute(name = "sessionAdmin") SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdmin(adminId, loginAdmin));
    }

    //관리자 정보 수정
    @PostMapping("/admins/{adminId}/update")
    public ResponseEntity<AdminUpdateResponse> updateAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdmin(adminId, request, loginAdmin));
    }

    // 모두 조회 페이징.
    @GetMapping("/admins")
    public ResponseEntity<Page<AdminGetResponse>> getAllAdmin(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAllAdmin(page, size));
    }

    // 비밀번호 변경
    @PatchMapping("/admins/{adminId}/password")
    public ResponseEntity<AdminUpdatePasswordResponse> updatePassword(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdatePasswordRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminPassword(adminId, request, loginAdmin));
    }

    // 관리자 역할 변경
    @PatchMapping("/admins/{adminId}/role")
    public ResponseEntity<AdminRoleUpdateResponse> AdminRoleUpdate(
            @PathVariable Long adminId,
            @RequestBody AdminRoleUpdateRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateRole(adminId, request, loginAdmin));
    }

    // 관리자 상태 변경
    @PatchMapping("/admins/{adminId}/status")
    public ResponseEntity<AdminStatusUpdateResponse> AdminStatusUpdate(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminStatusUpdateRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateStatus(adminId, request, loginAdmin));
    }


    // 내 프로필 조회 (관리자 개인 프로필 조회)
    @GetMapping("/admins/{adminId}/profile")
    public ResponseEntity<AdminGetOneProfileResponse> getAdminProfile(
            @PathVariable Long adminId,
            @SessionAttribute(name = "sessionAdmin") SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getAdminProfile(adminId, loginAdmin));
    }


    // 내 프로필 수정(관리자 개인 프로필 수정)
    @PutMapping("/admins/{adminId}/profile")
    public ResponseEntity<AdminUpdateProfileResponse> updateProfile(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateProfileRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminProfile(adminId, request, loginAdmin));
    }

    // 관리자 삭제
    @DeleteMapping("/admin/{adminId}")
    public ResponseEntity<Void> deleteAdmin(
            @PathVariable Long adminId,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin loginAdmin) {
        adminService.AdminDelete(adminId, loginAdmin);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

