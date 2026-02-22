package com.mangementsystem.customer.dto;

import com.mangementsystem.customer.entity.CustomerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateStatusRequest {
    private CustomerStatus status;
}
