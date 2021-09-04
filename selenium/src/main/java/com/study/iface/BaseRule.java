package com.study.iface;

import com.study.dto.RuleDto;

public interface BaseRule {
    boolean execute(RuleDto dto);

}
