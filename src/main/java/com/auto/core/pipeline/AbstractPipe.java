package com.auto.core.pipeline;

import com.alibaba.fastjson.JSON;
import com.auto.core.pipeline.rollback.RollBack;
import com.auto.core.pipeline.success.Success;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractPipe<T, S> implements Pipe<T, S> {

    @Override
    public void invoke(InvocationChain<T, S> invocationChain) {

        T parameter = invocationChain.getParameter();
        S context = invocationChain.getContext();

        if (isFilter(parameter, context)) {
            log.info("{} ---> filter pipe : {}",
                    invocationChain.getKey(), this.getClass().getSimpleName());

        } else {

            rollbackAdd(invocationChain);

            bizHandler(parameter, context);

            successAdd(invocationChain);

            log.info("{} ---> invoke pipe : {} success...context = {}",
                    invocationChain.getKey(), this.getClass().getSimpleName(), JSON.toJSONString(context));

        }
//如果改成遍历的话 这里也可以改一下
        invocationChain.invokeNext();

    }

    /**
     * 在管道公用的情况下，根据入参和上下文判断是否过滤这个管道
     * 参考CartPipe和CartByGoodsInfoPipe
     */
    protected abstract boolean isFilter(T t, S s);

    /**
     * 执行业务
     */
    protected abstract void bizHandler(T t, S s);

    private void rollbackAdd(InvocationChain<T, S> invocationChain) {
        if (invocationChain.needRollBack()) {
            List<RollBack<T, S>> rollBackList = invocationChain.getRollBackList();
            if (this instanceof RollBack && rollBackList != null) {
                rollBackList.add((RollBack) this);
            }
        }
    }

    private void successAdd(InvocationChain<T, S> invocationChain) {
        if (invocationChain.needSuccess()) {
            List<Success<T, S>> successList = invocationChain.getSuccessList();
            if (this instanceof Success && successList != null) {
                successList.add((Success<T, S>) this);
            }
        }
    }

}
