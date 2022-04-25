package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.port.CreateOrderPortService;
import com.robsonkades.broker.core.port.ProcessOrderPromisePortService;

public class CreateOrderPortServiceImpl implements CreateOrderPortService {

    private ProcessOrderPromisePortService processOrderPromisePortService;

    public CreateOrderPortServiceImpl(final ProcessOrderPromisePortService processOrderPromisePortService) {
        this.processOrderPromisePortService = processOrderPromisePortService;
    }

    @Override
    public String execute(final OrderCommand order) {
        processOrderPromisePortService.execute(order);
        return order.getId();
    }
}
