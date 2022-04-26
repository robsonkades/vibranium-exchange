package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.CreateWalletPortService;
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

@ExtendWith(MockitoExtension.class)
public class CreateWalletPortServiceImplTest {

    private CreateWalletPortService createWalletPortService;

    @Mock
    private WalletPortService walletPortService;

    @Captor
    private ArgumentCaptor<UpdateOrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.createWalletPortService = new CreateWalletPortServiceImpl(walletPortService);
    }

    @Test
    public void itShouldBeAbleToCreateWallet() {
        // Assemble
        Mockito.doNothing()
                .when(walletPortService)
                .save(orderCommandArgumentCaptor.capture());

        // Actions
        var input = new WalletInfo();
        input.setWallet("wallet");
        input.setSymbol(Symbol.VBN);
        input.setPrice(100);
        input.setAmount(1000);

        String id = createWalletPortService.execute(input);

        // Assertions
        orderCommandArgumentCaptor.getValue();
        Assertions.assertEquals(input.getWallet(), orderCommandArgumentCaptor.getValue().getWallet());
        Assertions.assertEquals(id, orderCommandArgumentCaptor.getValue().getOrderId());
        Assertions.assertEquals(input.getPrice(), orderCommandArgumentCaptor.getValue().getPrice());
        Assertions.assertEquals(input.getAmount(), orderCommandArgumentCaptor.getValue().getExecutedQuantity());
        Assertions.assertEquals(OrderType.BUY, orderCommandArgumentCaptor.getValue().getSide());
    }
}
