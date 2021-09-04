package com.study.iface;

import com.study.dto.AddressDto;
import com.study.dto.AgeDto;
import com.study.dto.RuleDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgeRule extends AbstractRule{

    @Override
    public <T> boolean executeRule(T t) {

        log.info("年龄校验不通过");
        return false;
    }

    @Override
    public <T> T  convert(RuleDto dto) {
        AgeDto ageDto = new AgeDto();
        return (T) ageDto;
    }
}
