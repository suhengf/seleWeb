package com.auto.biz.pipe;

import com.auto.biz.bo.CreateOrderContext;
import com.auto.biz.bo.CreateOrderRequest;
import com.auto.core.pipeline.AbstractPipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InsertOrderPipe extends AbstractPipe<CreateOrderRequest, CreateOrderContext> {

    @Override
    protected boolean isFilter(CreateOrderRequest request, CreateOrderContext context) {
        return false;
    }

    @Override
    protected void bizHandler(CreateOrderRequest request, CreateOrderContext context) {
        context.setOrderId(1L);
    }

}
