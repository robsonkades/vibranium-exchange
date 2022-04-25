package com.robsonkades.exchange.core.service;

import com.robsonkades.exchange.core.domain.Order;
import com.robsonkades.exchange.core.domain.OrderBook;
import com.robsonkades.exchange.core.domain.Side;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Hashtable;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ListOrdersPortServiceImplTest {

    private ListOrdersPortServiceImpl listOrdersPortServiceImpl;

    @Mock
    private OrderBook orderBook;

    @BeforeEach
    public void before() {
        this.listOrdersPortServiceImpl = new ListOrdersPortServiceImpl(orderBook);
    }

    @Test
    public void itShouldBeAbleToListOrders() {
        // Assemble
        Map<String, Order> orders = new Hashtable<>();
        orders.put("id-1", new Order("wallet-1", 1, "id-1", Side.BUY, 100, 10));
        orders.put("id-2", new Order("wallet-1", 2, "id-2", Side.SELL, 100, 10));
        orders.put("id-3", new Order("wallet-1", 3, "id-3", Side.BUY, 100, 10));

        Mockito
                .when(orderBook.getOrders())
                .thenReturn(orders);

        // Actions
        var response = this.listOrdersPortServiceImpl.execute();

        // Assertions
        Assertions.assertEquals(orders, response);
    }
}
