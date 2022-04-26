package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Order;
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

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OrderPortRepositoryImplTest {

    private OrderPortRepositoryImpl orderPortRepository;
    @Mock
    private OrderJpaRepository repository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @BeforeEach
    public void before() {
        this.orderPortRepository = new OrderPortRepositoryImpl(repository);
    }

    @Test
    public void itShouldBeAbleToSaveOrder() {
        // Assemble
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrderExchange("id-order-exchange");
        order.setType(OrderType.BUY);
        order.setWallet("my-wallet");
        order.setPrice(10);
        order.setSymbol(Symbol.VBN);
        order.setAmount(100);
        order.setCreationDate(LocalDateTime.now());

        Mockito.when(repository.saveAndFlush(orderArgumentCaptor.capture())).thenReturn(order);

        var orderCommand = new OrderCommand();
        orderCommand.setSymbol(Symbol.VBN);
        orderCommand.setType(OrderType.BUY);
        orderCommand.setPrice(10);
        orderCommand.setWallet("my-wallet");
        orderCommand.setAmount(100);

        // Actions
        orderPortRepository.create("id-order-exchange", orderCommand);

        // Assertions
        Mockito
                .verify(repository, Mockito.times(1))
                .saveAndFlush(orderArgumentCaptor.getValue());

        Assertions.assertEquals(order.getAmount(), orderArgumentCaptor.getValue().getAmount());
        Assertions.assertEquals(order.getOrderExchange(), orderArgumentCaptor.getValue().getOrderExchange());
        Assertions.assertEquals(order.getWallet(), orderArgumentCaptor.getValue().getWallet());
        Assertions.assertEquals(order.getPrice(), orderArgumentCaptor.getValue().getPrice());
        Assertions.assertEquals(order.getSymbol(), orderArgumentCaptor.getValue().getSymbol());
        Assertions.assertEquals(order.getType(), orderArgumentCaptor.getValue().getType());
    }
}
