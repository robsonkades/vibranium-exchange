package com.robsonkades.exchange.adapters.outbound.producer;

import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.domain.UpdateOrderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
public class ExchangeCommandPublisherTest {

    private ExchangeCommandPublisher exchangeCommandPublisher;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    public void before() {
        this.exchangeCommandPublisher = new ExchangeCommandPublisher(rabbitTemplate);
    }

    @Test
    public void itShouldBeAbleToPublishMessage() {
        // Assemble
        var command = new UpdateOrderCommand("order-id", "wallet", Side.BUY, 100, 50);
        Mockito
                .doNothing()
                .when(rabbitTemplate)
                .convertAndSend(null, null, command);

        // Actions
        exchangeCommandPublisher.publishNotificationCommand(command);

        // Assertions
        Mockito
                .verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(null, null, command);
    }
}
