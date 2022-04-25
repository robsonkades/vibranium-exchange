package com.robsonkades.exchange.core.port;

import com.robsonkades.exchange.core.event.OrderEvent;

import java.util.List;

public interface ListEventsPortService {

    List<OrderEvent.Event> execute();
}
