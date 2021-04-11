package com.auto.service.business.core;

import lombok.Getter;

/**
 * 大学枚举类
 */
@Getter
public enum EnumUniversityName  {

    OPEN_UNIVERSITY("OpenUniversity","上海开放大学"),
    MARITIME_UNIVERSITY("MaritimeUniversity","上海海事大学"),;

    private String code;
    private String desc;


    EnumUniversityName(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

}
