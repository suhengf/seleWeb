package com.study.iface;

import com.study.dto.AddressDto;
import com.study.dto.NationalityRuleDto;
import com.study.dto.RuleDto;

public class NationalityRule  extends AbstractRule{
    @Override
    public <T> T convert(RuleDto dto) {
        NationalityRuleDto nationalityRuleDto = new NationalityRuleDto();
        if (dto.getName().startsWith("张三")) {
            nationalityRuleDto.setNationality("1");
        }
        return (T) nationalityRuleDto;
    }



    @Override
    public  <T> boolean executeRule(T t) {
        System.out.println("NationalityRule invoke!");
        NationalityRuleDto nationalityRuleDto = (NationalityRuleDto) t;
        if (nationalityRuleDto.getNationality().startsWith("1")) {
            return true;
        }
        return false;
    }

}
