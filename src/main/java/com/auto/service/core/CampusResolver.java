package com.auto.service.core;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 引擎解析器。
 * <p>通过spring的特性策略模式初始化CampusExecutorMap</p>
 *
 * @author liuulingfeng
 * @since $Revision:1.0.0, $Date: 2021年1月27日 上午10:59:49 $
 */
@Component
public class CampusResolver implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, CampusOnlineHandler> CampusExecutorMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        Map<String, CampusOnlineHandler> beanMap = applicationContext.getBeansOfType(CampusOnlineHandler.class);
        for (CampusOnlineHandler executor : beanMap.values()) {
            EnumUniversityName universityName = executor.universityName();
            this.CampusExecutorMap.put(universityName.getCode(), executor);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public CampusOnlineHandler getExecutor(String universityName) {
        return CampusExecutorMap.get(universityName);
    }

}
