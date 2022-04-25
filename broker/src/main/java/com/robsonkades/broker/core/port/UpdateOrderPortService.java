package com.robsonkades.broker.core.port;

import com.robsonkades.broker.core.domain.UpdateOrderCommand;

public interface UpdateOrderPortService {

    void execute(UpdateOrderCommand updateOrderCommand);
}
