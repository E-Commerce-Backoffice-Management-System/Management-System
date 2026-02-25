package com.managementSystem.product.service;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.exception.AdminException;
import com.managementSystem.exception.ErrorCode;
import com.managementSystem.exception.ProductException;
import com.managementSystem.product.dto.*;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // 상품 생성
    @Transactional
    public CreateProductResponse save(Long adminId, CreateProductRequest request) {
        //현재 상품을 등록하려는 관리자 조회 Long adminId
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_NOT_FOUND)
        );

        Product savedProduct = productRepository.save(
                Product.builder()
                        .name(request.name())
                        .category(request.category())
                        .price(request.price())
                        .stock(request.stock())
                        .status(Status.ON_SALE)
                        .createdBy(admin)
                        .build()
        );
        return CreateProductResponse.from(savedProduct);
    }

    //상품 리스트 조회
    @Transactional(readOnly = true)
    public GetProductPageResponse getProduct(
            String productName, Category category, Status status,
            int page, int size, String sortBy, String sort
    ){
        //정렬
        Sort.Direction direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortObj = Sort.by(direction, sortBy);

        //Pageable 생성
        Pageable pageable = PageRequest.of(page-1, size, sortObj);

        Page<GetAllProductResponse> productPage = (Page<GetAllProductResponse>) productRepository.findAllProducts(productName, category, status, pageable);

        return new GetProductPageResponse(
                productPage.getContent(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.getNumber()+1,
                productPage.getSize()
        );
    }

    //상품 상세 조회
    @Transactional
    public GetOneProductResponse getProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        return new GetOneProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt()
        );
    }

    //상품 수정
    @Transactional
    public UpdateProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        product.updateProduct(
                request.name(),
                request.category(),
                request.price()
        );

        return UpdateProductResponse.from(product);
    }

    //상품 삭제
    @Transactional
    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        //삭제할때 댓글도 같이 삭제하게 만들기

        productRepository.deleteById(productId);
    }


}
