package com.robsonkades.exchange.adapters.inbound.controller;

import com.robsonkades.exchange.adapters.dto.CreateOrderInputDto;
import com.robsonkades.exchange.adapters.dto.OrderResponseDto;
import com.robsonkades.exchange.core.domain.Order;
import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.port.CreateOrderPortService;
import com.robsonkades.exchange.core.port.ListOrdersPortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private OrderController orderController;

    @Mock
    private CreateOrderPortService createOrderPortService;
    @Mock
    private ListOrdersPortService listOrdersPortService;

    @BeforeEach
    public void before() {
        this.orderController = new OrderController(createOrderPortService, listOrdersPortService);
    }

    @Test
    public void itShouldBeAbleToCreateOrder() {
        // Assemble
        var payload = new CreateOrderInputDto();
        payload.setWallet("wallet");
        payload.setAmount(10);
        payload.setPrice(100);
        payload.setType(Side.BUY);

        Mockito
                .when(createOrderPortService.execute(payload))
                .thenReturn("order-id");

        // Actions
        ResponseEntity<String> responseEntity = orderController.create(payload);

        // Assertions
        Assertions.assertEquals("order-id", responseEntity.getBody());
        Mockito.verify(createOrderPortService, Mockito.times(1)).execute(payload);
    }

    @Test
    public void itShouldBeAbleToListOrders() {
        // Assemble
        Map<String, Order> orders = new Hashtable<>();
        orders.put("id-1", new Order("wallet-1", 1, "id-1", Side.BUY, 100, 10));
        orders.put("id-2", new Order("wallet-1", 2, "id-2", Side.SELL, 100, 10));
        orders.put("id-3", new Order("wallet-1", 3, "id-3", Side.BUY, 100, 10));

        Mockito.when(listOrdersPortService.execute()).thenReturn(orders);

        // Actions
        ResponseEntity<List<OrderResponseDto>> listResponseEntity = orderController.index();

        // Assertions
        var expected = List.of(
                new OrderResponseDto(Side.BUY, 10, 100),
                new OrderResponseDto(Side.SELL, 10, 100),
                new OrderResponseDto(Side.BUY, 10, 100));

        Mockito.verify(listOrdersPortService, Mockito.times(1)).execute();

        org.assertj.core.api.Assertions.assertThat(listResponseEntity.getBody())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expected);
    }
}
