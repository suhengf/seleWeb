package com.auto.core;

import com.auto.core.engine.EngineExecutor;
import com.auto.core.engine.EngineName;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 引擎解析器。
 * <p>通过spring的特性策略模式初始化EngineExecutorMap</p>
 *
 * @author liuulingfeng
 * @since $Revision:1.0.0, $Date: 2021年1月27日 上午10:59:49 $
 */
@Component
public class EngineResolver implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<Integer, EngineExecutor> EngineExecutorMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Map<String, EngineExecutor> beanMap = applicationContext.getBeansOfType(EngineExecutor.class);
        for (EngineExecutor executor : beanMap.values()) {
            EngineName[] engineNames = executor.name();
            for (EngineName engineName : engineNames) {
                this.EngineExecutorMap.put(engineName.getCode(), executor);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public EngineExecutor getExecutor(Integer orderType) {
        return EngineExecutorMap.get(orderType);
    }

}
