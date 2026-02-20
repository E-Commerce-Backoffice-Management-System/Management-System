package com.mangementsystem.customer.controller;

import com.mangementsystem.customer.dto.CreateCustomerRequest;
import com.mangementsystem.customer.dto.CreateCustomerResponse;
import com.mangementsystem.customer.dto.GetCustomerResponse;
import com.mangementsystem.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/admins/customers")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(request));
    }
    @GetMapping("/admins/customers/customerId")
    public ResponseEntity<Page<GetCustomerResponse>> getCustomers(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(page, size));
    }

}
