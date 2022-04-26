package com.robsonkades.broker.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletInfo {

    private String wallet;
    private Symbol symbol;
    private OrderType type;
    private long amount;
    private long price;

    public WalletInfo() {}

    public WalletInfo(String wallet, Symbol symbol, OrderType type, long amount, long price) {
        this.wallet = wallet;
        this.symbol = symbol;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public WalletInfo(String wallet, Symbol symbol, long amount, long price) {
        this.wallet = wallet;
        this.symbol = symbol;
        this.amount = amount;
        this.price = price;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void sumPrice(long price) {
        this.price += price;
    }

    public void sumAmount(long amount) {
        this.amount += amount;
    }
}
