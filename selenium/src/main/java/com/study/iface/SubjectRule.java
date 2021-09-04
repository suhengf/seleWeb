package com.study.iface;

import com.study.dto.RuleDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubjectRule extends AbstractRule{

    @Override
    public <T> boolean executeRule(T t) {

        log.info("科目校验不通过");
        return false;
    }

    @Override
    public Boolean convert(RuleDto dto) {

        return true;
    }
}
