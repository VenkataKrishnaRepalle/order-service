package com.learning.orderservice.service;

import com.learning.orderservice.client.ProductClient;
import com.learning.orderservice.client.UserClient;
import com.learning.orderservice.dto.OrderRequestDto;
import com.learning.orderservice.dto.OrderResponseDto;
import com.learning.orderservice.dto.RequestContext;
import com.learning.orderservice.repository.OrderRepository;
import com.learning.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final ProductClient productClient;

    private final UserClient userClient;

    private final OrderRepository orderRepository;

    public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> orderRequestDto) {
        return orderRequestDto
                .map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(orderRepository::save)
                .map(EntityDtoUtil::orderToOrderResponseDto);
    }

    private Mono<RequestContext> productRequestResponse(RequestContext requestContext) {
        return productClient.getProductById(requestContext.getOrderRequestDto().getProductId())
                .doOnNext(requestContext::setProductDto)
                .thenReturn(requestContext);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext requestContext) {
        return userClient.authorizeTransaction(requestContext.getTransactionRequestDto())
                .doOnNext(requestContext::setTransactionResponseDto)
                .thenReturn(requestContext);
    }
}