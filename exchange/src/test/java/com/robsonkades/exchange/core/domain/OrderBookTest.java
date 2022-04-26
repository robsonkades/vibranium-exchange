package com.robsonkades.exchange.core.domain;

import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.OrderBookPortListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderBookTest {

    private OrderBook orderBook;
    private OrderBookPortListener orderBookPortListener;

    @BeforeEach
    public void before() {
        this.orderBookPortListener = new OrderBookPortListener() {

            List<OrderEvent.Event> events = new ArrayList<>();

            @Override
            public void cancel(String wallet, String orderId, long canceledQuantity, long remainingAmount) {

            }

            @Override
            public void add(String wallet, String orderId, Side side, long price, long amount) {
                events.add(new OrderEvent.Add(wallet, orderId, side, price, amount));
            }

            @Override
            public void match(String restingWallet, String incomingWallet, String restingOrderId, String incomingOrderId, Side incomingSide, long price, long executedAmount, long remainingAmount) {
                events.add(new OrderEvent.Match(restingWallet, incomingWallet, restingOrderId, incomingOrderId, incomingSide, price, executedAmount, remainingAmount));
            }

            @Override
            public List<OrderEvent.Event> events() {
                return events;
            }
        };

        this.orderBook = new OrderBook(this.orderBookPortListener);
    }

    @Test
    public void itShouldBeAbleToCreateBuyOrder() {
        // Assemble
        var addOrder = new OrderEvent.Add("my-wallet", "order-id", Side.BUY, 10, 100);

        // Actions
        this.orderBook.createOrder("my-wallet", "order-id", Side.BUY, 10, 100);

        // Assertions
        Assertions.assertEquals(List.of(addOrder), orderBookPortListener.events());
    }

    @Test
    public void itShouldBeAbleToCreateSellOrder() {
        // Assemble
        var addOrder = new OrderEvent.Add("my-wallet", "order-id", Side.SELL, 10, 100);

        // Actions
        this.orderBook.createOrder("my-wallet", "order-id", Side.SELL, 10, 100);

        // Assertions
        Assertions.assertEquals(List.of(addOrder), orderBookPortListener.events());
    }

    @Test
    public void itShouldBeAbleToMatchOrder() {
        // Assemble
        var buyOrder = new OrderEvent.Add("buy-wallet", "order-1", Side.BUY, 10, 100);
        var matchOrder = new OrderEvent.Match("buy-wallet", "sell-wallet", "order-1", "order-2", Side.SELL, 10, 100, 0);

        // Actions
        this.orderBook.createOrder("buy-wallet", "order-1", Side.BUY, 10, 100);
        this.orderBook.createOrder("sell-wallet", "order-2", Side.SELL, 10, 100);

        // Assertions
        Assertions.assertEquals(List.of(buyOrder, matchOrder), orderBookPortListener.events());
    }

    @Test
    public void itShouldBeAbleToMatchOrderAndCreateNewOrderIfHasRemaining() {
        // Assemble
        var buyOrder = new OrderEvent.Add("buy-wallet", "order-1", Side.BUY, 10, 100);
        var matchOrder = new OrderEvent.Match("buy-wallet", "sell-wallet", "order-1", "order-2", Side.SELL, 10, 100, 0);
        var remainingOrder = new OrderEvent.Add("sell-wallet", "order-2", Side.SELL, 10, 100);

        // Actions
        this.orderBook.createOrder("buy-wallet", "order-1", Side.BUY, 10, 100);
        this.orderBook.createOrder("sell-wallet", "order-2", Side.SELL, 10, 200);

        // Assertions
        Assertions.assertEquals(List.of(buyOrder, matchOrder, remainingOrder), orderBookPortListener.events());
    }
}
