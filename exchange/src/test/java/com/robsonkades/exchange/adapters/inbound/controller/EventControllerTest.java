package com.robsonkades.exchange.adapters.inbound.controller;

import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.ListEventsPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    private EventController eventController;

    @Mock
    private ListEventsPortService listEventsPortService;

    @BeforeEach
    public void before() {
        this.eventController = new EventController(listEventsPortService);
    }

    @Test
    public void itShouldBeAbleToListEvents() {
        // Assemble

        var buyOrder = new OrderEvent.Add("buy-wallet", "order-1", Side.BUY, 10, 100);
        var matchOrder = new OrderEvent.Match("buy-wallet", "sell-wallet", "order-1", "order-2", Side.SELL, 10, 100, 0);
        var remainingOrder = new OrderEvent.Add("sell-wallet", "order-2", Side.SELL, 10, 100);

        Mockito
                .when(listEventsPortService.execute())
                .thenReturn(List.of(buyOrder, matchOrder, remainingOrder));

        // Actions
        ResponseEntity<List<OrderEvent.Event>> listResponseEntity = this.eventController.index();

        // Assertions
        Assertions.assertEquals(List.of(buyOrder, matchOrder, remainingOrder), listResponseEntity.getBody());
        Mockito.verify(listEventsPortService, Mockito.times(1)).execute();
    }
}
