package com.robsonkades.exchange.adapters.inbound.service;

import com.robsonkades.exchange.adapters.outbound.persistence.TransactionJpaRepository;
import com.robsonkades.exchange.adapters.outbound.persistence.entity.TransactionEntity;
import com.robsonkades.exchange.core.domain.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SaveTransactionTest {

    private SaveTransaction saveTransaction;

    @Mock
    private TransactionJpaRepository repository;

    @BeforeEach
    public void before() {
        this.saveTransaction = new SaveTransaction(repository);
    }

    @Test
    public void itShouldBeAbleToSaveTransaction() {
        // Assemble
        var transaction = new TransactionEntity();
        transaction.setId(UUID.randomUUID());
        transaction.setPrice(10);
        transaction.setCreationDate(LocalDateTime.now());
        transaction.setSide(Side.BUY);
        transaction.setExecutedQuantity(10);
        transaction.setIncomingOrderId("order-1");
        transaction.setIncomingWallet("order-2");
        transaction.setRestingWallet("wallet-1");
        transaction.setIncomingWallet("wallet-2");

        Mockito.when(repository.save(transaction)).thenReturn(transaction);

        // Action
        this.saveTransaction.execute(transaction);

        // Assemble
        Mockito.verify(repository, Mockito.times(1)).save(transaction);
    }
}
