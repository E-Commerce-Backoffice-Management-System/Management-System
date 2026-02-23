package com.managementSystem.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    ONSALE("판매중"),
    SOLDOUT("품절"),
    DISCONTINUED("단종");

    private final String status;
}
