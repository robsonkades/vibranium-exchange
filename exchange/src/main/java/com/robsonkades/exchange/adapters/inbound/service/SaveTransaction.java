package com.robsonkades.exchange.adapters.inbound.service;

import com.robsonkades.exchange.adapters.outbound.persistence.TransactionJpaRepository;
import com.robsonkades.exchange.adapters.outbound.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Service;

@Service
public class SaveTransaction {

    private TransactionJpaRepository repository;

    public SaveTransaction(final TransactionJpaRepository repository) {
        this.repository = repository;
    }

    public void execute(TransactionEntity transactionEntity) {
        repository.save(transactionEntity);
    }
}
