package com.robsonkades.broker.adapters.inbound.service;

import com.robsonkades.broker.adapters.outbound.producer.BrokerCommandPublisher;
import com.robsonkades.broker.adapters.outbound.producer.BrokerCommandPublisherTest;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.port.ProcessOrderPromisePortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ProcessOrderPromiseServiceTest {

    @Mock
    private BrokerCommandPublisher brokerCommandPublisher;
    @Captor
    private ArgumentCaptor<OrderCommand> orderCommandArgumentCaptor;
    private ProcessOrderPromisePortService processOrderPromiseService;

    @BeforeEach
    public void before() {
        this.processOrderPromiseService = new ProcessOrderPromiseService(brokerCommandPublisher);
    }

    @Test
    public void itShouldBeAbleToPublishMessageToProcessOrder() {
        // Assemble
        Mockito
                .doNothing()
                .when(brokerCommandPublisher)
                .publishNotificationCommand(orderCommandArgumentCaptor.capture());

        var orderCommand = new OrderCommand();
        orderCommand.setId(UUID.randomUUID().toString());
        orderCommand.setWallet("my-wallet");
        orderCommand.setAmount(10);
        orderCommand.setPrice(100);
        orderCommand.setSymbol(Symbol.VBN);
        orderCommand.setType(OrderType.BUY);

        // Actions
        processOrderPromiseService.execute(orderCommand);

        // Assertions
        Assertions.assertEquals(orderCommand, orderCommandArgumentCaptor.getValue());
        Mockito.verify(brokerCommandPublisher, Mockito.times(1)).publishNotificationCommand(orderCommand);
    }
}
