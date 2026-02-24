package com.managementSystem.admin.entity;

import com.managementSystem.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(unique = true, nullable = false, length = 20)
    private String phoneNumber;

    //AdminRole role >>  가입시 선택하는 역할
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    //AdminStatus status -> 계정의 현재 상태
    @Enumerated(EnumType.STRING)
    private AdminStatus status;

    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private String rejectionReason;

    private boolean isDeleted = false;

    // 일반 회원가입용 생성자 (회원가입 시 기본 상태 : 승인 대기)
    public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = AdminStatus.PENDING;
    }

    // 슈퍼 관리자 생성/초기화용 (슈퍼관리자는 계정이 이미 만들어져있고, 승인 완료 상태)
    public Admin(String name, String email, String password, String phoneNumber, AdminRole role, AdminStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status; // 외부에서 넘겨준 APPROVED를 받을 수 있음!
    }

    // 승인 대기중 관리자를 승인하여 활성화상태로 만들어주는 메서드
    public void approve() {
        // 만약 해당 관리자가 승인대기중 상태가 아니라면
        if(this.status != AdminStatus.PENDING) {
            throw new IllegalStateException("승인 대기중인 관리자만 승인할 수 있습니다.");
        }
        // 승인 대기중 -> 활성화
        this.status = AdminStatus.ACTIVE;
        this.approvedAt = LocalDateTime.now(); // 승인 일시 업데이트
    }
    // 관리자 거부 메서드, String reason -> 거부 사유
    public void reject(String reason) {
        if (this.status != AdminStatus.PENDING) {
            throw new IllegalStateException("승인 대기 상태인 관리자만 거부할 수 있습니다.");
        }
        // 거부 사유는 필수로 작성, null이면 예외 던지기
        if (reason == null){
            throw new IllegalStateException("거부 사유는 필수 입니다.");
        }
        // 거부 상태로 변경
        this.status = AdminStatus.REJECTED;
        // 거부 사유
        this.rejectionReason = reason;
        this.rejectedAt = LocalDateTime.now(); // 거부 일시 업데이트
    }


    public void AdminUpdate(String name,String email,String phoneNumber ) {
        this.name = name;
        this.email = email;
        this.phoneNumber=phoneNumber;

    }
    public void AdminRoleUpdate(AdminRole role){
        this.role=role;
    }

    public void AdminStatusUpdate(AdminStatus status){
        this.status=status;
    }

    public void AdminUpdateProfile(
            String name, String email, String phoneNumber
    ){
        this.name = name;
        this.email = email;
        this.phoneNumber=phoneNumber;
    }

    public void AdminUpdatePassword(String password){
        this.password = password;
    }

    public void delete(boolean isDeleted) {
        this.isDeleted = true;
    }

}
