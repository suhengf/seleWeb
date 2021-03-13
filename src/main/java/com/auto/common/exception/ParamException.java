package com.auto.common.exception;

public class ParamException extends RuntimeException {

    private BizError error;

    public ParamException(String msg) {
        super(msg);
        BizError bizError = new BizError("300", msg);
        this.error = bizError;
    }

    public ParamException(BizError error) {
        super(error.getErrorMsg());
        this.error = error;
    }

    public BizError getError() {
        return error;
    }

    public void setError(BizError error) {
        this.error = error;
    }

}
