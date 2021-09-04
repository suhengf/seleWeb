package com.auto.exception;

/**
 * 系统内部异常
 *
 * @author little
 */
public class BusinessException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public BusinessException(String message) {
        super(message);
    }
}
