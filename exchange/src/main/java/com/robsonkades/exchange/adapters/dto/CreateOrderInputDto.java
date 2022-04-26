package com.robsonkades.exchange.adapters.dto;

import com.robsonkades.exchange.core.domain.Side;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateOrderInputDto {

    @NotBlank
    private String wallet;
    @NotNull
    private Side type;
    @Min(value = 1)
    private long amount;
    @Min(value = 1)
    private long price;

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Side getType() {
        return type;
    }

    public void setType(Side type) {
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
}
