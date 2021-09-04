package com.auto.common;

public class Result<T> {

    private String code;

    private String msg;

    private T data;

    public Result() {

    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success() {
        return new Result("200", "成功");
    }

    public static Result success(Object data) {
        return new Result("200", "成功", data);
    }

    public static Result fail(String code, String msg) {
        return new Result(code, msg);
    }

    public static Result fail(String msg) {
        return new Result("-1", msg);
    }

}
