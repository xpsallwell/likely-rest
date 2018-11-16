package com.xps.rest.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br> Converter
 * 〈功能详细描述〉实现dateTostring转换
 */
public class DateStringConverter extends DateConverterBase implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        if (source == null){
            return "";
        }
        return getDateTimeFormat().format(source);
    }

}
