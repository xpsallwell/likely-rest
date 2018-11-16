package com.xps.rest.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.util.RestAssert;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by xiongps on 2018/11/15.
 */
public class ProtocolContent {

    private ProtocolType protocolType;

    private String methodName;

    private JSONObject json;

    private Document document;

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void parseMethod(String method) {
        RestAssert.hasText(method,"请求的方法不能为空");
        if(method.contains(".")) {
            String ms[] = method.split("\\.");
            this.setMethodName(ms[0]);
            this.parseProtocolType(ms[1]);
        } else {
            this.setMethodName(method);
            this.setProtocolType(ProtocolType.JSON);
        }
    }

    private void parseProtocolType(String pr){
        RestAssert.hasText(pr,"协议名不能为空");
        pr = pr.toUpperCase();
        try {
            this.setProtocolType(ProtocolType.valueOf(pr));
        } catch (Exception e) {
            throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"不支持的协议");
        }
    }

    public void parseContent(String content) {
        switch (getProtocolType()){
            case JSON:
                try {
                    //urldecode解码
                    content = URLDecoder.decode(content,"UTF-8");
                    this.setJson(JSONObject.parseObject(content));
                } catch (Exception e) {
                    throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"协议格式不正确");
                }
                break;
            case XML:
            case XJ:
                try {
                    //urldecode解码
                    content = URLDecoder.decode(content,"UTF-8");
                    this.setDocument(DocumentHelper.parseText(content));
                } catch (DocumentException e) {
                    throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"协议格式不正确");
                } catch (UnsupportedEncodingException e) {
                    throw new BusinessException(DefaultExceptionComponent.NOT_CAST,"协议格式不正确");
                }
                break;
            default:
                throw new BusinessException(DefaultExceptionComponent.NOT_SUPPORT,"不支持的协议类型");
        }
    }
}
