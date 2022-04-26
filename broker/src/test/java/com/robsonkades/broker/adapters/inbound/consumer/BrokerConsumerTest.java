package com.robsonkades.broker.adapters.inbound.consumer;

import com.robsonkades.broker.adapters.dto.OrderExchangePayload;
import com.robsonkades.broker.adapters.outbound.persistence.entity.Order;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.port.OrderPortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BrokerConsumerTest {

    private BrokerConsumer brokerConsumer;
    @Mock
    private OrderPortRepository orderPortRepository;
    @Mock
    private RestTemplate restTemplate;

    @Captor
    private ArgumentCaptor<HttpEntity<OrderExchangePayload>> httpEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> orderExchangeCaptor;

    @Captor
    private ArgumentCaptor<OrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.brokerConsumer = new BrokerConsumer(orderPortRepository, restTemplate);
    }

    @Test
    public void itShouldBeAbleToCreateOrderToExchange() {
        // Assemble
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setAmount(10);
        order.setPrice(100);
        order.setWallet("mock-wallet");
        order.setSymbol(Symbol.VBN);
        order.setOrderExchange("id-transaction-response");
        order.setType(OrderType.BUY);

        Mockito.when(restTemplate
                        .exchange(
                                Mockito.eq("null/orders"),
                                Mockito.eq(HttpMethod.POST),
                                httpEntityArgumentCaptor.capture(),
                                Mockito.eq(String.class)))
                .thenReturn(new ResponseEntity<>("id-transaction-response", HttpStatus.OK));

        Mockito.when(orderPortRepository
                        .create(
                                orderExchangeCaptor.capture(),
                                orderCommandArgumentCaptor.capture()))
                .thenReturn(order);

        var payload = new OrderCommand();
        payload.setType(OrderType.BUY);
        payload.setSymbol(Symbol.VBN);
        payload.setPrice(100);
        payload.setAmount(10);
        payload.setId(UUID.randomUUID().toString());
        payload.setWallet("mock-wallet");

        // Actions
        brokerConsumer.consumer(payload);

        // Assertions
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange("null/orders", HttpMethod.POST, httpEntityArgumentCaptor.getValue(), String.class);
        Mockito.verify(orderPortRepository, Mockito.times(1))
                .create(orderExchangeCaptor.getValue(), orderCommandArgumentCaptor.getValue());

    }
}
