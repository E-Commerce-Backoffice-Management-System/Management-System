package com.mangementsystem.admin.entity;

import com.mangementsystem.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false)
    private AdminRole role;

    //AdminStatus status -> 계정의 현재 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;

    public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = AdminStatus.PENDING;
    }

    public void approve() {
        if(this.status != AdminStatus.PENDING) {
            throw new IllegalStateException("승인 대기중입니다.");
        }
        this.status = AdminStatus.ACTIVE;
    }


}
