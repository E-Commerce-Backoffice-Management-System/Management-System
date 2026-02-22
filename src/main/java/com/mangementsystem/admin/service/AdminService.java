package com.mangementsystem.admin.service;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.admin.entity.AdminRole;
import com.mangementsystem.admin.entity.AdminStatus;
import com.mangementsystem.admin.repository.AdminRepository;
import com.mangementsystem.config.PasswordEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Transactional
    public AdminSignupResponse save(AdminSignupRequest request){
        if(adminRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getPhoneNumber(),
                request.getRole()
        );
        Admin savedAdmin = adminRepository.save(admin);
        return new AdminSignupResponse(
                savedAdmin.getId(),
                savedAdmin.getName(),
                savedAdmin.getEmail(),
                savedAdmin.getPhoneNumber(),
                savedAdmin.getRole(),
                savedAdmin.getCreatedAt(),
                savedAdmin.getUpdatedAt()
        );
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public AdminGetOneResponse getOneAdmin(Long adminId) {

        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException("없는 일정이거나, 유저정보가 없습니다.")
        );

        return new AdminGetOneResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        );
    }


    // 모두 조회
    @Transactional(readOnly = true)
    public Page<AdminGetOneResponse> getAllAdmin(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Admin> admins=adminRepository.findAll(pageable);


        return admins.map(admin -> new AdminGetOneResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        ));
    }


    // 관리자 정보 수정
    @Transactional
    public AdminUpdateResponse updateAdmin(SessionAdmin sessionAdmin,Long adminId, AdminUpdateRequest request) {
        UserLoginId(sessionAdmin,adminId);
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException(" 정보가 없습니다.")
        );
        admin.AdminUpdate(request.getName(),request.getEmail(),request.getPhoneNumber());
        return new AdminUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }


    // 삭제
    @Transactional
    public void AdminDelete(SessionAdmin admin,Long adminId) {
        boolean existence = adminRepository.existsById(adminId);
        if (!existence) {
            throw new IllegalStateException("없습니다.");
        }

        adminRepository.deleteById(adminId);
    }

    @Transactional(readOnly = true)
    public SessionAdmin login(@Valid AdminLoginRequest request){
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("가입되지 않은 이메일 입니다.")
        );
        boolean match = passwordEncoder.matches(request.getPassword(), admin.getPassword());
        if(!match){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        if(admin.getStatus().equals(AdminStatus.PENDING)){
            throw new IllegalStateException("승인 대기중입니다.");
        }
        if(admin.getStatus().equals(AdminStatus.INACTIVE)){
            throw new IllegalStateException("비활성 상태입니다.");
        }
        if(admin.getStatus().equals(AdminStatus.REJECTED)){
            throw new IllegalStateException("거부되었습니다.");
        }
        if(admin.getStatus().equals(AdminStatus.SUSPENDED)){
            throw new IllegalStateException("정지된 계정입니다.");
        }
        return new SessionAdmin (
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );
    }

    @Transactional
    public void approveAdmin(Long adminId, SessionAdmin loginAdmin){
        if((loginAdmin.getRole() != AdminRole.SUPER_ADMIN)) {
            throw new IllegalStateException("승인 권한이 없습니다");
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("해당 관리자를 찾을 수 없습니다.")
        );
        admin.approve();

    }

    private SessionAdmin adminLogin(SessionAdmin admin){
        if(admin == null){
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return admin;
    }
    // 권한 기능.
    private void UserLoginId(SessionAdmin admin, Long userId) {
        adminLogin(admin);
        if (!admin.getId().equals(userId)) {
            throw new IllegalStateException("권한이 없습니다.");
        }
    }


    // 관리자 역할 변경
    @Transactional
    public AdminRoleUpdateResponse updateRole(SessionAdmin sessionAdmin,Long adminId, AdminRoleUpdateRequest request) {
        UserLoginId(sessionAdmin,adminId);
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException("정보가 없습니다.")
        );
        admin.AdminRoleUpdate(request.getRole());
        return new AdminRoleUpdateResponse(
                admin.getRole()
        );

    }


    // 관리자 상태 변경
    @Transactional
    public AdminStatusUpdateResponse updateStatus(SessionAdmin sessionAdmin,Long adminId, AdminStatusUpdateRequest request) {
        UserLoginId(sessionAdmin,adminId);
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException("정보가 없습니다.")
        );
        admin.AdminStatusUpdate(request.getStatus());
        return new AdminStatusUpdateResponse(
                admin.getStatus()
        );
    }

    // 관리자 프로필 조회
    @Transactional(readOnly = true)
    public AdminGetOneProfileResponse getOneAdminProfile(Long adminId) {
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException("없는 일정이거나, 유저정보가 없습니다.")
        );
        return new AdminGetOneProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 관리자 프로필 수정
    @Transactional
    public AdminUpdateProfileResponse getUpdateAdminProfile(
            SessionAdmin sessionAdmin
            ,Long adminId,
            @Valid AdminUpdateProfileRequest request

    ) {
        UserLoginId(sessionAdmin,adminId);
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException("정보가 없습니다.")
        );
        admin.AdminUpdateProfile(request.getName(),request.getEmail(),request.getPhoneNumber());

        return new AdminUpdateProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getName()
        );


    }

    // 관리자 비밀번호 변경
    @Transactional
    public AdminUpdatePasswordResponse updateAdminPassword(SessionAdmin sessionAdmin,Long adminId, @Valid AdminUpdatePasswordRequest request) {
        UserLoginId(sessionAdmin,adminId);
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException(" 정보가 없습니다.")
        );
        admin.AdminUpdatePassword(request.getPassword());

        return new AdminUpdatePasswordResponse(
                admin.getName()
        );
    }
}
