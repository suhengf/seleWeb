package com.auto.task;

import com.auto.chain.CreditApplyMqChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将节点信息入库 通过数据库
 * 动态配置节点信息
 */
@Component
public class EventReflector {

    //注入的map
    @Autowired
    @Qualifier("creditApplyMqHandlerList")
    @Lazy
    private List<CreditApplyMqChain>creditApplyMqHandlerList;

    @Autowired
    @Qualifier("creditApplyMqHandlerMap")
    @Lazy
    private Map<String,CreditApplyMqChain> creditApplyMqHandlerMap;


    //注入的list

    //根据数据库查询出来的list 得到反射的类 去调方法   map.put("antiFraudHandler",antiFraudHandler);
    //        map.put("orcHandler",orcHandler);
    //        map.put("riskHandler",riskHandler);

    public void handler(){
        List<String>list = new ArrayList<>();
        list.add("antiFraudHandler");
        list.add("orcHandler");
        list.add("riskHandler");
        int count = 0;
        for (int i=0;i<list.size();i++) {

            if(list.get(i).equals("orcHandler")){
                count =  i+ 1;
            }


        }


        for (int i=count;i<list.size();i++) {

            creditApplyMqHandlerMap.get(list.get(i)).process(null);

        }

    }



}
