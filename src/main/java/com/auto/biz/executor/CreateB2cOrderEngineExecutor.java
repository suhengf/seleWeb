package com.auto.biz.executor;


import com.auto.biz.CreateOrderEngineExecutor;
import com.auto.biz.bo.CreateOrderContext;
import com.auto.biz.bo.CreateOrderRequest;
import com.auto.biz.pipe.b2c.BuildB2cOrderPipe;
import com.auto.common.exception.ParamException;
import com.auto.core.engine.EngineName;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateB2cOrderEngineExecutor extends CreateOrderEngineExecutor {

    @Autowired
    private BuildB2cOrderPipe buildB2cOrderPipe;

    @Override
    protected void validCustomParameter(CreateOrderRequest request) {
        if (request.getCartId() == null && CollectionUtils.isEmpty(request.getSkuInfoList())) {
            throw new ParamException("购物信息不能为空");
        }
    }

    @Override
    protected String setKey(CreateOrderRequest createOrderRequest, CreateOrderContext createOrderContext) {
        return "createB2cOrder_" + createOrderRequest.getUserId();
    }

    @Override
    public void afterPropertiesSet() {
        pipeline.addPipe(cartPipe);
        pipeline.addPipe(cartByGoodsInfoPipe);
        pipeline.addPipe(addressPipe);
        pipeline.addPipe(depotPipe);
        pipeline.addPipe(freightPipe);
        pipeline.addPipe(buildB2cOrderPipe);
        pipeline.addPipe(insertOrderPipe);
    }

    @Override
    public EngineName[] name() {
        return new EngineName[]{EngineName.CREATE_B2C_ORDER, EngineName.CREATE_B2C_OWN_ORDER};
    }

}
