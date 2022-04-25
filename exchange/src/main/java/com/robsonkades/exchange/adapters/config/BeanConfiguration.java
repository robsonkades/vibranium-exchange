package com.robsonkades.exchange.adapters.config;

import com.robsonkades.exchange.ExchangeApplication;
import com.robsonkades.exchange.adapters.inbound.service.SaveTransaction;
import com.robsonkades.exchange.adapters.outbound.event.OrderBookListenerImpl;
import com.robsonkades.exchange.adapters.outbound.producer.ExchangeCommandPublisher;
import com.robsonkades.exchange.core.domain.OrderBook;
import com.robsonkades.exchange.core.port.CreateOrderPortService;
import com.robsonkades.exchange.core.port.ListEventsPortService;
import com.robsonkades.exchange.core.port.ListOrdersPortService;
import com.robsonkades.exchange.core.port.OrderBookPortListener;
import com.robsonkades.exchange.core.service.CreateOrderPortServiceImpl;
import com.robsonkades.exchange.core.service.ListEventsPortServiceImpl;
import com.robsonkades.exchange.core.service.ListOrdersPortServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ExchangeApplication.class)
public class BeanConfiguration {

    @Bean
    public OrderBookPortListener newOrderBookListener(
            final SaveTransaction saveTransaction,
            final ExchangeCommandPublisher exchangeCommandPublisher) {
        return new OrderBookListenerImpl(saveTransaction, exchangeCommandPublisher);
    }

    @Bean
    public OrderBook newOrder(final SaveTransaction saveTransaction, final ExchangeCommandPublisher exchangeCommandPublisher) {
        return new OrderBook(newOrderBookListener(saveTransaction, exchangeCommandPublisher));
    }

    @Bean
    public CreateOrderPortService createOrderServiceImpl(final OrderBook orderBook) {
        return new CreateOrderPortServiceImpl(orderBook);
    }

    @Bean
    public ListEventsPortService listEventsServiceImpl(final OrderBookPortListener orderBookPortListener) {
        return new ListEventsPortServiceImpl(orderBookPortListener);
    }

    @Bean
    public ListOrdersPortService listOrdersServiceImpl(final OrderBook orderBook) {
        return new ListOrdersPortServiceImpl(orderBook);
    }
}
