package com.auto.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("instantiationTracingBeanPostProcessor")
public class PropUtil implements BeanPostProcessor {

    public static Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("dataSource".equals(beanName)){
//            DataSource ds = (DataSource)(bean);
//            try (Connection conn  = ds.getConnection();
//                 PreparedStatement pm = conn.prepareStatement("select * from user order by id");){
//                ResultSet rs = pm.executeQuery();
//                while(rs.next()){
//                    log.info("id: {} userName : {}",rs.getString("id"),rs.getString("userName"));
////                   map.put("userName",rs.getString("userName"));
//                }
//            } catch (SQLException e) {
//               log.error("e",e);
//            }
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
