package com.mangementsystem.admin.service;

import com.mangementsystem.admin.dto.*;
import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.admin.repository.AdminRepository;
import com.mangementsystem.config.PasswordEncoder;
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
                ()-> new IllegalStateException("없는 일정이거나, 유저정보가 없습니다.")
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
            throw new IllegalStateException("없는 게시글 입니다.");
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


}
