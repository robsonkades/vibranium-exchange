package com.robsonkades.exchange.core.port;

import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.event.OrderEvent;

import java.util.List;

public interface OrderBookPortListener {
    void cancel(String wallet, String orderId, long canceledQuantity, long remainingQuantity);
    void add(String wallet, String orderId, Side side, long price, long size);
    void match(String restingWallet, String incomingWallet, String restingOrderId, String incomingOrderId, Side incomingSide,
               long price, long executedQuantity, long remainingQuantity);

    List<OrderEvent.Event> events();
}
