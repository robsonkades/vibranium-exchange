package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.GetWalletPortService;
import com.robsonkades.broker.core.port.WalletPortService;

import java.util.List;

public class GetWalletPortServiceImpl implements GetWalletPortService {

    private final WalletPortService walletPortService;

    public GetWalletPortServiceImpl(WalletPortService walletPortService) {
        this.walletPortService = walletPortService;
    }

    @Override
    public WalletInfo execute(String wallet, Symbol symbol) {
        List<WalletInfo> infos = walletPortService.read(wallet, symbol);
        var walletBuy = infos.stream()
                .filter(walletInfo -> walletInfo.getType().equals(OrderType.BUY))
                .reduce(new WalletInfo() , (previous, current) -> {
                    previous.sumPrice(current.getPrice());
                    previous.sumAmount(current.getAmount());
                    previous.setWallet(current.getWallet());
                    previous.setSymbol(current.getSymbol());
                    return previous;
                });

        var walletSell = infos.stream()
                .filter(walletInfo -> walletInfo.getType().equals(OrderType.SELL))
                .reduce(new WalletInfo() , (previous, current) -> {
                    previous.sumPrice(current.getPrice());
                    previous.sumAmount(current.getAmount());
                    previous.setWallet(current.getWallet());
                    previous.setSymbol(current.getSymbol());
                    return previous;
                });

        long amount = walletBuy.getAmount() - walletSell.getAmount();
        long price = walletBuy.getPrice() - walletSell.getPrice();

        return new WalletInfo(wallet, symbol, amount, price);
    }
}
