package com.managementSystem.customer.dto;

import com.managementSystem.customer.entity.CustomerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateStatusRequest {
    private CustomerStatus status;
}
