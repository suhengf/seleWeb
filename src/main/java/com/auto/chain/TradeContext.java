package com.auto.chain;

import lombok.Data;

@Data
public class TradeContext {

    private boolean checkSuccess;

    private String errorMsg;

    private String errorCode;

    private boolean isHandUp;

    private String nodeName;

}
