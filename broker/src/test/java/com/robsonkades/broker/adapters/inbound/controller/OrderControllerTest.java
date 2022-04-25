package com.robsonkades.broker.adapters.inbound.controller;

import com.robsonkades.broker.adapters.dto.CreateOrderInputDto;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.port.CreateOrderPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private OrderController orderController;

    @Mock
    private CreateOrderPortService createOrderPortService;

    @Captor
    private ArgumentCaptor<OrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.orderController = new OrderController(createOrderPortService);
    }

    @Test
    public void itShouldBeAbleToCreateOrder() {
        // Assemble
        Mockito
                .when(createOrderPortService.execute(orderCommandArgumentCaptor.capture()))
                .thenReturn("id-transaction");

        var payload = new CreateOrderInputDto();
        payload.setAmount(10);
        payload.setPrice(100);
        payload.setSymbol(Symbol.VBN);
        payload.setWallet("id-wallet");
        payload.setType(OrderType.BUY);

        // Actions
        this.orderController.create(payload);

        // Assertions
        Mockito
                .verify(createOrderPortService, Mockito.times(1))
                .execute(orderCommandArgumentCaptor.getValue());

        Assertions.assertEquals(payload.getSymbol(), orderCommandArgumentCaptor.getValue().getSymbol());
        Assertions.assertEquals(payload.getAmount(), orderCommandArgumentCaptor.getValue().getAmount());
        Assertions.assertEquals(payload.getPrice(), orderCommandArgumentCaptor.getValue().getPrice());
        Assertions.assertEquals(payload.getType(), orderCommandArgumentCaptor.getValue().getType());
        Assertions.assertEquals(payload.getWallet(), orderCommandArgumentCaptor.getValue().getWallet());
    }
}
