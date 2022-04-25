package com.robsonkades.broker.adapters.inbound.consumer;

import com.robsonkades.broker.adapters.dto.OrderExchangePayload;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.port.OrderPortRepository;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class BrokerConsumer {

    private OrderPortRepository orderPortRepository;
    private RestTemplate restTemplate;

    @Value(value = "${meli.hostExchange}")
    private String hostExchange;

    public BrokerConsumer(final OrderPortRepository orderPortRepository, final RestTemplate restTemplate) {
        this.orderPortRepository = orderPortRepository;
        this.restTemplate = restTemplate;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${meli.broker.queue.brokerCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${meli.broker.exchange.brokerExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${meli.broker.key.brokerCommandKey}")
    )
    @Transactional
    @Async
    public void consumer(@Payload OrderCommand orderCommand) {
        HttpEntity<OrderExchangePayload> request = new HttpEntity<>(new OrderExchangePayload(orderCommand.getWallet(), orderCommand.getType(), orderCommand.getAmount(), orderCommand.getPrice()));
        String host = String.format("%s/orders", hostExchange);
        ResponseEntity<String> response = restTemplate.exchange(host, HttpMethod.POST, request, String.class);
        String orderId = response.getBody();
        orderPortRepository.create(orderId, orderCommand);
    }
}
