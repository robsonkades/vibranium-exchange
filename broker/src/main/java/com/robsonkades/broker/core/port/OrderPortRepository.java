package com.robsonkades.broker.core.port;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Order;
import com.robsonkades.broker.core.domain.OrderCommand;

public interface OrderPortRepository {
    Order create(String orderExchage, final OrderCommand order);
}
