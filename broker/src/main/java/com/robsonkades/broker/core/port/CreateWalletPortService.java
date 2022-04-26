package com.robsonkades.broker.core.port;

import com.robsonkades.broker.core.domain.WalletInfo;

public interface CreateWalletPortService {

    String execute(WalletInfo walletInfo);
}
