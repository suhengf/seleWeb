package com.auto.biz;

import com.auto.biz.bo.CreateOrderContext;
import com.auto.biz.bo.CreateOrderRequest;
import com.auto.biz.bo.CreateOrderResponse;
import com.auto.biz.pipe.*;
import com.auto.common.Result;
import com.auto.common.exception.ParamException;
import com.auto.core.engine.AbstractEngineExecutor;
import com.auto.core.engine.EngineExecutor;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class CreateOrderEngineExecutor
        extends AbstractEngineExecutor<CreateOrderRequest, CreateOrderContext, CreateOrderResponse>
        implements EngineExecutor<CreateOrderRequest, CreateOrderContext, CreateOrderResponse> {

    /**
     * 公共管道
     */

    @Autowired
    protected CartPipe cartPipe;

    @Autowired
    protected CartByGoodsInfoPipe cartByGoodsInfoPipe;

    @Autowired
    protected AddressPipe addressPipe;

    @Autowired
    protected DepotPipe depotPipe;

    @Autowired
    protected FreightPipe freightPipe;

    @Autowired
    protected InsertOrderPipe insertOrderPipe;

    @Override
    public void validCommonParameter(CreateOrderRequest request) {
        if (request.getUserId() == null) {
            throw new ParamException("userId不能为空");
        }

        if (request.getOrderType() == null) {
            throw new ParamException("orderType不能为空");
        }

        if (request.getAddressId() == null) {
            throw new ParamException("addressId不能为空");
        }
    }

    @Override
    public Result<CreateOrderResponse> execute(CreateOrderRequest request, CreateOrderContext context, CreateOrderResponse response) {

        doPipeline(request, context);

        response.setOrderId(context.getOrderId());

        return Result.success(response);

    }

}
