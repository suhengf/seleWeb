package com.auto.common.exception;

public class BizException extends RuntimeException {

    private BizError error;

    public BizException(String msg) {
        super(msg);
        BizError bizError = new BizError("400", msg);
        this.error = bizError;
    }

    public BizException(BizError error) {
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
