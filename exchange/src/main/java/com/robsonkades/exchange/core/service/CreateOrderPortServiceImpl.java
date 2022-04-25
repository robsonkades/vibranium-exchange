package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.adapters.dto.CreateOrderInputDto;
import com.robsonkades.exchange.core.domain.OrderBook;
import com.robsonkades.exchange.core.port.CreateOrderPortService;

import java.util.UUID;

public class CreateOrderPortServiceImpl implements CreateOrderPortService {

    private OrderBook order;

    public CreateOrderPortServiceImpl(final OrderBook order) {
        this.order = order;
    }

    @Override
    public String execute(CreateOrderInputDto inputDto) {
        String orderId = UUID.randomUUID().toString();
        this.order.createOrder(inputDto.getWallet(), orderId, inputDto.getType(), inputDto.getPrice(), inputDto.getAmount());
        return orderId;
    }
}
