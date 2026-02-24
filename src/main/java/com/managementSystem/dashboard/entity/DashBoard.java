//package com.managementSystem.dashboard.entity;
//
//
//import com.managementSystem.admin.entity.Admin;
//import com.managementSystem.customer.entity.Customer;
//import com.managementSystem.global.BaseEntity;
//import com.managementSystem.product.entity.Product;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity
//@Table(name = "dashboards")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class DashBoard extends BaseEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY,optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;
//
//    @ManyToOne(fetch = FetchType.LAZY,optional = false)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.LAZY,optional = false)
//    @JoinColumn(name = "admin_id", nullable = false)
//    private Admin admin;
//
////    @ManyToOne(fetch = FetchType.LAZY,optional = false)
////    @JoinColumn(name = "review_id", nullable = false)
////    private  Review review;
//
//}
