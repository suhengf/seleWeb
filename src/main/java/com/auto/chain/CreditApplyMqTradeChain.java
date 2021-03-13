package com.auto.chain;

import org.springframework.retry.annotation.Recover;

public interface CreditApplyMqTradeChain <T,R>{


    R process(Chain<T, R> chain);

    @Recover
    R recover(Exception e, Chain<T, R> chain);

    interface Chain<T,R>{
        T request();

        R response();

        R process(T request, R response);
    }

}
