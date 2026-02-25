package com.managementSystem.customer.controller;

import com.managementSystem.customer.dto.*;
import com.managementSystem.customer.service.CustomerService;
import com.managementSystem.global.dto.ApiResponse;
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

    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<CreateCustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(customerService.save(request)));
    }
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<Page<GetCustomerResponse>>> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
            ){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.findAll(keyword, page, size, sortBy, direction)));
    }
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<GetCustomerResponse>> getOne(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.findOne(customerId)));
    }
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<UpdateCustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.update(customerId, request)));
    }
    @PatchMapping("/customers/{customerId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long customerId,
            @RequestBody UpdateStatusRequest request
            ){
        customerService.updateStatus(customerId, request.getStatus());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
