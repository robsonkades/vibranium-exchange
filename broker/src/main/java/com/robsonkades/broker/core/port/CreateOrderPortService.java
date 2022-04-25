package com.robsonkades.broker.core.port;

import com.robsonkades.broker.core.domain.OrderCommand;

public interface CreateOrderPortService {

    String execute(OrderCommand order);
}
