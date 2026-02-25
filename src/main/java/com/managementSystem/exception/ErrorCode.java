package com.managementSystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {

    // Admin 에러 처리
    ADMIN_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST,"A001" ,"중복된 이메일입니다."),
    ADMIN_MISTAKE_PASSWORD(HttpStatus.UNAUTHORIZED,"A002" ,"비밀번호가 일치하지 않습니다."),
    ADMIN_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"A003","가입되지 않은 이메일 입니다.."),
    ADMIN_NO_AUTHORITY(HttpStatus.FORBIDDEN,"A004" ,"해당 작업에 대한 권한이 없습니다."),
    ADMIN_USER_NOT_FOUND(HttpStatus.NOT_FOUND,"A005" ,"해당 유저를 찾지 못했습니다."),
    ADMIN_PENDING_ADMIN(HttpStatus.FORBIDDEN,"A006","관리자 승인 대기중입니다."),
    ADMIN_INACTIVE_ADMIN(HttpStatus.FORBIDDEN,"A007" ,"비활성화 상태의 계정입니다."),
    ADMIN_REJECTED_ADMIN(HttpStatus.FORBIDDEN,"A008" ,"관리자에게 거부된 계정입니다."),
    ADMIN_SUSPENDED_ADMIN(HttpStatus.FORBIDDEN, "A009","정지된 계정입니다."),
    ADMIN_ALREADY_LOGIN(HttpStatus.BAD_REQUEST, "A010", "이미 로그인된 계정입니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "A011", "해당 관리자를 찾을 수 없습니다."),
    ADMIN_INVALID_STATUS_CHANGE(HttpStatus.BAD_REQUEST, "A012", "잘못된 상태 변경 요청입니다."),
    ADMIN_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "A013", "로그인이 필요합니다."),



    // Customer 에러 처리
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "해당 고객을 찾을 수 없습니다."),
    CUSTOMER_DUPLICATE(HttpStatus.BAD_REQUEST,"C002" ,"이미 존재하는 고객입니다."),

    // Product 에러 처리
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "해당 상품을 찾을 수 없습니다."),
    PRODUCT_DISCONTINUED(HttpStatus.BAD_REQUEST, "P002", "단종된 상품입니다."),
    PRODUCT_SOLD_OUT(HttpStatus.BAD_REQUEST, "P003", "품절된 상품입니다."),
    PRODUCT_INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "P004", "재고가 부족합니다."),
    PRODUCT_INVALID_STOCK_QUANTITY(HttpStatus.BAD_REQUEST, "P005", "잘못된 재고 입력입니다."),


    // Order 에처 처리
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "OR001", "해당 주문을 찾을 수 없습니다."),
    ORDER_INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "OR002", "주문 수량은 1 이상이어야 합니다."),
    ORDER_NOT_PREPARING(HttpStatus.BAD_REQUEST, "OR003", "준비중 상태에서만 취소 가능합니다."),
    ORDER_NO_AUTHORITY(HttpStatus.FORBIDDEN, "OR004", "주문에 대한 권한이 없습니다."),

    // Review 에러 처리
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "해당 리뷰를 찾을 수 없습니다."),
    ALREADY_DELETED(HttpStatus.BAD_REQUEST, "R002", "이미 삭제된 리뷰 입니다."),


    // 공통 에러 (G로 시작)
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
