package com.robsonkades.exchange.core.port;

import com.robsonkades.exchange.adapters.dto.CreateOrderInputDto;

public interface CreateOrderPortService {

    String execute(CreateOrderInputDto inputDto);
}
