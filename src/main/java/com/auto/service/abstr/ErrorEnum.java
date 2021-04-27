package com.auto.service.abstr;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    OPEN_UN("000001","开放大学处理失败"),
    ECNUSOLE_NU("000002","华东师范大学处理失败"),
    MARITIME_NU("000003","海事大学处理失败");

    private String code;
    private String msg;

    ErrorEnum(String code, String msg){
        this.code=code;
        this.msg=msg;
    }


    public static ErrorEnum getErrorEnum(String code){
        if(code==null){
            return null;
        }
        for (ErrorEnum type: values()) {
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
