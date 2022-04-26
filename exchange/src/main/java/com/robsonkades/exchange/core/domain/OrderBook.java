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

    public synchronized void createOrder(String wallet, String orderId, Side side, long price, long amount) {
        if (orders.containsKey(orderId))
            return;

        if (side == Side.BUY) {
            buy(wallet, orderId, price, amount);
        } else {
            sell(wallet, orderId, price, amount);
        }
    }

    private void sell(String wallet, String incomingId, long incomingPrice, long incomingAmount) {
        while (!bids.isEmpty()) {
            Order resting = bids.first();

            long restingPrice = resting.getPrice();
            if (restingPrice < incomingPrice)
                break;

            String restingId = resting.getId();
            String walletResting = resting.getWallet();

            long restingAmount = resting.getRemainingAmount();
            if (restingAmount > incomingAmount) {
                resting.reduce(incomingAmount);

                eventListener.match(walletResting, wallet, restingId, incomingId, Side.SELL, restingPrice, incomingAmount, resting.getRemainingAmount());

                return;
            }

            bids.remove(resting);
            orders.remove(restingId);

            eventListener.match(walletResting, wallet, restingId, incomingId, Side.SELL, restingPrice, restingAmount, 0);

            incomingAmount -= restingAmount;
            if (incomingAmount == 0)
                return;
        }

        add(wallet, incomingId, Side.SELL, incomingPrice, incomingAmount, asks);
    }

    private void buy(String wallet, String incomingId, long incomingPrice, long incomingAmount) {
        while (!asks.isEmpty()) {
            Order resting = asks.first();

            long restingPrice = resting.getPrice();
            if (restingPrice > incomingPrice)
                break;

            String restingId = resting.getId();
            String walletResting = resting.getWallet();

            long restingQuantity = resting.getRemainingAmount();

            if (restingQuantity > incomingAmount) {
                resting.reduce(incomingAmount);
                eventListener.match(walletResting, wallet, restingId, incomingId, Side.BUY, restingPrice, incomingAmount, resting.getRemainingAmount());
                return;
            }

            asks.remove(resting);
            orders.remove(restingId);

            eventListener.match(walletResting, wallet, restingId, incomingId, Side.BUY, restingPrice, restingQuantity, 0);

            incomingAmount -= restingQuantity;
            if (incomingAmount == 0)
                return;
        }

        add(wallet, incomingId, Side.BUY, incomingPrice, incomingAmount, bids);
    }

    private void add(String wallet, String orderId, Side side, long price, long amount, TreeSet<Order> queue) {
        Order order = new Order(wallet, nextOrderNumber++, orderId, side, price, amount);
        queue.add(order);
        orders.put(orderId, order);
        eventListener.add(wallet, orderId, side, price, amount);
    }

    public void cancel(String orderId, long amount) {
        Order order = orders.get(orderId);
        if (order == null)
            return;

        long remainingAmount = order.getRemainingAmount();

        if (amount >= remainingAmount)
            return;

        if (amount > 0) {
            order.resize(amount);
        } else {
            TreeSet<Order> queue = order.getSide() == Side.BUY ? bids : asks;

            queue.remove(order);
            orders.remove(orderId);
        }

        eventListener.cancel(order.getWallet(), orderId, remainingAmount - amount, amount);
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
