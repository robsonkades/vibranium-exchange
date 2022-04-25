package com.robsonkades.broker.core.port;

import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;

public interface GetWalletPortService {

    WalletInfo execute(String wallet, Symbol symbol);
}
