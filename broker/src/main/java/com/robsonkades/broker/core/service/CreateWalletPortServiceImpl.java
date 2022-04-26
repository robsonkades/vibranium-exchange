package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.CreateWalletPortService;
import com.robsonkades.broker.core.port.WalletPortService;

import java.util.UUID;

public class CreateWalletPortServiceImpl implements CreateWalletPortService {

    private final WalletPortService walletPortService;

    public CreateWalletPortServiceImpl(final WalletPortService walletPortService) {
        this.walletPortService = walletPortService;
    }

    @Override
    public String execute(WalletInfo walletInfo) {
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand();
        updateOrderCommand.setWallet(walletInfo.getWallet());
        updateOrderCommand.setOrderId(UUID.randomUUID().toString());
        updateOrderCommand.setSide(OrderType.BUY);
        updateOrderCommand.setPrice(walletInfo.getPrice());
        updateOrderCommand.setExecutedQuantity(walletInfo.getAmount());
        walletPortService.save(updateOrderCommand);
        return updateOrderCommand.getOrderId();
    }
}
