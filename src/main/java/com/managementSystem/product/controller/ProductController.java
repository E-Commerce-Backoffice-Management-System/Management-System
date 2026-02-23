package com.managementSystem.product.controller;

import com.managementSystem.admin.dto.SessionAdmin;
import com.managementSystem.global.ApiResponse;
import com.managementSystem.product.dto.*;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @PostMapping("/admin/{adminId}/products")
//    public ResponseEntity<ProductResponse> createProduct(
//            @PathVariable Long adminId, @Valid @RequestBody ProductRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(new SessionAdmin();
//    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Status status,

            @PageableDefault(
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(productName, category, status, pageable));
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<GetOneProductResponse> getOneProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getOneProduct(productId));
    }

    @PatchMapping ("/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, request));
    }

    @PatchMapping("/products/{productId}/stock")
    public ResponseEntity<ProductResponse> updateProductStock(@PathVariable Long productId, @RequestBody UpdateProductStockRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductStock(productId, request));
    }

    @PatchMapping("/products/{productId}/status")
    public ResponseEntity<ProductResponse> updateProductStatus(@PathVariable Long productId, @RequestBody UpdateProductStatusRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductStatus(productId, request));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.successWithNoContent());
    }
}
