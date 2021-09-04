package com.auto.chain;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CreditTradeMqChainHandler implements CreditApplyMqChain.Chain<Request,TradeContext>{
    private final int index;

    private final Request request;

    private TradeContext response;

    private List<CreditApplyMqChain> handlers;

    public CreditTradeMqChainHandler(List<CreditApplyMqChain> handlers,int index,Request request,TradeContext response) {
        this.handlers=handlers;
        this.index = index;
        this.request=request;
        this.response= response;
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public TradeContext response() {
        return response;
    }

    @Override
    public TradeContext process(Request request, TradeContext response) {
        if(index>=handlers.size()){
            return response;
        }
        CreditApplyMqTradeChain.Chain<Request,TradeContext> next = new CreditTradeMqChainHandler(handlers,index+1,request,response);
        CreditApplyMqChain handle = handlers.get(index);
        log.info("当前处理类：{}"+handle.getClass().getName(),index);
        if(response.isHandUp()){
            return response;
        }
        if(response.isCheckSuccess()){
            return response;
        }
        response =  handle.process(next);




        log.info("当前handle处理response{}", JSONObject.toJSON(response));

        return response;
    }


}
