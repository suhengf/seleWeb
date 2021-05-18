package com.auto.service.abstr;


import com.auto.service.core.EnumUniversityName;
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
 * 工厂类  spring 容器初始化的时候
 */
@Component
public class UniversityResolver implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<String, University> universityHashMap = new HashMap<>();

    /**
     * spring 容器加载 去把 实现University 的类 把这五个类  存到一个Map<String, University> beanMap
     * 循环去遍历 拿到具体的五个实现实现University接口的五个类 最后把这些存到一个map容器 根据的自己key
     */
    @Override
    public void afterPropertiesSet() {
        Map<String, University> beanMap = applicationContext.getBeansOfType(University.class);
        for (University executor : beanMap.values()) {
            EnumUniversityName universityName = executor.getUniversityName();
            this.universityHashMap.put(universityName.getCode(), executor);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public University getExecutor(String universityName) {
        return universityHashMap.get(universityName);
    }

}
