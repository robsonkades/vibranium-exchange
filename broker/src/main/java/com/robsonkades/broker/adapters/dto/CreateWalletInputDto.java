package com.robsonkades.broker.adapters.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateWalletInputDto {

    @NotBlank
    private String wallet;

    @Min(1)
    private long amount;

    @Min(1)
    private long price;

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
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
