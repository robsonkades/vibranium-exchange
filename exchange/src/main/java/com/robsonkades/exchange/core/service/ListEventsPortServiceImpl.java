package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.ListEventsPortService;
import com.robsonkades.exchange.core.port.OrderBookPortListener;

import java.util.List;

public class ListEventsPortServiceImpl implements ListEventsPortService {

    private OrderBookPortListener orderBookPortListener;

    public ListEventsPortServiceImpl(final OrderBookPortListener orderBookPortListener) {
        this.orderBookPortListener = orderBookPortListener;
    }

    @Override
    public List<OrderEvent.Event> execute() {
        return this.orderBookPortListener.events();
    }
}
