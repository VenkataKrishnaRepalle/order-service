package com.learning.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private Integer userId;

    private String productId;
}
