package com.robsonkades.exchange.core.domain;

public class Order {

    private final String wallet;

    private final long number;

    private final String id;

    private final Side side;

    private final long price;

    private long remainingQuantity;

    public Order(String wallet, long number, String id, Side side, long price, long size) {
        this.wallet = wallet;
        this.number = number;
        this.id = id;
        this.side = side;
        this.price = price;
        this.remainingQuantity = size;
    }

    public String getWallet() {
        return wallet;
    }

    public long getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public Side getSide() {
        return side;
    }

    public long getPrice() {
        return price;
    }

    public long getRemainingQuantity() {
        return remainingQuantity;
    }

    void reduce(long quantity) {
        remainingQuantity -= quantity;
    }

    void resize(long size) {
        remainingQuantity = size;
    }

}
