package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Wallet;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.port.WalletPortService;

import java.time.LocalDateTime;
import java.util.List;

public class WalletPortRepositoryImpl implements WalletPortService {

    private WalletJpaRepository repository;

    public WalletPortRepositoryImpl(final WalletJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UpdateOrderCommand updateOrderCommand) {
        Wallet wallet = new Wallet();
        wallet.setWallet(updateOrderCommand.getWallet());
        wallet.setType(updateOrderCommand.getSide());
        wallet.setAmount(updateOrderCommand.getExecutedQuantity());
        wallet.setCreationDate(LocalDateTime.now());
        wallet.setPrice(updateOrderCommand.getPrice());
        wallet.setSymbol(Symbol.VBN);
        wallet.setOrderId(updateOrderCommand.getOrderId());
        repository.save(wallet);
    }

    @Override
    public List<WalletInfo> read(String wallet, Symbol symbol) {
        return repository.listWallets(wallet, symbol);
    }
}
