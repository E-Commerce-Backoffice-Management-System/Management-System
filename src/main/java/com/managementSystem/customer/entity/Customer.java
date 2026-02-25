package com.managementSystem.customer.entity;

import com.managementSystem.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    // 회원가입을 위한 비밀번호 필드 추가
    private String password;

    // 회원가입용 생성자
    public Customer(String name, String email, String phoneNumber, String password, CustomerStatus status) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = CustomerStatus.ACTIVE;
    }

//    public Customer(String name, String email, String phoneNumber){
//        this.name = name;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }

    public void updateInfo(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void  updateStatus(CustomerStatus status){
        this.status = status;
    }

    public void delete(){
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.status = CustomerStatus.INACTIVE;
    }
}
