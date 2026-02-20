package com.mangementsystem.admin.service;

import com.mangementsystem.admin.dto.AdminLoginRequest;
import com.mangementsystem.admin.dto.AdminSignupRequest;
import com.mangementsystem.admin.dto.AdminSignupResponse;
import com.mangementsystem.admin.dto.SessionAdmin;
import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.admin.entity.AdminStatus;
import com.mangementsystem.admin.repository.AdminRepository;
import com.mangementsystem.config.PasswordEncoder;
import com.mangementsystem.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminSignupResponse save(AdminSignupRequest request){
        if(adminRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException(ErrorCode.DUPLICATE_EMAIL.getMessage());
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
