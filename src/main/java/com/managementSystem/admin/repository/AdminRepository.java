package com.managementSystem.admin.repository;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.entity.AdminStatus;
import com.managementSystem.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query(value = "SELECT a FROM Admin a " +
            "WHERE (:keyword IS NULL OR a.name LIKE %:keyword% OR a.email LIKE %:keyword%) " +
            "AND (:role IS NULL OR a.role = :role) " +
            "AND (:status IS NULL OR a.status = :status)",
            countQuery = "SELECT count(a) FROM Admin a " +
                    "WHERE (:keyword IS NULL OR a.name LIKE %:keyword% OR a.email LIKE %:keyword%) " +
                    "AND (:role IS NULL OR a.role = :role) " +
                    "AND (:status IS NULL OR a.status = :status)")
    Page<Admin> searchAdmins(@Param("keyword") String keyword,
                             @Param("role") AdminRole role,
                             @Param("status") AdminStatus status,
                             Pageable pageable);
}
