package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Wallet;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WalletJpaRepository extends JpaRepository<Wallet, UUID> {

    @Query(value = "SELECT new com.robsonkades.broker.core.domain.WalletInfo(w.wallet as wallet, w.symbol as symbol, w.type as type, sum(w.amount) as sum, sum(w.price) as price) FROM Wallet AS w WHERE w.wallet = :wallet and w.symbol = :symbol GROUP BY w.wallet, w.symbol, w.type")
    List<WalletInfo> listWallets(String wallet, Symbol symbol);
}
