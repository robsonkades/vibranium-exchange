package com.robsonkades.broker.adapters.inbound.controller;

import com.robsonkades.broker.adapters.dto.CreateOrderInputDto;
import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.port.CreateOrderPortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private CreateOrderPortService createOrderPortService;

    public OrderController(final CreateOrderPortService createOrderPortService) {
        this.createOrderPortService = createOrderPortService;
    }

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody CreateOrderInputDto payload) {

        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setId(UUID.randomUUID().toString());
        orderCommand.setAmount(payload.getAmount());
        orderCommand.setPrice(payload.getPrice());
        orderCommand.setSymbol(payload.getSymbol());
        orderCommand.setType(payload.getType());
        orderCommand.setWallet(payload.getWallet());

        return ResponseEntity.ok(createOrderPortService.execute(orderCommand));
    }
}
