package com.study.iface;

import com.study.dto.NameDto;
import com.study.dto.RuleDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NameRule extends AbstractRule{
    @Override
    public <T> boolean executeRule(T t) {

        log.info("姓名校验通过");
        return true;
    }

    @Override
    public <T> T  convert(RuleDto dto) {
        NameDto nameDto = new NameDto();
        return (T) nameDto;
    }

}
