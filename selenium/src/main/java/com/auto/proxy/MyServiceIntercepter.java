package com.auto.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
@Slf4j
public class MyServiceIntercepter implements MethodInterceptor {

    public static void main(String[] args) {

        //生成代理类目录
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D:\\coder");
        Enhancer enhancer =  new Enhancer();
        enhancer.setSuperclass(RealService.class);
        enhancer.setCallback(new MyServiceIntercepter());
        RealService realService = (RealService) enhancer.create();
        realService.realMethod();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("before execute");

       Object result = methodProxy.invokeSuper(o,objects);
        log.info("after execute");
        return result;
    }
}
