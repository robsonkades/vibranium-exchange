package com.robsonkades.exchange.core.domain;

public class UpdateOrderCommand {
    private String orderId;
    private String wallet;
    private Side side;
    private long price;
    private long executedQuantity;

    public UpdateOrderCommand(String orderId, String wallet, Side side, long price, long executedQuantity) {
        this.orderId = orderId;
        this.wallet = wallet;
        this.side = side;
        this.price = price;
        this.executedQuantity = executedQuantity;
    }

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

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
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
