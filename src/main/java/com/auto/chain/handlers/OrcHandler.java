package com.auto.chain.handlers;


import com.auto.chain.CreditApplyMqChain;
import com.auto.chain.Request;
import com.auto.chain.TradeContext;
import com.auto.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;


@Slf4j
@Component("orcHandler")
public class OrcHandler implements CreditApplyMqChain {




    @Retryable(value= BizException.class,maxAttempts = 3,backoff = @Backoff(delay = 2000L))
    @Override
    public TradeContext process(Chain<Request, TradeContext> chain) {
        log.info("重试");
        TradeContext response = chain.response();
        response.setCheckSuccess(false);
        response.setErrorCode("失败");
        response.setErrorMsg("000000");
        if(true){
            response.setErrorCode("你好");
            response.setErrorMsg("000000");
            throw new BizException("222222222");
        }
//        response.setHandUp(true);
        response.setNodeName("orcHandler");
        return chain.process(chain.request(),response);
    }

    @Override
//    @Recover
    public TradeContext recover(Exception e,Chain<Request, TradeContext> chain) {
        TradeContext response = chain.response();
        log.info(response.getErrorCode());
        response.setErrorMsg("19999999991991");
        response.setCheckSuccess(true);
        log.info("补偿机制");
        return chain.process(chain.request(),response);
    }


}
