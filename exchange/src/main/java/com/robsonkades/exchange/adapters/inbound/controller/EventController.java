package com.robsonkades.exchange.adapters.inbound.controller;

import com.robsonkades.exchange.core.event.OrderEvent;
import com.robsonkades.exchange.core.port.ListEventsPortService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EventController {

    private final ListEventsPortService listEventsPortService;

    public EventController(final ListEventsPortService listEventsPortService) {
        this.listEventsPortService = listEventsPortService;
    }

    @GetMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderEvent.Event>> index() {
        return ResponseEntity.ok(listEventsPortService.execute());
    }
}
