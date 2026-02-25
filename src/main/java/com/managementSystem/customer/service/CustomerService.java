package com.managementSystem.customer.service;

import com.managementSystem.config.PasswordEncoder;
import com.managementSystem.customer.dto.*;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.entity.CustomerStatus;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.exception.CustomerException;
import com.managementSystem.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.Error;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public final CustomerRepository customerRepository;
    public final PasswordEncoder passwordEncoder;

    // Customer 회원가입
    @Transactional
    public CustomerSignupResponse customerSignup(CustomerSignupRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new CustomerException(ErrorCode.DUPLICATE_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Customer customer = new Customer(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getPhoneNumber()
        );
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerSignupResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail()
        );
    }

    // Customer Login
    @Transactional(readOnly = true)
    public SessionCustomer customerLogin(CustomerLoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomerException(ErrorCode.EMAIL_NOT_FOUND)
        );
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new CustomerException(ErrorCode.MISTAKE_PASSWORD);
        }
        return new SessionCustomer(
                customer.getId(),
                customer.getEmail()
        );
    }

    @Transactional
    public CreateCustomerResponse save(CreateCustomerRequest request) {
        // 중복 체크
        if (customerRepository.existsByEmail(request.getEmail())){
            throw new CustomerException(ErrorCode.CUSTOMER_DUPLICATE);
        }
        Customer customer = new Customer(request.getName(), request.getEmail(), request.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(customer);
        return new CreateCustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhoneNumber(),
                savedCustomer.getStatus(),
                savedCustomer.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<GetCustomerResponse> findAll(String keyword, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Customer> customersPage = customerRepository.findByIsDeletedFalseAndNameContainingOrEmailContaining(keyword, keyword, pageable);
        return customersPage.map(customer -> new GetCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getCreatedAt()
        ));
    }

    @Transactional(readOnly = true)
    public GetCustomerResponse findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
        return new GetCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }
    @Transactional
    public UpdateCustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
        customer.updateInfo(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber()
                );
        return new UpdateCustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
    @Transactional
    public void updateStatus(Long customerId, CustomerStatus status) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerException(ErrorCode.CUSTOMER_NOT_FOUND)
                );

        customer.updateStatus(status);
    }

    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new CustomerException(ErrorCode.CUSTOMER_NOT_FOUND)
                );

        customer.delete();
    }
}
