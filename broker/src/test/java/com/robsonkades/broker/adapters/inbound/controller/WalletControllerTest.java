package com.robsonkades.broker.adapters.inbound.controller;

import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.GetWalletPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    private WalletController walletController;

    @Mock
    private GetWalletPortService getWalletPortService;

    @BeforeEach
    public void before() {
        this.walletController = new WalletController(getWalletPortService);
    }

    @Test
    public void itShouldBeAbleToGetWalletInfo() {
        // Assemble
        var wallet = new WalletInfo("my-wallet", Symbol.VBN,10, 200);
        Mockito
                .when(getWalletPortService.execute(Mockito.any(), Mockito.any()))
                .thenReturn(wallet);

        // Actions
        ResponseEntity<WalletInfo> response = walletController.create("my-wallet", Symbol.VBN);

        Mockito.verify(getWalletPortService, Mockito.times(1)).execute("my-wallet", Symbol.VBN);
        Assertions.assertEquals(wallet, response.getBody());
    }
}
