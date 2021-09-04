package com.study.iface;

import com.study.dto.AddressDto;
import com.study.dto.NationalityRuleDto;
import com.study.dto.RuleDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressRule extends AbstractRule {
//    @Override
//    public boolean execute(RuleDto dto) {
//        System.out.println("AddressRule invoke!");
//        if (dto.getAddress().startsWith("北京")) {
//            return true;
//        }
//        return false;
//    }

    @Override
    public <T> boolean executeRule(T t) {
        AddressDto addressDto = (AddressDto) t;
        String address = addressDto.getAddress();
        log.info("addree调用户信息不通过：",addressDto);
        return false;
    }

    @Override
    public <T> T  convert(RuleDto dto) {
        AddressDto addressDto = new AddressDto();
        return (T) addressDto;
    }

}
