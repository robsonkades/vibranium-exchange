package com.robsonkades.exchange.core.port;

import com.robsonkades.exchange.core.domain.Order;

import java.util.Map;

public interface ListOrdersPortService {

    Map<String, Order> execute();
}
