package com.tmac.exception;

public class ValidateException extends RuntimeException  {
    private String msg;
    private String code;

    public ValidateException(final String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
