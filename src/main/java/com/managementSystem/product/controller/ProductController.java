package com.managementSystem.product.controller;

import com.managementSystem.product.dto.*;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 생성
    @PostMapping("/products")
    public ResponseEntity<CreateProductResponse> saveProduct(
            //@RequestAttribute Long adminId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(1L, request));
    }

    //상품 리스트 조회
    @GetMapping("/products")
    public ResponseEntity<GetProductPageResponse> getAllProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        GetProductPageResponse response = productService.getProduct(productName, category, status, page, size, sortBy, sort);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //상품 상세 조회
    @GetMapping("/products/{productId}")
    public ResponseEntity<GetOneProductResponse> getDetailProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(productId));
    }

    //상품 업데이트
    @PatchMapping("/products/{productId}")
    public ResponseEntity<UpdateProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, request));
    }

    //상품 삭제
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
