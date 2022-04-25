package com.robsonkades.broker.adapters.inbound.service;

import com.robsonkades.broker.adapters.outbound.producer.BrokerCommandPublisher;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.port.ProcessOrderPromisePortService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessOrderPromiseService implements ProcessOrderPromisePortService {

    private BrokerCommandPublisher brokerCommandPublisher;

    public ProcessOrderPromiseService(BrokerCommandPublisher brokerCommandPublisher) {
        this.brokerCommandPublisher = brokerCommandPublisher;
    }

    @Override
    @Async
    public void execute(OrderCommand order) {
        brokerCommandPublisher.publishNotificationCommand(order);
    }
}
