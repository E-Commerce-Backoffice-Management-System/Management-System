package com.mangementsystem.customer.repository;

import com.mangementsystem.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAll(Long customerId, Pageable pageable);
    Page<Customer> findAllByCustomerId(Long customerId, Pageable pageable);
}
