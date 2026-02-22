package com.mangementsystem.admin.service;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.admin.entity.AdminRole;
import com.mangementsystem.admin.entity.AdminStatus;
import com.mangementsystem.admin.repository.AdminRepository;
import com.mangementsystem.config.PasswordEncoder;
import com.mangementsystem.exception.AdminException;
import com.mangementsystem.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminSignupResponse save(AdminSignupRequest request){
        if(adminRepository.existsByEmail(request.getEmail())){
            throw new AdminException(ErrorCode.DUPLICATE_EMAIL);
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

    // 관리자 단건 조회
    @Transactional(readOnly = true)
    public AdminGetResponse getAdmin(Long adminId, SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        return new AdminGetResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        );
    }

    // 관리자 전체 조회
    @Transactional(readOnly = true)
    public List<AdminGetResponse> getAdmins(SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(admin -> new AdminGetResponse(
                        admin.getId(),
                        admin.getName(),
                        admin.getEmail(),
                        admin.getPhoneNumber(),
                        admin.getRole(),
                        admin.getStatus(),
                        admin.getCreatedAt(),
                        admin.getUpdatedAt()
                )).toList();
    }

    // 괸리자 정보 수정 (슈퍼관리자 권한 확인 로직)
    @Transactional(readOnly = true)
    public AdminUpdateResponse updateAdmin(Long adminId, AdminUpdateRequest request,  SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        admin.AdminUpdate(request.getName(),request.getEmail(),request.getPhoneNumber());
        return new AdminUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 관리자 삭제, 슈퍼관리자인지 삭제 권한 확인, SoftDelete로 삭제
    @Transactional
    public void AdminDelete(Long adminId, SessionAdmin loginAdmin) {
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        // Soft Delete
        admin.AdminStatusUpdate(AdminStatus.INACTIVE);

    }
    // 로그인 검증
    @Transactional(readOnly = true)
    public SessionAdmin login(@Valid AdminLoginRequest request){
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AdminException(ErrorCode.EMAIL_NOT_FOUND)
        );
        boolean match = passwordEncoder.matches(request.getPassword(), admin.getPassword());
        if(!match){
            throw new AdminException(ErrorCode.MISTAKE_PASSWORD);
        }
        if(admin.getStatus().equals(AdminStatus.PENDING)){
            throw new AdminException(ErrorCode.PENDING_ADMIN);
        }
        if(admin.getStatus().equals(AdminStatus.INACTIVE)){
            throw new AdminException(ErrorCode.INACTIVE_ADMIN);
        }
        if(admin.getStatus().equals(AdminStatus.REJECTED)){
            throw new AdminException(ErrorCode.REJECTED_ADMIN);
        }
        if(admin.getStatus().equals(AdminStatus.SUSPENDED)){
            throw new AdminException(ErrorCode.SUSPENDED_ADMIN);
        }
        return new SessionAdmin (
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );
    }
    // 슈퍼관라자가 로그인 승인, 권한 확인
    @Transactional
    public void approveAdmin(Long adminId, SessionAdmin loginAdmin){
        if((loginAdmin.getRole() != AdminRole.SUPER_ADMIN)) {
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        admin.approve();
    }

    // 슈퍼관리자가 승인 대기 상태의 관리자를 활성/거부 상태 변경
    @Transactional
    public void updateAdminStatus(Long adminId, AdminStatusRequest request, SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        if(request.getStatus() == AdminStatus.ACTIVE){
            admin.approve();
        } else if(request.getStatus() == AdminStatus.REJECTED){
            admin.reject(request.getRejectReason());
        } else {
            throw new IllegalStateException("잘못된 상태 변경 요청입니다.");
        }
    }

    private SessionAdmin adminLogin(SessionAdmin admin){
        if(admin == null){
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return admin;
    }

    // 관리자 역할 변경
    @Transactional
    public AdminRoleUpdateResponse updateRole(Long adminId, AdminRoleUpdateRequest request, SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        admin.AdminRoleUpdate(request.getRole());
        return new AdminRoleUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getRole()
        );
    }

    // 관리자 상태 변경 (슈퍼관리자가 맞는지 확인)
    @Transactional
    public AdminStatusUpdateResponse updateStatus(Long adminId, AdminStatusUpdateRequest request, SessionAdmin loginAdmin){
        if(loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        admin.AdminStatusUpdate(request.getStatus());
        return new AdminStatusUpdateResponse(
                admin.getStatus()
        );
    }

    // 내 프로필 조회 (단건 조회, 본인이 맞는지 확인)
    @Transactional(readOnly = true)
    public AdminGetOneProfileResponse getAdminProfile(Long adminId, SessionAdmin loginAdmin){
        if(!loginAdmin.getId().equals(adminId)){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );

        return new AdminGetOneProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 내 프로필 수정 (본인이 맞는지 확인)
    @Transactional
    public AdminUpdateProfileResponse updateAdminProfile(Long adminId, AdminUpdateProfileRequest request, SessionAdmin loginAdmin){
        if(!loginAdmin.getId().equals(adminId)){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        admin.AdminUpdateProfile(request.getName(), request.getEmail(), request.getPhoneNumber());
        return new AdminUpdateProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 비밀번호 변경
    @Transactional
    public AdminUpdatePasswordResponse updateAdminPassword(Long adminId, AdminUpdatePasswordRequest request, SessionAdmin loginAdmin){
        if(!loginAdmin.getId().equals(adminId)){
            throw new AdminException(ErrorCode.NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.USER_NOT_FOUND)
        );
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        admin.AdminUpdatePassword(encodedPassword);
        return new AdminUpdatePasswordResponse(
                admin.getName()
        );
    }
}
