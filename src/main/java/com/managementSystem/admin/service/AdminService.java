package com.managementSystem.admin.service;

import com.managementSystem.admin.dto.*;
import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.entity.AdminStatus;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.config.PasswordEncoder;
import com.managementSystem.exception.AdminException;
import com.managementSystem.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public AdminSignupResponse save(AdminSignupRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new AdminException(ErrorCode.ADMIN_DUPLICATE_EMAIL);
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
    public AdminGetResponse getAdmin(Long adminId, SessionAdmin loginAdmin) {
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
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
    public Page<AdminGetResponse> getAllAdmin(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Admin> AdminPage = adminRepository.findByIsDeletedFalseAndNameContainingOrEmailContaining(keyword, keyword, pageable);

        return AdminPage.map(admin -> new AdminGetResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getUpdatedAt()
        ));
    }

    // 괸리자 정보 수정 (슈퍼관리자 권한 확인 로직)
    @Transactional
    public AdminUpdateResponse updateAdmin(Long adminId, AdminUpdateRequest request, SessionAdmin loginAdmin) {
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND));

        admin.AdminUpdate(request.getName(), request.getEmail(), request.getPhoneNumber());
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
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        // Soft Delete
        admin.AdminStatusUpdate(AdminStatus.INACTIVE);

    }


    // 로그인 검증
    @Transactional(readOnly = true)
    public SessionAdmin login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_EMAIL_NOT_FOUND)
        );
        if(!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new AdminException(ErrorCode.ADMIN_MISTAKE_PASSWORD);
        }
        checkAdminStatus(admin);

        return new SessionAdmin(
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );
    }

    private void checkAdminStatus(Admin admin) {
        if (admin.getStatus().equals(AdminStatus.PENDING)) {
            throw new AdminException(ErrorCode.ADMIN_PENDING_ADMIN);
        }
        if (admin.getStatus().equals(AdminStatus.INACTIVE)) {
            throw new AdminException(ErrorCode.ADMIN_INACTIVE_ADMIN);
        }
        if (admin.getStatus().equals(AdminStatus.REJECTED)) {
            throw new AdminException(ErrorCode.ADMIN_REJECTED_ADMIN);
        }
        if (admin.getStatus().equals(AdminStatus.SUSPENDED)) {
            throw new AdminException(ErrorCode.ADMIN_SUSPENDED_ADMIN);
        }
    }

    // 슈퍼관라자가 로그인 승인, 권한 확인
    @Transactional
    public void approveAdmin(Long adminId, SessionAdmin loginAdmin) {
        if ((loginAdmin.getRole() != AdminRole.SUPER_ADMIN)) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        admin.approve();
    }

    // 슈퍼관리자가 승인 대기 상태의 관리자를 활성/거부 상태 변경
    @Transactional
    public void updateAdminStatus(Long adminId, AdminStatusRequest request, SessionAdmin loginAdmin) {
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        if (request.getStatus() == AdminStatus.ACTIVE) {
            admin.approve();
        } else if (request.getStatus() == AdminStatus.REJECTED) {
            admin.reject(request.getRejectReason());
        } else {
            throw new IllegalStateException("잘못된 상태 변경 요청입니다.");
        }
    }

    private SessionAdmin adminLogin(SessionAdmin admin) {
        if (admin == null) {
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
    public AdminRoleUpdateResponse updateRole(Long adminId, AdminRoleUpdateRequest request, SessionAdmin loginAdmin) {
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
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
    public AdminStatusUpdateResponse updateStatus(Long adminId, AdminStatusUpdateRequest request, SessionAdmin loginAdmin) {
        if (loginAdmin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        admin.AdminStatusUpdate(request.getStatus());
        return new AdminStatusUpdateResponse(
                admin.getStatus()
        );
    }

    // 내 프로필 조회 (단건 조회, 본인이 맞는지 확인)
    @Transactional(readOnly = true)
    public AdminGetProfileResponse getAdminProfile(Long adminId, SessionAdmin loginAdmin) {
        if (!loginAdmin.getId().equals(adminId)) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );

        return new AdminGetProfileResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 내 프로필 수정 (본인이 맞는지 확인)
    @Transactional
    public AdminUpdateProfileResponse updateAdminProfile(
            Long adminId, AdminUpdateProfileRequest request, SessionAdmin loginAdmin) {
        if (!loginAdmin.getId().equals(adminId)) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
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
    public AdminUpdatePasswordResponse updateAdminPassword(Long adminId, AdminUpdatePasswordRequest
            request, SessionAdmin loginAdmin) {
        if (!loginAdmin.getId().equals(adminId)) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        admin.AdminUpdatePassword(encodedPassword);
        return new AdminUpdatePasswordResponse(
                admin.getName()
        );
    }
}
