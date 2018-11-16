package com.xps.rest.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.xps.rest.ws.handler.ProtocolContent;
import com.xps.rest.ws.handler.ProtocolType;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by xiongps on 2018/11/15.
 */
public class ProtocolUtil {

    private static final byte JSON = 1;
    private static final byte XML = 2;


    public static ProtocolContent getReturnContent(ProtocolContent srcContent,
                                                   Map map){

        if(XML == getType(srcContent.getProtocolType())) {
            Document srcDoc  = srcContent.getDocument();
            Document returnDocument = DocumentHelper.createDocument();
            Element root = returnDocument.addElement("response");// 创建root节点

            Element head = XmlUtils.getElementByName(srcDoc.getRootElement(),"head");
            head.addElement("respTime").setText(String.valueOf(System.currentTimeMillis()));
            addHead(root,head);

            Element body = root.addElement("body");
            XmlUtils.addStringToElement("success","true",body);
            Iterator<String> keys = map.keySet().iterator();
            while(keys.hasNext()) {
                String key = keys.next();
                String value = ((null == map.get(key) || "null" == map.get(key)) ?"":String.valueOf(map.get(key)));
                body.addElement(key).setText(value);
            }
            srcContent.setDocument(returnDocument);
            return srcContent;
        }
        JSONObject srcJson = srcContent.getJson();
        JSONObject headJson = srcJson.getJSONObject("head");
        JSONObject json = new JSONObject();
        headJson.put("respTime",System.currentTimeMillis());
        headJson.put("sign", DigestUtils.md5Hex(new JSONObject(map).toJSONString()));
        json.put("head",headJson);
        map.put("success",true);
        json.put("body",map);
        srcContent.setJson(json);
        return srcContent;
    }

    public static String getExceptionRet(ProtocolContent protocolContent,String code,String msg){
        if(protocolContent!=null && XML == getType(protocolContent.getProtocolType())) {
            Document returnDocument = DocumentHelper.createDocument();
            Element root = returnDocument.addElement("response");// 创建root节点
            XmlUtils.addStringToElement("success","false",root);
            XmlUtils.addStringToElement("code",code,root);
            XmlUtils.addStringToElement("msg",msg,root);
            return XmlUtils.format(returnDocument);
        }
        JSONObject ret = new JSONObject();
        ret.put("success",false);
        ret.put("code",code);
        ret.put("msg",msg);
        return ret.toJSONString();
    }

    private static byte getType( ProtocolType type) {
        if(type == ProtocolType.XML || type == ProtocolType.XJ) {
            return XML;
        }
        return JSON;
    }

    private static void addHead(Element root,Element head){
        Iterator<Element> it = head.elementIterator();
        Element newHead = root.addElement("head");
        while (it.hasNext()) {
            Element el = it.next();
            newHead.addElement(el.getName()).setText(el.getText());
        }
    }

}
