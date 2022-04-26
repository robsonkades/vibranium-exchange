package com.robsonkades.broker.adapters.inbound.controller;

import com.robsonkades.broker.adapters.dto.CreateWalletInputDto;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.domain.WalletInfo;
import com.robsonkades.broker.core.port.CreateWalletPortService;
import com.robsonkades.broker.core.port.GetWalletPortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WalletController {

    private GetWalletPortService getWalletPortService;
    private CreateWalletPortService createWalletPortService;
    public WalletController(final GetWalletPortService getWalletPortService, final CreateWalletPortService createWalletPortService) {
        this.getWalletPortService = getWalletPortService;
        this.createWalletPortService = createWalletPortService;
    }

    @PostMapping(value = "/wallets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody CreateWalletInputDto inputDto) {
        return ResponseEntity.ok(createWalletPortService.execute(new WalletInfo(inputDto.getWallet(), Symbol.VBN, inputDto.getAmount(), inputDto.getPrice())));
    }

    @GetMapping(value = "/wallets/{wallet}/{symbol}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletInfo> read(@PathVariable String wallet, @PathVariable Symbol symbol) {
        return ResponseEntity.ok(getWalletPortService.execute(wallet, symbol));
    }
}
