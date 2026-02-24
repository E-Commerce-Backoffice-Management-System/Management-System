package com.managementSystem.product.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ON_SALE("판매중"),
    SOLD_OUT("품절"),
    DISCONTINUED("단종");

    private final String statusValue;
    Status(String statusValue) {
        this.statusValue = statusValue;
    }

    @JsonValue //객체 -> JSON시 "판매중"으로 출력되게 함
    public String getStatusValue() {
        return statusValue;
    }

    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.statusValue.equals(value)) {
                return status;
            }
        }
        return null;
    }
}