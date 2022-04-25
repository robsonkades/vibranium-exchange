package com.robsonkades.exchange.adapters.inbound.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BrokerConsumerTest {

    private BrokerConsumer brokerConsumer;

    @BeforeEach
    public void before() {
        this.brokerConsumer = new BrokerConsumer();
    }

    @Test
    public void testCreateBrokerConsumer() {
        Assertions.assertNotNull(this.brokerConsumer);
    }
}
