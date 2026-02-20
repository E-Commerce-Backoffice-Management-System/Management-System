package com.mangementsystem.customer.service;

import com.mangementsystem.customer.dto.CreateCustomerRequest;
import com.mangementsystem.customer.dto.CreateCustomerResponse;
import com.mangementsystem.customer.dto.GetCustomerResponse;
import com.mangementsystem.customer.entity.Customer;
import com.mangementsystem.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                savedCustomer.getCreatedAt()
        );
    }
    @Transactional(readOnly = true)
    public Page<GetCustomerResponse> findAll(int page, int size) {
        Pageable pageable= PageRequest.of(page-1, size);
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers
                .map(customer -> new GetCustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getPhoneNumber(),
                        customer.getCreatedAt()
                ));
    }
}
