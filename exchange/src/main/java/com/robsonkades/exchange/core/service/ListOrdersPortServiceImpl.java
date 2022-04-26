package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.core.domain.Order;
import com.robsonkades.exchange.core.domain.OrderBook;
import com.robsonkades.exchange.core.port.ListOrdersPortService;

import java.util.Map;

public class ListOrdersPortServiceImpl implements ListOrdersPortService {

    private OrderBook order;

    public ListOrdersPortServiceImpl(final OrderBook order) {
        this.order = order;
    }

    @Override
    public Map<String, Order> execute() {
        return this.order.getOrders();
    }
}
