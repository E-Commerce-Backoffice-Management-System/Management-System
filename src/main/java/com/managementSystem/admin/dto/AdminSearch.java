package com.managementSystem.admin.dto;

import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.entity.AdminStatus;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AdminSearch {
    // 관리자 리스트 조회 전용 DTO (쿼리 파라미터를 담음), 기본값 설정

    // 검색 키워 : 이름, 이메일
    private String keyword;

    // 페이지 번호 기본 값 : 1
    private int page = 1;

    // 페이지 개수 기본 값 : 10
    private int size = 10;

    // 정렬 기준: 생성일
    private String sortBy = "createdAt";

    // 정렬 순서 : 내림 차순
    private String sortOrder = "desc";

    // 역할 필터
    private AdminRole role;

    // 상태 필터
    private AdminStatus status;
}
