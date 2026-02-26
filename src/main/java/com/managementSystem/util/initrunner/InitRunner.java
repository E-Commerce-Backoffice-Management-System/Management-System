package com.managementSystem.util.initrunner;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.entity.AdminStatus;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitRunner implements ApplicationRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.findByEmail("superSparta@system.com").isEmpty()) {

            Admin admin = new Admin(
                    "갓갓갓",
                    "superSparta@system.com",
                    passwordEncoder.encode("12345678"),
                    "010-1234-5678",
                    AdminRole.SUPER_ADMIN,
                    AdminStatus.APPROVED
                    );
            adminRepository.save(admin);
        }
    }
}
