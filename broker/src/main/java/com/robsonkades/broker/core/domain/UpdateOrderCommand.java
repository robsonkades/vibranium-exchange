package com.robsonkades.broker.core.domain;

public class UpdateOrderCommand {
    private String orderId;
    private String wallet;
    private OrderType side;
    private long price;
    private long executedQuantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public OrderType getSide() {
        return side;
    }

    public void setSide(OrderType type) {
        this.side = type;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(long executedQuantity) {
        this.executedQuantity = executedQuantity;
    }
}
