package com.robsonkades.broker.adapters.config;

import com.robsonkades.broker.BrokerApplication;
import com.robsonkades.broker.adapters.outbound.persistence.OrderJpaRepository;
import com.robsonkades.broker.adapters.outbound.persistence.OrderPortRepositoryImpl;
import com.robsonkades.broker.adapters.outbound.persistence.WalletJpaRepository;
import com.robsonkades.broker.adapters.outbound.persistence.WalletPortRepositoryImpl;
import com.robsonkades.broker.core.port.*;
import com.robsonkades.broker.core.service.CreateOrderPortServiceImpl;
import com.robsonkades.broker.core.service.CreateWalletPortServiceImpl;
import com.robsonkades.broker.core.service.GetWalletPortServiceImpl;
import com.robsonkades.broker.core.service.UpdateOrderPortServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackageClasses = BrokerApplication.class)
public class BeanConfiguration {

    @Bean
    public OrderPortRepository orderPortRepository(final OrderJpaRepository repository) {
        return new OrderPortRepositoryImpl(repository);
    }

    @Bean
    public CreateOrderPortService createOrderPortService(final ProcessOrderPromisePortService processOrderPromisePortService) {
        return new CreateOrderPortServiceImpl(processOrderPromisePortService);
    }

    @Bean
    public WalletPortService updateWalletPortService(final WalletJpaRepository updateWalletPortService) {
        return new WalletPortRepositoryImpl(updateWalletPortService);
    }

    @Bean
    public UpdateOrderPortService updateOrderPortService(final WalletPortService walletPortService) {
        return new UpdateOrderPortServiceImpl(walletPortService);
    }

    @Bean
    public GetWalletPortService walletPortService(final WalletPortService walletPortService) {
        return new GetWalletPortServiceImpl(walletPortService);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CreateWalletPortService createWalletPortService(final WalletPortService walletPortService) {
        return new CreateWalletPortServiceImpl(walletPortService);
    }
}
