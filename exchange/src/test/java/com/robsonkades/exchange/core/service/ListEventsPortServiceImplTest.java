package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.OrderBookPortListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListEventsPortServiceImplTest {

    private ListEventsPortServiceImpl listEventsPortServiceImpl;
    @Mock
    private OrderBookPortListener orderBookPortListener;

    @BeforeEach
    public void before() {
        listEventsPortServiceImpl = new ListEventsPortServiceImpl(orderBookPortListener);
    }

    @Test
    public void itShouldBeAbleToListEvents() {
        // Assemble

        var buyOrder = new OrderEvent.Add("buy-wallet", "order-1", Side.BUY, 10, 100);
        var matchOrder = new OrderEvent.Match("buy-wallet", "sell-wallet", "order-1", "order-2", Side.SELL, 10, 100, 0);
        var remainingOrder = new OrderEvent.Add("sell-wallet", "order-2", Side.SELL, 10, 100);


        Mockito
                .when(orderBookPortListener.events())
                .thenReturn(List.of(buyOrder, matchOrder, remainingOrder));

        // Actions
        var response = listEventsPortServiceImpl.execute();

        // Assertions
        Assertions.assertEquals(List.of(buyOrder, matchOrder, remainingOrder), response);
    }
}
