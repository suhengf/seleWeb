package com.auto.core.aop;

import com.auto.common.Result;
import com.auto.common.exception.BizException;
import com.auto.common.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(1)
@Component
public class ServerCacheAspect {

    @Pointcut("@annotation(com.auto.core.aop.ServerCatch)")
    public void pointcut(){}


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){

        try{
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        } catch (Throwable e) {

            //参数异常捕获
            if (e instanceof ParamException) {
                log.error("ParamException:", e);
                ParamException paramException = (ParamException) e;
                return Result.fail(paramException.getError().getErrorCode(), paramException.getError().getErrorMsg());
            }

            //自定义异常捕获
            if (e instanceof BizException) {
                log.error("BizException", e);
                BizException bizException = (BizException) e;
                return Result.fail(bizException.getError().getErrorCode(), bizException.getError().getErrorMsg());
            }

            log.error("系统异常:", e);

            return Result.fail("-200", "系统异常");
        }
    }
}
