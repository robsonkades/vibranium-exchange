package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Order;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.port.OrderPortRepository;

import java.time.LocalDateTime;

public class OrderPortRepositoryImpl implements OrderPortRepository {

    private OrderJpaRepository repository;

    public OrderPortRepositoryImpl(final OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(final String orderExchange, final OrderCommand orderCommand) {
        Order order = new Order();
        order.setId(order.getId());
        order.setOrderExchange(orderExchange);
        order.setCreationDate(LocalDateTime.now());
        order.setSymbol(orderCommand.getSymbol());
        order.setAmount(orderCommand.getAmount());
        order.setType(orderCommand.getType());
        order.setPrice(orderCommand.getPrice());
        order.setWallet(orderCommand.getWallet());
        return repository.saveAndFlush(order);
    }
}
