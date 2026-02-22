package com.mangementsystem.customer.entity;

import com.mangementsystem.global.BaseEntity;
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
    private CustomerStatus status;

    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    public Customer(String name, String email, String phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = CustomerStatus.ACTIVE;
    }

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
