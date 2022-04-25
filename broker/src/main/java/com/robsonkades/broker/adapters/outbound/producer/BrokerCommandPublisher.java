package com.robsonkades.broker.adapters.outbound.producer;

import com.robsonkades.broker.core.domain.OrderCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BrokerCommandPublisher {

    private RabbitTemplate rabbitTemplate;

    public BrokerCommandPublisher(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${meli.broker.exchange.brokerExchange}")
    private String brokerExchange;

    @Value(value = "${meli.broker.key.brokerCommandKey}")
    private String brokerEventMatchCommandKey;

    @Async
    public void publishNotificationCommand(OrderCommand orderCommand) {
        rabbitTemplate.convertAndSend(brokerExchange, brokerEventMatchCommandKey, orderCommand);
    }
}
