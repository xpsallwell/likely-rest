package com.xps.rest.util;

import com.alibaba.fastjson.JSONObject;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiongps on 2017/7/6.
 */
public class JsonUtil {

    public static JSONObject parseDocBodyToJson(Document doc) throws Exception {
        String body =  XmlUtils.getNodeText(doc, "//uipRequest/uipBody", true, true);
        return JSONObject.parseObject(body);
    }

    public static <T> T  parseDocBodyToBean(Document doc,Class<T> cls) throws Exception {
        String body =  XmlUtils.getNodeText(doc, "//uipRequest/uipBody", true, true);
        return JSONObject.parseObject(body,cls);
    }

    public static String getAttrText(JSONObject jsonObject,
                                     String keyName,
                                     boolean isMustExist) throws BusinessException{
        if(!jsonObject.containsKey(keyName) && isMustExist) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不存在");
        }
        String str = jsonObject.getString(keyName);
        if(isMustExist && StringUtils.isEmpty(str)) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不能为空");
        }
        return str;
    }

    public static Long  getAttrTextLong(JSONObject jsonObject,String keyName,boolean isMustExist){
        if(!jsonObject.containsKey(keyName) && isMustExist) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不存在");
        }
        try{
            Long lg = jsonObject.getLong(keyName);
            if(isMustExist && null == lg) {
                throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不能为空");
            }
            return lg;
        } catch (Exception e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"节点["+keyName+"]的值不是数字类型");
        }
    }
    public static Integer  getAttrTextInteger(JSONObject jsonObject,String keyName,boolean isMustExist){
        if(!jsonObject.containsKey(keyName) && isMustExist) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不存在");
        }
        try{
            Integer lg = jsonObject.getInteger(keyName);
            if(isMustExist && null == lg) {
                throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不能为空");
            }
            return lg;
        } catch (Exception e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"节点["+keyName+"]的值不是数字类型");
        }
    }
    public static Double  getAttrTextDouble(JSONObject jsonObject,String keyName,boolean isMustExist){
        if(!jsonObject.containsKey(keyName) && isMustExist) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不存在");
        }
        try{
            Double lg = jsonObject.getDouble(keyName);
            if(isMustExist && null == lg) {
                throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不能为空");
            }
            return lg;
        } catch (Exception e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"节点["+keyName+"]的值不是数字类型");
        }
    }
    public static BigDecimal getAttrTextBigDecimal(JSONObject jsonObject, String keyName, boolean isMustExist){
        if(!jsonObject.containsKey(keyName) && isMustExist) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不存在");
        }
        try{
            BigDecimal lg = jsonObject.getBigDecimal(keyName);
            if(isMustExist && null == lg) {
                throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"节点["+keyName+"]的值不能为空");
            }
            return lg;
        } catch (Exception e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"节点["+keyName+"]的值不是数字类型");
        }
    }

    public static Date getAttrTextDate(JSONObject jsonObject, String keyName,String pattern, boolean isMustExist){
        String dt = getAttrText(jsonObject,keyName,isMustExist);
        if(dt == null){return null;}
        try {
            SimpleDateFormat slf = new SimpleDateFormat(pattern);
            return slf.parse(dt);
        } catch (ParseException e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"节点["+keyName+"]的值["
                    +dt+"]与日期格式["+pattern+"]不对应，不能转换成日期");
        }
    }
}
