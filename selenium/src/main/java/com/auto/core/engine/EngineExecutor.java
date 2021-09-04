package com.auto.core.engine;


import com.auto.common.Result;

public interface EngineExecutor<T, S, R> {

    EngineName[] name();

    Result<R> execute(T t, S s, R r);

}
