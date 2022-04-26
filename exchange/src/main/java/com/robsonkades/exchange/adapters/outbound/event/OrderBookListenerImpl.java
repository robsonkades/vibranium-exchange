package com.robsonkades.exchange.adapters.outbound.event;

import com.robsonkades.exchange.adapters.inbound.service.SaveTransaction;
import com.robsonkades.exchange.adapters.outbound.persistence.entity.TransactionEntity;
import com.robsonkades.exchange.adapters.outbound.producer.ExchangeCommandPublisher;
import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.domain.UpdateOrderCommand;
import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.OrderBookPortListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderBookListenerImpl implements OrderBookPortListener {

    private final SaveTransaction saveTransaction;
    private final ExchangeCommandPublisher exchangeCommandPublisher;
    private final List<OrderEvent.Event> events = new ArrayList<>();

    public OrderBookListenerImpl(final SaveTransaction saveTransaction, final ExchangeCommandPublisher exchangeCommandPublisher) {
        this.saveTransaction = saveTransaction;
        this.exchangeCommandPublisher = exchangeCommandPublisher;
    }

    @Override
    public void cancel(String wallet, String orderId, long canceledQuantity, long remainingAmount) {
    }

    @Override
    public void add(String wallet, String orderId, Side side, long price, long amount) {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void match(String restingWallet, String incomingWallet, String restingOrderId, String incomingOrderId, Side incomingSide, long price, long executedAmount, long remainingAmount) {

        TransactionEntity transaction = new TransactionEntity();
        transaction.setRestingWallet(restingWallet);
        transaction.setIncomingWallet(incomingWallet);
        transaction.setRestingOrderId(restingOrderId);
        transaction.setIncomingOrderId(incomingOrderId);
        transaction.setSide(incomingSide);
        transaction.setPrice(price);
        transaction.setExecutedQuantity(executedAmount);
        transaction.setCreationDate(LocalDateTime.now());
        saveTransaction.execute(transaction);

        var sellOrder = new UpdateOrderCommand(
                transaction.getRestingOrderId(),
                transaction.getRestingWallet(),
                Side.SELL,
                transaction.getPrice(),
                transaction.getExecutedQuantity());

        var buyOrder = new UpdateOrderCommand(
                transaction.getIncomingOrderId(),
                transaction.getIncomingWallet(),
                Side.BUY,
                transaction.getPrice(),
                transaction.getExecutedQuantity());

        events.add(new OrderEvent.Match(
                transaction.getRestingWallet(),
                transaction.getIncomingWallet(),
                transaction.getRestingOrderId(),
                transaction.getIncomingOrderId(),
                incomingSide,
                price,
                executedAmount,
                remainingAmount
        ));

        exchangeCommandPublisher.publishNotificationCommand(sellOrder);
        exchangeCommandPublisher.publishNotificationCommand(buyOrder);
    }

    @Override
    public List<OrderEvent.Event> events() {
        return events;
    }
}
