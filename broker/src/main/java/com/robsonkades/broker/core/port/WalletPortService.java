package com.robsonkades.broker.core.port;

import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.UpdateOrderCommand;

import java.util.List;

public interface WalletPortService {

    void save(UpdateOrderCommand updateOrderCommand);
    List<WalletInfo> read(String wallet, Symbol symbol);
}
