package com.robsonkades.broker.adapters.dto;

import com.robsonkades.broker.core.domain.OrderType;

public class OrderExchangePayload {

    private String wallet;
    private OrderType type;
    private long amount;
    private long price;

    public OrderExchangePayload(String wallet, OrderType type, long amount, long price) {
        this.wallet = wallet;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public String getWallet() {
        return wallet;
    }

    public OrderType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public long getPrice() {
        return price;
    }
}
