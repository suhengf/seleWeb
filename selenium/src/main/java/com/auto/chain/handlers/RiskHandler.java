package com.auto.chain.handlers;

import com.auto.chain.CreditApplyMqChain;
import com.auto.chain.Request;
import com.auto.chain.TradeContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("riskHandler")
public class RiskHandler implements CreditApplyMqChain {
    @Override
    public TradeContext process(Chain<Request, TradeContext> chain) {
        TradeContext response = chain.response();
        log.info("不走该节点");
        response.setNodeName("riskHandler");
        return chain.process(chain.request(),response);
    }

    @Override
    public TradeContext recover(Exception e, Chain<Request, TradeContext> chain) {
        return null;
    }


}
