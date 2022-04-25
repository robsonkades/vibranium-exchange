package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.adapters.dto.CreateOrderInputDto;
import com.robsonkades.exchange.core.domain.OrderBook;
import com.robsonkades.exchange.core.domain.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateOrderPortServiceImplTest {

    private CreateOrderPortServiceImpl createOrderPortServiceImpl;

    @Mock
    private OrderBook orderBook;

    @BeforeEach
    public void before() {
        this.createOrderPortServiceImpl = new CreateOrderPortServiceImpl(orderBook);
    }

    @Test
    public void itShouldBeAbleToCreateOrder() {
        // Assemble
        CreateOrderInputDto inputDto = new CreateOrderInputDto();
        inputDto.setType(Side.BUY);
        inputDto.setPrice(10);
        inputDto.setWallet("wallet");
        inputDto.setAmount(100);

        Mockito
                .doNothing()
                .when(orderBook)
                .createOrder(
                        Mockito.eq("wallet"),
                        Mockito.anyString(),
                        Mockito.eq(Side.BUY),
                        Mockito.eq(10L),
                        Mockito.eq(100L));

        // Actions
        String orderId = this.createOrderPortServiceImpl.execute(inputDto);

        // Assertions
        Assertions.assertNotNull(orderId);
        Mockito
                .verify(orderBook, Mockito.times(1))
                .createOrder("wallet", orderId, Side.BUY, 10, 100);
    }
}
