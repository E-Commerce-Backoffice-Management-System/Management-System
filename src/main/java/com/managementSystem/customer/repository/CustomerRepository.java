package com.managementSystem.customer.repository;

import com.managementSystem.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByIsDeletedFalseAndNameContainingOrEmailContaining(
            String nameKeyword,
            String emailKeyword,
            Pageable pageable
    );

    boolean existsByEmail(String email);
    // 서비스 코드 로그인 로직에서 사용하는 email 찾기
    Optional<Customer> findByEmail(String email);
}
