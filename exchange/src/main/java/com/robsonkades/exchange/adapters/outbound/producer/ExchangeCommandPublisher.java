package com.robsonkades.exchange.adapters.outbound.producer;

import com.robsonkades.exchange.core.domain.UpdateOrderCommand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExchangeCommandPublisher {

    private RabbitTemplate rabbitTemplate;

    public ExchangeCommandPublisher(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${meli.broker.exchange.vibraniumExchange}")
    private String vibraniumExchange;

    @Value(value = "${meli.broker.key.vibraniumEventMatchCommandKey}")
    private String vibraniumEventMatchCommandKey;

    @Async
    public void publishNotificationCommand(UpdateOrderCommand updateOrderCommand) {
        rabbitTemplate.convertAndSend(vibraniumExchange, vibraniumEventMatchCommandKey, updateOrderCommand);
    }
}
