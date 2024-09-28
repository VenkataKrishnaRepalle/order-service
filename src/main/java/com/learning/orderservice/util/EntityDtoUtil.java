package com.learning.orderservice.util;

import com.learning.orderservice.dto.*;
import com.learning.orderservice.entity.Order;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static OrderResponseDto orderToOrderResponseDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        BeanUtils.copyProperties(order, responseDto);
        return responseDto;
    }
    public static void setTransactionRequestDto(RequestContext requestContext) {
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setUserId(requestContext.getTransactionRequestDto().getUserId());
        requestDto.setAmount(requestContext.getProductDto().getPrice());
        requestContext.setTransactionRequestDto(requestDto);
    }

    public static Order getPurchaseOrder(RequestContext requestContext) {
        Order order = new Order();
        order.setUserId(requestContext.getOrderRequestDto().getUserId());
        order.setProductId(requestContext.getProductDto().getId());
        order.setAmount(requestContext.getProductDto().getPrice());

        TransactionStatus status = requestContext.getTransactionResponseDto().getStatus();
        order.setStatus(TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED);
        return order;
    }
}
