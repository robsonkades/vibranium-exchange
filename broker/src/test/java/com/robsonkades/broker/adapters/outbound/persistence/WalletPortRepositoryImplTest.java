package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Wallet;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;
import com.robsonkades.broker.core.domain.WalletInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class WalletPortRepositoryImplTest {

    private WalletPortRepositoryImpl walletPortRepository;

    @Mock
    private WalletJpaRepository repository;

    @Captor
    private ArgumentCaptor<Wallet> walletArgumentCaptor;

    @BeforeEach
    public void before() {
        this.walletPortRepository = new WalletPortRepositoryImpl(repository);
    }

    @Test
    public void itShouldBeAbleToSaveWalletTransaction() {
        // Assemble
        var wallet = new Wallet();
        wallet.setId(UUID.randomUUID());
        wallet.setAmount(10);
        wallet.setWallet("my-wallet");
        wallet.setCreationDate(LocalDateTime.now());
        wallet.setPrice(100);
        wallet.setSymbol(Symbol.VBN);
        wallet.setOrderId("order-id");
        wallet.setType(OrderType.BUY);

        Mockito
                .when(repository.save(walletArgumentCaptor.capture()))
                .thenReturn(wallet);

        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand();
        updateOrderCommand.setWallet("my-wallet");
        updateOrderCommand.setOrderId("order-id");
        updateOrderCommand.setExecutedQuantity(10);
        updateOrderCommand.setPrice(100);
        updateOrderCommand.setSide(OrderType.BUY);

        // Actions
        this.walletPortRepository.save(updateOrderCommand);

        // Actions
        Mockito
                .verify(repository, Mockito.times(1))
                .save(walletArgumentCaptor.getValue());

        Assertions.assertEquals(wallet.getWallet(), walletArgumentCaptor.getValue().getWallet());
        Assertions.assertEquals(wallet.getOrderId(), walletArgumentCaptor.getValue().getOrderId());
        Assertions.assertEquals(wallet.getAmount(), walletArgumentCaptor.getValue().getAmount());
        Assertions.assertEquals(wallet.getPrice(), walletArgumentCaptor.getValue().getPrice());
        Assertions.assertEquals(wallet.getSymbol(), walletArgumentCaptor.getValue().getSymbol());
    }

    @Test
    public void itShouldBeAbleToReadWalletTransaction() {
        // Assemble
        var response = List.of(
                new WalletInfo("my-wallet", Symbol.VBN, OrderType.BUY, 10, 100),
                new WalletInfo("my-wallet", Symbol.VBN, OrderType.SELL, 20, 100),
                new WalletInfo("my-wallet", Symbol.VBN, OrderType.BUY, 30, 100));

        Mockito
                .when(repository.listWallets("my-wallet", Symbol.VBN))
                .thenReturn(response);

        // Actions
        var actual = this.walletPortRepository.read("my-wallet", Symbol.VBN);

        // Assertions
        Assertions.assertIterableEquals(response, actual);
    }
}
