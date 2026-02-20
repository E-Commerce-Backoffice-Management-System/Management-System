package com.mangementsystem.admin.repository;

import com.mangementsystem.admin.entity.Admin;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(@Email String email);
    Optional<Admin> findByEmail(@Email String email);
}
