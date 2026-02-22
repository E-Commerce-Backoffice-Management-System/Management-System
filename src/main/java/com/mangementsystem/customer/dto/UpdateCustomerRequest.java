package com.mangementsystem.customer.dto;

import lombok.Getter;

@Getter
public class UpdateCustomerRequest {

    private String name;
    private String email;
    private String phoneNumber;
}
