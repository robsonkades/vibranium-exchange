package com.robsonkades.broker.adapters.inbound.controller;

import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.GetWalletPortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WalletController {

    private GetWalletPortService getWalletPortService;
    public WalletController(final GetWalletPortService getWalletPortService) {
        this.getWalletPortService = getWalletPortService;
    }

    @GetMapping(value = "/wallets/{wallet}/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletInfo> create(@PathVariable String wallet, @PathVariable Symbol symbol) {
        return ResponseEntity.ok(getWalletPortService.execute(wallet, symbol));
    }
}
