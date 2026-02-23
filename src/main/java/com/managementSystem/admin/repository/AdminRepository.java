package com.managementSystem.admin.repository;

import com.managementSystem.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);
    Optional<Admin> findByEmail(String email);
    Page<Admin> findByIsDeletedFalseAndNameContainingOrEmailContaining(
            String nameKeyword,
            String emailKeyword,
            Pageable pageable
    );
}
