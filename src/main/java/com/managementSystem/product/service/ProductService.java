package com.managementSystem.product.service;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.product.dto.*;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

//    @Transactional
//    public ProductResponse createProduct(ProductRequest request) {
//       Admin admin = adminRepository.findById(adminId).orElseThrow(
//                () -> new IllegalStateException("권한이 없는 관리자 입니다.")
//        );
//        Product product = new Product(
//                request.getProductName(), request.getCategory(), request.getPrice(), request.getStock(), request.getStatus());
//        Product savedProduct = productRepository.save(product);
//        return new ProductResponse(
//                savedProduct.getId(),
//                savedProduct.getProductName(),
//                savedProduct.getCategory(),
//                savedProduct.getPrice(),
//        );
//    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(String productName, Category category, Status status, Pageable pageable){

        if (productName != null && category == null && status == null) {
            // 검색 키워드 상품명 조회
            return productRepository.findByProductNameContaining(productName, pageable)
                    .map(ProductResponse::new);
        } else if (productName == null && category != null && status == null) {
            // 카테고리 필터
            return productRepository.findByCategory(category, pageable)
                    .map(ProductResponse::new);
        } else if (productName == null && category == null && status != null) {
            // 상품상태 필터
            return productRepository.findByStatus(status, pageable)
                    .map(ProductResponse::new);
        } else if (productName != null && category != null && status == null) {
            // 검색 키워드 상품명 조회와 카테고리 필터
            return productRepository.findByProductNameAndCategory(productName, category, pageable)
                    .map(ProductResponse::new);
        } else if (productName != null && category == null && status != null) {
            // 검색 키워드 상품명 조회와 상품상태 필터
            return productRepository.findByProductNameAndStatus(productName, status, pageable)
                    .map(ProductResponse::new);
        } else if (productName == null && category != null && status != null) {
            // 카테고리 필터와 상품상태 필터
            return productRepository.findByCategoryAndStatus(category, status, pageable)
                    .map(ProductResponse::new);
        } else if (productName != null && category != null && status != null) {
            // 검색 키워드 상품명 조회와 카테고리 필터와 상품상태 필터
            return productRepository.findByProductNameAndCategoryAndStatus(productName, category, status, pageable)
                    .map(ProductResponse::new);
        }
        // 전체 조회
        return productRepository.findAll(pageable)
                .map(ProductResponse::new);
    }

    public GetOneProductResponse getOneProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("없는 상품 입니다.")
        );

        return new GetOneProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("없는 상품 입니다.")
        );

        product.updateProduct(request.getProductName(), request.getCategory(), request.getPrice());

        return new ProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProductStock(Long productId, UpdateProductStockRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("없는 상품 입니다.")
        );

        if (product.getStatus() == Status.DISCONTINUED) {
            product.updateProductStock(request.getStock());
        } else {
            product.updateProductStockAndStatus(request.getStock());
        }

        return new ProductResponse(product);
    }

    @Transactional
    public Product checkProductStock(Long productId, int quantity){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("없는 상품 입니다.")
        );

        if (product.getStatus() == Status.DISCONTINUED){
            throw new IllegalStateException("단종된 상품입니다.");
        }
        if (product.getStatus() == Status.SOLDOUT){
            throw new IllegalStateException("품절된 상품입니다.");
        }
        if (product.getStock() < quantity){
            throw new IllegalStateException("재고가 부족합니다.");
        }

        return product;
    }

    @Transactional
    public ProductResponse updateProductStatus(Long productId, UpdateProductStatusRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("없는 상품 입니다.")
        );

        product.updateProductStatus(request.getStatus());

        return new ProductResponse(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 상품 입니다.")
        );
    }
}
