package com.mangementsystem.admin.repository;

import com.mangementsystem.admin.entity.Admin;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(@Email String email);
}
