package com.robsonkades.broker.adapters.inbound.consumer;

import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.port.WalletPortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ExchangeConsumerTest {

    private ExchangeConsumer exchangeConsumer;

    @Mock
    private WalletPortService walletPortService;
    @Captor
    private ArgumentCaptor<UpdateOrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.exchangeConsumer = new ExchangeConsumer(walletPortService);
    }

    @Test
    public void itShouldBeAbleToSaveWalletTransaction() {
        // Assemble
        Mockito.doNothing().when(walletPortService).save(orderCommandArgumentCaptor.capture());

        var response = new UpdateOrderCommand();
        response.setOrderId(UUID.randomUUID().toString());
        response.setPrice(10);
        response.setWallet("my-wallet");
        response.setSide(OrderType.BUY);
        response.setExecutedQuantity(100);

        // Actions
        this.exchangeConsumer.consumer(response);

        // Actions
        Mockito.verify(walletPortService, Mockito.times(1)).save(response);
    }
}
