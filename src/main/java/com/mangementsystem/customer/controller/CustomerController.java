package com.mangementsystem.customer.controller;

import com.mangementsystem.customer.dto.*;
import com.mangementsystem.customer.entity.CustomerStatus;
import com.mangementsystem.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/admins/customers")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(request));
    }
    @GetMapping("/admins/customers")
    public ResponseEntity<Page<GetCustomerResponse>> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
            ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(keyword, page, size, sortBy, direction));
    }
    @GetMapping("/admins/customers/{customerId}")
    public ResponseEntity<GetCustomerResponse> getOne(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }
    @PatchMapping("/admins/customers/{customerId}")
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(customerId, request));
    }
    @PatchMapping("/admins/customers/{customerId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long customerId,
            @RequestBody UpdateStatusRequest request
            ){
        customerService.updateStatus(customerId, request.getStatus());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/admins/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
