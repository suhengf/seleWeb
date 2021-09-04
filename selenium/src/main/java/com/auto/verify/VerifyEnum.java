package com.auto.verify;

public enum VerifyEnum {

    SUCCESS(1,"交易成功"),PROCESSING(2,"交易处理中"),FAIL(3,"交易失败");

    private int index;
    private String msg;

    VerifyEnum(int index,String msg){
        this.index=index;
        this.msg=msg;
    }


}
