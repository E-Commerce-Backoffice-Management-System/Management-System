package com.managementSystem.customer.controller;

import com.managementSystem.customer.dto.*;
import com.managementSystem.customer.service.CustomerService;
import com.managementSystem.global.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
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

    // customer 회원 가입
    @PostMapping("/customerSignup")
    public ResponseEntity<ApiResponse<CustomerSignupResponse>> customerSignup(
            @RequestBody CustomerSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(customerService.customerSignup(request)));
    }

    // customer 로그인
    @PostMapping("/customerLogin")
    public ResponseEntity<Void> customerLogin(
            @Valid @RequestBody CustomerLoginRequest request, HttpSession session) {
        SessionCustomer sessionCustomer = customerService.customerLogin(request);
        session.setAttribute("sessionCustomer", sessionCustomer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 고객 로그아웃
    @PostMapping("/customer/logout")
    public ResponseEntity<Void> customerLogout(
            @SessionAttribute(name = "sessionCustomer", required = false) SessionCustomer sessionCustomer, HttpSession session) {
        if (sessionCustomer == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    // 고객 집계 리스트 조회 (도전)
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<Page<GetCustomerListResponse>>> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(customerService.findAll(keyword, page, size, sortBy, direction)));
    }
    //고객 상세 조회
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<GetCustomerResponse>> getOne(@PathVariable Long customerId){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.findOne(customerId)));
    }
    //고객 정보 수정
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<UpdateCustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(customerService.update(customerId, request)));
    }
    // 고객 상태 변경
    @PatchMapping("/customers/{customerId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long customerId,
            @RequestBody UpdateStatusRequest request
            ){
        customerService.updateStatus(customerId, request.getStatus());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //  고객 탈퇴
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
