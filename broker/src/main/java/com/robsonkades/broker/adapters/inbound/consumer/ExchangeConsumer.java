package com.robsonkades.broker.adapters.inbound.consumer;

import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.port.WalletPortService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ExchangeConsumer {

    private WalletPortService walletPortService;

    public ExchangeConsumer(final WalletPortService walletPortService) {
        this.walletPortService = walletPortService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${meli.broker.queue.vibraniumExchangeEventMatchQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${meli.broker.exchange.vibraniumExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${meli.broker.key.vibraniumEventMatchCommandKey}")
    )
    @Transactional
    @Async
    public void consumer(@Payload UpdateOrderCommand updateOrderCommand) {
        walletPortService.save(updateOrderCommand);
    }
}
