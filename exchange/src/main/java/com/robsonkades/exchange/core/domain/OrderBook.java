package com.robsonkades.exchange.core.domain;

import com.robsonkades.exchange.core.port.OrderBookPortListener;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeSet;

public class OrderBook {

    private final TreeSet<Order> bids;
    private final TreeSet<Order> asks;
    private final Map<String, Order> orders;
    private final OrderBookPortListener eventListener;
    private long nextOrderNumber;

    public OrderBook(OrderBookPortListener eventListener) {
        this.bids = new TreeSet<>(OrderBook::compareBids);
        this.asks = new TreeSet<>(OrderBook::compareAsks);
        this.orders = Collections.synchronizedMap(new Hashtable<>());
        this.eventListener = eventListener;
        this.nextOrderNumber = 0;
    }

    public Map<String, Order> getOrders() {
        return this.orders;
    }

    public synchronized void createOrder(String wallet, String orderId, Side side, long price, long size) {
        if (orders.containsKey(orderId))
            return;

        if (side == Side.BUY) {
            buy(wallet, orderId, price, size);
        } else {
            sell(wallet, orderId, price, size);
        }
    }

    private void sell(String wallet, String incomingId, long incomingPrice, long incomingQuantity) {
        while (!bids.isEmpty()) {
            Order resting = bids.first();

            long restingPrice = resting.getPrice();
            if (restingPrice < incomingPrice)
                break;

            String restingId = resting.getId();
            String walletResting = resting.getWallet();

            long restingQuantity = resting.getRemainingQuantity();
            if (restingQuantity > incomingQuantity) {
                resting.reduce(incomingQuantity);

                eventListener.match(walletResting, wallet, restingId, incomingId, Side.SELL, restingPrice, incomingQuantity, resting.getRemainingQuantity());

                return;
            }

            bids.remove(resting);
            orders.remove(restingId);

            eventListener.match(walletResting, wallet, restingId, incomingId, Side.SELL, restingPrice, restingQuantity, 0);

            incomingQuantity -= restingQuantity;
            if (incomingQuantity == 0)
                return;
        }

        add(wallet, incomingId, Side.SELL, incomingPrice, incomingQuantity, asks);
    }

    private void buy(String wallet, String incomingId, long incomingPrice, long incomingQuantity) {
        while (!asks.isEmpty()) {
            Order resting = asks.first();

            long restingPrice = resting.getPrice();
            if (restingPrice > incomingPrice)
                break;

            String restingId = resting.getId();
            String walletResting = resting.getWallet();

            long restingQuantity = resting.getRemainingQuantity();

            if (restingQuantity > incomingQuantity) {
                resting.reduce(incomingQuantity);
                eventListener.match(walletResting, wallet, restingId, incomingId, Side.BUY, restingPrice, incomingQuantity, resting.getRemainingQuantity());
                return;
            }

            asks.remove(resting);
            orders.remove(restingId);

            eventListener.match(walletResting, wallet, restingId, incomingId, Side.BUY, restingPrice, restingQuantity, 0);

            incomingQuantity -= restingQuantity;
            if (incomingQuantity == 0)
                return;
        }

        add(wallet, incomingId, Side.BUY, incomingPrice, incomingQuantity, bids);
    }

    private void add(String wallet, String orderId, Side side, long price, long size, TreeSet<Order> queue) {
        Order order = new Order(wallet, nextOrderNumber++, orderId, side, price, size);
        queue.add(order);
        orders.put(orderId, order);
        eventListener.add(wallet, orderId, side, price, size);
    }

    public void cancel(String orderId, long size) {
        Order order = orders.get(orderId);
        if (order == null)
            return;

        long remainingQuantity = order.getRemainingQuantity();

        if (size >= remainingQuantity)
            return;

        if (size > 0) {
            order.resize(size);
        } else {
            TreeSet<Order> queue = order.getSide() == Side.BUY ? bids : asks;

            queue.remove(order);
            orders.remove(orderId);
        }

        eventListener.cancel(order.getWallet(), orderId, remainingQuantity - size, size);
    }

    private static int compareBids(Order a, Order b) {
        int result = Long.compare(b.getPrice(), a.getPrice());
        if (result != 0)
            return result;
        return Long.compare(a.getNumber(), b.getNumber());
    }

    private static int compareAsks(Order a, Order b) {
        int result = Long.compare(a.getPrice(), b.getPrice());
        if (result != 0)
            return result;

        return Long.compare(a.getNumber(), b.getNumber());
    }
}
