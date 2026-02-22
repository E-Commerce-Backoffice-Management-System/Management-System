package com.mangementsystem.customer.service;

import com.mangementsystem.customer.dto.*;
import com.mangementsystem.customer.entity.Customer;
import com.mangementsystem.customer.entity.CustomerStatus;
import com.mangementsystem.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public CreateCustomerResponse save(CreateCustomerRequest request) {
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
                () -> new IllegalStateException("없는 고객입니다.")
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
                () -> new IllegalStateException("없는 고객입니다.")
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
                .orElseThrow(() -> new IllegalStateException("없는 고객입니다."));

        customer.updateStatus(status);
    }

    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("없는 고객입니다."));

        customer.delete();
    }
}
