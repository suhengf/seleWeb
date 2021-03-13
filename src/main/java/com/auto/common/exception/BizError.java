package com.auto.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizError {

    private String errorCode;

    private String errorMsg;

    public BizError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
