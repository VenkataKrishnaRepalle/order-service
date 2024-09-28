package com.learning.orderservice.dto;

import lombok.Data;

@Data
public class RequestContext {
    private OrderRequestDto orderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(OrderRequestDto orderRequestDto, ProductDto productDto, TransactionRequestDto transactionRequestDto, TransactionResponseDto transactionResponseDto) {
        this.orderRequestDto = orderRequestDto;
    }

    public RequestContext(OrderRequestDto orderRequest) {
    }
}
