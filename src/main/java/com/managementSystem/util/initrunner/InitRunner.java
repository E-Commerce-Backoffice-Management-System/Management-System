//package com.managementSystem.util.initrunner;
//
//import com.managementSystem.admin.entity.Admin;
//import com.managementSystem.admin.repository.AdminRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class InitRunner implements ApplicationRunner {
//
//    private final AdminRepository adminRepository;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        Admin admin = new Admin("관리자", "이메일", "비번", "폰번", "슈퍼 관리자", "Active" );
//        adminRepository. save(admin);
//
//    }
//
//}
