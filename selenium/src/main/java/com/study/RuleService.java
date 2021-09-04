package com.study;

import com.study.dto.RuleDto;
import com.study.iface.BaseRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleService {
    private Map<Integer, List<BaseRule>> hashMap = new HashMap<>();
    private static final int AND = 1;
    private static final int OR = 0;

    public static RuleService create() {
        return new RuleService();
    }


    public RuleService and(List<BaseRule> ruleList) {
        hashMap.put(AND, ruleList);
        return this;
    }

    public RuleService or(List<BaseRule> ruleList) {
        hashMap.put(OR, ruleList);
        return this;
    }

    public boolean execute(RuleDto dto) {
        for (Map.Entry<Integer, List<BaseRule>> item : hashMap.entrySet()) {
            List<BaseRule> ruleList = item.getValue();
            switch (item.getKey()) {
                case AND:
                    // 如果是 and 关系，同步执行
                    System.out.println("execute key = " + 1);
                    if (!and(dto, ruleList)) {
                        return false;
                    }
                    break;
                case OR:
                    // 如果是 or 关系，并行执行
                    System.out.println("execute key = " + 0);
                    if (!or(dto, ruleList)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    private boolean and(RuleDto dto, List<BaseRule> ruleList) {
        for (BaseRule rule : ruleList) {
            boolean execute = rule.execute(dto);
            if (!execute) {
                // and 关系匹配失败一次，返回 false
                return false;
            }
        }
        // and 关系全部匹配成功，返回 true
        return true;
    }

    private boolean or(RuleDto dto, List<BaseRule> ruleList) {
        for (BaseRule rule : ruleList) {
            boolean execute = rule.execute(dto);
            if (execute) {
                // or 关系匹配到一个就返回 true
                return true;
            }
        }
        // or 关系一个都匹配不到就返回 false
        return false;
    }

}
