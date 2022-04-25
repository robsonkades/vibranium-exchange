package com.robsonkades.exchange.adapters.inbound.controller;

import com.robsonkades.exchange.adapters.dto.CreateOrderInputDto;
import com.robsonkades.exchange.adapters.dto.OrderResponseDto;
import com.robsonkades.exchange.core.port.CreateOrderPortService;
import com.robsonkades.exchange.core.port.ListOrdersPortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final CreateOrderPortService orderBookService;
    private final ListOrdersPortService listOrdersPortService;

    public OrderController(final CreateOrderPortService orderBookService, final ListOrdersPortService listOrdersPortService) {
        this.orderBookService = orderBookService;
        this.listOrdersPortService = listOrdersPortService;
    }

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> create(@Valid @RequestBody CreateOrderInputDto payload) {
        return ResponseEntity.ok(orderBookService.execute(payload));
    }

    @GetMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponseDto>> index() {
        var orders = listOrdersPortService.execute().values()
                .stream()
                .map(item -> new OrderResponseDto(item.getSide(), item.getRemainingQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }
}
