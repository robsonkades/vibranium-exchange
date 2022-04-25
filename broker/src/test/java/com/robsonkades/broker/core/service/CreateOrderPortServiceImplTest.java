package com.robsonkades.broker.core.service;

import com.robsonkades.broker.core.domain.OrderCommand;
import com.robsonkades.broker.core.domain.OrderType;
import com.robsonkades.broker.core.domain.Symbol;
import com.robsonkades.broker.core.port.CreateOrderPortService;
import com.robsonkades.broker.core.port.ProcessOrderPromisePortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CreateOrderPortServiceImplTest {

    @Mock
    private ProcessOrderPromisePortService processOrderPromisePortService;
    @Captor
    private ArgumentCaptor<OrderCommand> orderCommandCaptor;
    private CreateOrderPortService createOrderPortService;


    @BeforeEach
    public void before() {
        this.createOrderPortService = new CreateOrderPortServiceImpl(processOrderPromisePortService);
    }

    @Test
    public void itShouldBeAbleToCreateOrder() {
        // Assemble
        Mockito.doNothing().when(processOrderPromisePortService).execute(orderCommandCaptor.capture());

        // Actions
        var orderCommand = new OrderCommand();
        orderCommand.setId("mock_id");
        orderCommand.setAmount(10);
        orderCommand.setPrice(100);
        orderCommand.setWallet("john-doe");
        orderCommand.setSymbol(Symbol.VBN);
        orderCommand.setType(OrderType.BUY);

        String orderId = this.createOrderPortService.execute(orderCommand);

        // Assertions
        Assertions.assertEquals(orderCommand.getId(), orderId);
        Assertions.assertEquals(orderCommand, orderCommandCaptor.getValue());
        Mockito.verify(processOrderPromisePortService, Mockito.times(1)).execute(orderCommand);
    }
}
