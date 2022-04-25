package com.robsonkades.exchange.adapters.dto;

import com.robsonkades.exchange.core.domain.Side;

public class OrderResponseDto {

    private Side type;
    private long amount;
    private long price;

    public OrderResponseDto(Side type, long amount, long price) {
        this.type = type;
        this.amount = amount;
        this.price = price;
    }

    public Side getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public long getPrice() {
        return price;
    }
}
