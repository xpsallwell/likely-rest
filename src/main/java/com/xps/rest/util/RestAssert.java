package com.xps.rest.util;

import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Created by xiongps on 2018/11/15.
 */
public class RestAssert {


    public static void hasText(String text,String message) {
        if(!StringUtils.hasText(text)) {
            throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,message);
        }
    }

    public static void notEmpty(String text,String message) {
        if(!StringUtils.isEmpty(text)) {
            throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,message);
        }
    }

    public static void notNull(Object text,String message) {
        if(text == null) {
            throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,message);
        }
    }
}
