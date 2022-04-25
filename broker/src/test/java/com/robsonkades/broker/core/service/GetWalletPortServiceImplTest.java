package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.GetWalletPortService;
import com.robsonkades.broker.core.port.WalletPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GetWalletPortServiceImplTest {

    @Mock
    private WalletPortService walletPortService;
    @Captor
    private ArgumentCaptor<String> walletCaptor;
    @Captor
    private ArgumentCaptor<Symbol> symbolCaptor;
    private GetWalletPortService getWalletPortService;

    @BeforeEach
    public void before() {
        this.getWalletPortService = new GetWalletPortServiceImpl(walletPortService);
    }

    @Test
    public void itShouldBeAbleToGetWallet() {
        // Assemble
        Mockito
                .when(walletPortService.read(walletCaptor.capture(), symbolCaptor.capture()))
                .thenReturn(List.of(
                        new WalletInfo("my-wallet", Symbol.VBN, OrderType.BUY, 10, 20),
                        new WalletInfo("my-wallet", Symbol.VBN, OrderType.BUY, 15, 150),
                        new WalletInfo("my-wallet", Symbol.VBN, OrderType.BUY, 35, 350),
                        new WalletInfo("my-wallet", Symbol.VBN, OrderType.SELL, 20, 200)
                ));

        // Actions
        WalletInfo walletInfo = getWalletPortService.execute("my-wallet", Symbol.VBN);

        // Assertions
        Assertions.assertEquals("my-wallet", walletInfo.getWallet());
        Assertions.assertEquals(40, walletInfo.getAmount());
        Assertions.assertEquals(320, walletInfo.getPrice());
        Mockito.verify(walletPortService, Mockito.times(1)).read(walletCaptor.getValue(), symbolCaptor.getValue());
    }
}
