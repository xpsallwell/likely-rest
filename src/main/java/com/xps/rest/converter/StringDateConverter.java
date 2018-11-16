package com.xps.rest.converter;

import com.xps.rest.exceptions.BusinessException;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;


/**
 * 〈一句话功能简述〉<br> Converter
 * 〈功能详细描述〉实现stringTodate
 */
public class StringDateConverter extends DateConverterBase implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source == null){
            return null;
        }
        String trim = source.trim();
        if (trim.length() == 0){
            return null;
        }
        try {
//            return source.contains(":") ? getDateTimeFormat().parse(trim) : getDateFormat().parse(trim);
            if(source.length() == 14){
                return getDateTimeFormat().parse(trim);
            }else if(source.length() == 8){
                getDateFormat().parse(trim);
            }else if(source.length() == 6){
                getMonthFormat().parse(trim);
            }
            return source.length() == 14? getDateTimeFormat().parse(trim) : getDateFormat().parse(trim);
        } catch (ParseException e) {
            throw new BusinessException("无效的日期格式：" + trim);
        }
    }

}
