package com.auto.biz.pipe;

import com.auto.biz.bo.CreateOrderContext;
import com.auto.biz.bo.CreateOrderRequest;
import com.auto.core.pipeline.AbstractPipe;
import com.auto.core.pipeline.InvocationChain;
import com.auto.core.pipeline.rollback.RollBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DepotPipe extends AbstractPipe<CreateOrderRequest, CreateOrderContext> implements RollBack<CreateOrderRequest, CreateOrderContext> {

    @Override
    protected boolean isFilter(CreateOrderRequest createOrderRequest, CreateOrderContext createOrderContext) {
        return false;
    }

    @Override
    protected void bizHandler(CreateOrderRequest createOrderRequest, CreateOrderContext createOrderContext) {

    }

    @Override
    public void rollBack(InvocationChain<CreateOrderRequest, CreateOrderContext> invocationChain) {
        log.info("回滚库存");
    }

}
