package com.managementSystem.customer.repository;

import com.managementSystem.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 삭제되지 않은 고객 중 이름 또는 이메일에 키워드가 포함된 목록 조회
    Page<Customer> findByIsDeletedFalseAndNameContainingOrEmailContaining(
            String nameKeyword,
            String emailKeyword,
            Pageable pageable
    );

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}