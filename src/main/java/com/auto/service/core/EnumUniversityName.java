package com.auto.service.core;

import lombok.Getter;

/**
 * 大学作业枚举类
 */
@Getter
public enum EnumUniversityName  {
    ECNUSOLE_UNIVERSITY("EcnusoleUniversity","华东师范大学"),
    OPEN_UNIVERSITY("OpenUniversity","上海开放大学"),
    MARITIME_UNIVERSITY("MaritimeUniversity","上海海事大学"),
    OPEN_UNIVERSITY_JOURNEY("MaritimeUniversityJourney","上海开放大学旅游学院"),
    SH_UNIVERSITY("ShUniversity","上海大学"),
    UNIVERSAL_EXAMINATION("universalExamination","统考成绩查询"),
    ;

    private String code;
    private String desc;


    EnumUniversityName(String code, String desc) {
        this.code=code;
        this.desc=desc;
    }

    public static EnumUniversityName getEnumUniversityName(String code){
        if(code==null){
            return null;
        }
        for (EnumUniversityName type: values()) {
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
