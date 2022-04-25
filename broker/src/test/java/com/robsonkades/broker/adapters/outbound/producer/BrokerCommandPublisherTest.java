package com.robsonkades.broker.adapters.outbound.producer;

import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BrokerCommandPublisherTest {

    private BrokerCommandPublisher brokerCommandPublisher;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Captor
    private ArgumentCaptor<OrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.brokerCommandPublisher = new BrokerCommandPublisher(rabbitTemplate);
    }

    @Test
    public void itShouldBeAbleToPublishMessage() {
        // Assemble
        Mockito.doNothing().when(rabbitTemplate).convertAndSend(Mockito.any(), Mockito.any(), orderCommandArgumentCaptor.capture());

        var order = new OrderCommand();
        order.setAmount(10);
        order.setWallet("wallet");
        order.setSymbol(Symbol.VBN);
        order.setType(OrderType.BUY);
        order.setId(UUID.randomUUID().toString());
        order.setPrice(200);

        // Actions
        brokerCommandPublisher.publishNotificationCommand(order);

        // Assertions
        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend(null, null, orderCommandArgumentCaptor.getValue());
        Assertions.assertEquals(order, orderCommandArgumentCaptor.getValue());
    }
}
