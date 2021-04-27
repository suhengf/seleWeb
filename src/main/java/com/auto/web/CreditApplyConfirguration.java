package com.auto.web;

import com.auto.chain.CreditApplyMqChain;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CreditApplyConfirguration {

    @Autowired
    @Qualifier("antiFraudHandler")
    private CreditApplyMqChain antiFraudHandler;

    @Autowired
    @Qualifier("orcHandler")
    private CreditApplyMqChain orcHandler;

    @Autowired
    @Qualifier("riskHandler")
    private CreditApplyMqChain riskHandler;

    @Bean(name="creditApplyMqHandlerList")
    public List<CreditApplyMqChain> creditApplyMqChainList(){
        return Lists.newArrayList(antiFraudHandler,orcHandler,riskHandler);
    }


    @Bean(name="creditApplyMqHandlerMap")
    public Map<String,CreditApplyMqChain> creditApplyMqHandlerMap(){
        ConcurrentHashMap<String,CreditApplyMqChain> map = new ConcurrentHashMap<>();
        map.put("antiFraudHandler",antiFraudHandler);
        map.put("orcHandler",orcHandler);
        map.put("riskHandler",riskHandler);
        return map;

    }




}
