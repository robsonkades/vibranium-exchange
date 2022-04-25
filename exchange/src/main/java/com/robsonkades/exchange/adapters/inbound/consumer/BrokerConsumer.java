package com.robsonkades.exchange.adapters.inbound.consumer;

import com.robsonkades.exchange.adapters.dto.BrokerCommandDto;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class BrokerConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${meli.broker.queue.vibraniumExchangeBrokerCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${meli.broker.exchange.vibraniumExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${meli.broker.key.vibraniumBrokerCommandKey}")
    )

    @Async
    public void consumer(@Payload BrokerCommandDto brokerCommandDto) {
        // TODO: Criar orders assincronas.
    }
}
