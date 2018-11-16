package com.xps.rest.exceptions;

/**
 * Created by xiongps on 2018/11/13.
 */
public interface IAcctException {

    String getCode();
    String getMessage();
    String getDesc();

    IAcctException.ExceptionLevel getLevel();



    enum ExceptionLevel {
        INFO,WARN,ERROR,FAIL;
    }
}
