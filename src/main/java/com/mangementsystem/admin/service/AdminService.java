package com.mangementsystem.admin.service;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.admin.entity.AdminStatus;
import com.mangementsystem.admin.repository.AdminRepository;
import com.mangementsystem.config.PasswordEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

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
    public List<AdminGetOneResponse> getAllAdmin() {
        List<Admin> admins=adminRepository.findAll();


        List<AdminGetOneResponse> dtos=new ArrayList<>();
        for(Admin admin: admins){
            AdminGetOneResponse dto= new AdminGetOneResponse(
                    admin.getId(),
                    admin.getEmail(),
                    admin.getPhoneNumber(),
                    admin.getRole(),
                    admin.getStatus(),
                    admin.getCreatedAt(),
                    admin.getUpdatedAt()
            );
                    dtos.add(dto);
        }
        return dtos;
    }


    // 관리자 정보 수정
    @Transactional
    public AdminUpdateResponse updateAdmin(Long adminId, AdminUpdateRequest request) {

        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException(" 정보가 없습니다.")
        );
        admin.Adminupdate(request.getName(),request.getEmail(),request.getPhoneNumber());
        return new AdminUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }


    // 삭제
    @Transactional
    public void Admindelete(Long adminId) {
        boolean existence = adminRepository.existsById(adminId);
        if (!existence) {
            throw new IllegalStateException("없습니다.");
        }

        adminRepository.deleteById(adminId);
    }

    @Transactional
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


    // 관리자 역할 변경
    @Transactional
    public AdminRoleUpdateResponse updateRole(Long adminId, AdminRoleUpdateRequest request) {
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
    public AdminStatusUpdateResponse updateStatus(Long adminId, AdminStatusUpdateRequest request) {

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
            Long adminId, @Valid AdminUpdateProfileRequest request
    ) {
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException(" 정보가 없습니다.")
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
    public AdminUpdatePasswordResponse updateAdminPassword(Long adminId, @Valid AdminUpdatePasswordRequest request) {
        Admin admin=adminRepository.findById(adminId).orElseThrow(
                ()-> new IllegalStateException(" 정보가 없습니다.")
        );
        admin.AdminUpdatePassword(request.getPassword());

        return new AdminUpdatePasswordResponse(
                admin.getName()
        );
    }
}
