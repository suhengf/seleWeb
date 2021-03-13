package com.auto.core.pipeline;

import com.auto.core.pipeline.rollback.RollBack;
import com.auto.core.pipeline.success.Success;


import java.util.List;

public interface InvocationChain<T, S> {

    void invoke();

    void invokeNext();

    T getParameter();

    S getContext();

    //这条管道流执行的唯一标识 方便日志查找
    String getKey();

    void setKey(String key);

    List<RollBack<T, S>> getRollBackList();

    List<Success<T, S>> getSuccessList();

    boolean needSuccess();

    boolean needRollBack();

}
