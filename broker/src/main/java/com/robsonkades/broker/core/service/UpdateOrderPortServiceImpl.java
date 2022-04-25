package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.port.UpdateOrderPortService;
import com.robsonkades.broker.core.port.WalletPortService;

public class UpdateOrderPortServiceImpl implements UpdateOrderPortService {

    private final WalletPortService walletPortService;

    public UpdateOrderPortServiceImpl(WalletPortService walletPortService) {
        this.walletPortService = walletPortService;
    }

    @Override
    public void execute(final UpdateOrderCommand updateOrderCommand) {
        walletPortService.save(updateOrderCommand);
    }
}
