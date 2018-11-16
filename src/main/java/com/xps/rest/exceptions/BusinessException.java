package com.xps.rest.exceptions;

import java.text.MessageFormat;

/**
 * Created by xiongps on 2018/11/13.
 */
public class BusinessException  extends RuntimeException {
    private static final long serialVersionUID = 198567894L;
    private String code;
    private String message;
    private IAcctException enumException;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(IAcctException enumException) {
        this.enumException = enumException;
    }

    public BusinessException(IAcctException enumException, Object... arguments) {
        this.enumException = enumException;
        this.message = MessageFormat.format(this.enumException.getMessage(), arguments);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, new Throwable(message));
        this.message = message;
    }

    public IAcctException getEnumException() {
        return this.enumException;
    }

    public void setEnumException(IAcctException enumException) {
        this.enumException = enumException;
    }
}