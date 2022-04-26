package com.robsonkades.exchange.adapters.outbound.event;

import com.robsonkades.exchange.adapters.inbound.service.SaveTransaction;
import com.robsonkades.exchange.adapters.outbound.persistence.entity.TransactionEntity;
import com.robsonkades.exchange.adapters.outbound.producer.ExchangeCommandPublisher;
import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.domain.UpdateOrderCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderBookListenerImplTest {

    private OrderBookListenerImpl orderBookListenerImpl;

    @Mock
    private SaveTransaction saveTransaction;
    @Mock
    private ExchangeCommandPublisher exchangeCommandPublisher;

    @Captor
    private ArgumentCaptor<TransactionEntity> transactionEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<UpdateOrderCommand> orderCommandArgumentCaptor;

    @BeforeEach
    public void before() {
        this.orderBookListenerImpl = new OrderBookListenerImpl(saveTransaction, exchangeCommandPublisher);
    }

    @Test
    public void itShouldBeAbleToMatchEvent() {
        // Assemble
        Mockito
                .doNothing()
                .when(saveTransaction)
                .execute(transactionEntityArgumentCaptor.capture());

        Mockito
                .doNothing()
                .when(exchangeCommandPublisher)
                .publishNotificationCommand(orderCommandArgumentCaptor.capture());

        // Action
        this.orderBookListenerImpl.match("buy-wallet", "sell-wallet", "order-1", "order-2", Side.SELL, 10, 100, 0);

        // Assertions
        Mockito
                .verify(saveTransaction, Mockito.times(1))
                .execute(transactionEntityArgumentCaptor.getValue());
        Mockito
                .verify(saveTransaction, Mockito.times(1))
                .execute(transactionEntityArgumentCaptor.getValue());
    }

}
