/* 
 * Project Name: mms-aop 
 * File Name: XmlUtils.java 
 * Package Name:com.yaoyaohao.tao.util
 * Date:2015-9-16上午8:55:01 
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/
package com.xps.rest.util;

import com.alibaba.fastjson.JSON;

import com.xps.rest.bean.ReqParamBean;
import com.xps.rest.bean.UipHead;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * XML处理工具类. 
 * <p>解析XML报文
 * </p> 
 * @author xiongps
 * @version 2015-9-18 下午4:51:00   
 * @since JDK1.7
 */
public class XmlUtils {

	/**
	 * 根据XML文本生成Document对象
	 * 
	 * @param xml
	 *            xml文本
	 * @return Document对象
	 * @throws DocumentException
	 */
	public static Document parseToDoc(String xml) throws DocumentException {
		return DocumentHelper.parseText(xml);
	}

	/**
	 * 根据Xml文件生成Document对象
	 * 
	 * @param file
	 *            xml文件路径
	 * @return Document对象
	 * @throws DocumentException
	 */
	public static Document parseToDoc(File file) throws DocumentException {
		SAXReader xmlReader = new SAXReader();
		return xmlReader.read(file);
	}

	/**
	 * 根据输入流生成Document对象
	 * 
	 * @param is
	 *            输入流
	 * @return Document对象
	 * @throws DocumentException
	 */
	public static Document parseToDoc(InputStream is) throws DocumentException {
		SAXReader xmlReader = new SAXReader();
		return xmlReader.read(is);
	}

	/**
	 * 根据Document得到根结点
	 * 
	 * @param doc
	 *            Document目录
	 * @return 根结点
	 */
	public static Element getRoot(Document doc) {
		return doc.getRootElement();
	}

	/**
	 * 根据元素名称返回一组Element
	 * 
	 * @param root
	 *            当前结点
	 * @param name
	 *            要返回的元素名称
	 * @return 一组Element
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElementsByName(Element root, String name) {
		return root.elements(name);
	}

	/**
	 * 
	 * 按路径查找某元素的集合. 
	 * @param doc 
	 * 			xml报文对象
	 * @param xpath
	 * 			xml路径,必须从根路径开始,eg: "<i>//root/reqBody/elelist</i>"
	 * @return 元素列表集合
	 *
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElementsByPath(Document doc, String xpath) {
		List<Node> nodeList = doc.selectNodes(xpath);
		List<Element> eleList = new ArrayList<Element>();
		if(nodeList == null ) {
			return eleList;
		}
		for (Node n : nodeList) {
			if (n instanceof Element) {
				eleList.add((Element) n);
			}
		}
		return eleList;
	}
	
	/**
	 * 
	 * 按路径查找单个元素. 
	 * @param doc 
	 * 			xml报文对象
	 * @param xpath
	 * 			xml路径,必须从根路径开始,eg: "<i>//root/reqBody/valueField</i>"
	 * @return 单个元素
	 *
	 */
	public static Element getSingleElementByPath(Document doc, String xpath) {
		 Node node  = doc.selectSingleNode(xpath);
		 if (node instanceof Element) {
			return (Element)node;
		 }
		return null;
	}

	/**
	 * 根据元素名称返回一个元素(如果有多个元素的话，只返回第一个)
	 * 
	 * @param root
	 *            当前结点
	 * @param name
	 *            要返回的元素名称
	 * @return 一个Element元素
	 */
	public static Element getElementByName(Element root, String name) {
		return root.element(name);
	}

	/**
	 * 根据当前元素,返回该元素的所有属性
	 * 
	 * @param root
	 *            当前结点
	 * @return 当前结点的所有属性
	 */
	@SuppressWarnings("unchecked")
	public static List<Attribute> getAttributes(Element root) {
		return root.attributes();
	}

	/**
	 * 根据属性名称,返回当前元素的某个属性
	 * 
	 * @param root
	 *            当前结点
	 * @return 当前结点的一个属性
	 */
	public static Attribute getAttributeByName(Element root, String name) {
		return root.attribute(name);
	}

	// ---------------------------

	/**
	 * 根据DOC取路径下节点的值，可以判断节点是否存在或节点值是否为空
	 * 
	 * @param doc
	 *            XML对象
	 * @param path
	 *            节点全路径
	 * @param isMustExist
	 *            节点是否必须存在，true说明节点必须存在否则报异常
	 * @return
	 * @throws BusinessException
	 */
	public static String getNodeText(Document doc, String path,
			boolean... isMustExist) throws BusinessException {
		Node node = doc.selectSingleNode(path);
		String returnText = "";
		if (node != null) {
			returnText = node.getText();
			returnText = returnText == null ? "" : returnText.trim();
			if ("".equals(returnText) && isMustExist.length >= 2
					&& isMustExist[1]) {
				throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS, "报文协议中["
						+ path + "]节点值不能为空，请核查！");
			}

		} else {
			if (isMustExist.length >= 1 && isMustExist[0]) {
				throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,
						"报文协议中必须存在的[" + path + "]节点可能不存在，请核查！");
			}
		}
		return returnText;
	}

	/**
	 * 根据DOC取路径下节点的值，可以判断节点是否存在或节点值是否为空
	 *
	 * @param ele
	 *            XML对象
	 * @param path
	 *            节点全路径
	 * @param isMustExist
	 *            节点是否必须存在，true说明节点必须存在否则报异常
	 * @return
	 * @throws BusinessException
	 */
	public static String getNodeText(Element ele, String path,
									 boolean... isMustExist) throws BusinessException {
		Node node = ele.selectSingleNode(path);
		String returnText = "";
		if (node != null) {
			returnText = node.getText();
			returnText = returnText == null ? "" : returnText.trim();
			if ("".equals(returnText) && isMustExist.length >= 2
					&& isMustExist[1]) {
				throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS, "报文协议中["
						+ path + "]节点值不能为空，请核查！");
			}
		} else {
			if (isMustExist.length >= 1 && isMustExist[0]) {
				throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,
						"报文协议中必须存在的[" + path + "]节点可能不存在，请核查！");
			}
		}
		return returnText;
	}


	/**
	 * 格式化输出XML，换行缩排方便查看
	 * 
	 * @param retStr XML串
	 * @return String 格式化后的XML串
	 * @throws Exception
	 */
	public static String formatXML(String retStr) throws Exception {
		String res = null;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(retStr);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
		// 格式化XML
		OutputFormat format = new OutputFormat();
		// 设置元素是否有子节点都输出
		format.setExpandEmptyElements(true);
		format.setExpandEmptyElements(false);
		format.setEncoding("UTF-8");
		format.setIndent("    "); // 在元素后换行，每一层元素缩排4个空格

		OutputStream outputStream = new ByteArrayOutputStream();
		XMLWriter writer = new XMLWriter(outputStream, format);
		writer.write(doc);
		writer.close();
		res = outputStream.toString();
		return res;
	}

	// -------------------公共响应信息-----------------------------
	/**
	 * 格式化输出XML
	 * @param doc
	 * @return XML字符串
	 */
	public static String format(Document doc) {
		doc.setXMLEncoding("UTF-8");
		return doc.asXML();
	}

	/**
	 * 获取返回的报文Document对象
	 * @param doc 传入的参数报文对象
	 * @return 返回的报文对象
	 * @throws BusinessException
	 */
	public static Document getReturnDoc(Document doc,Map<String,Object> map,String ...codeMsg) throws BusinessException{
		UipHead uipHead = getUipHeadFromXmlDoc(doc);
		return getReturnDoc(uipHead,map,codeMsg);
	}

	/**
	 * 获取返回的报文Document对象
	 * @param doc 传入的参数报文对象
	 * @return 返回的报文对象
	 * @throws BusinessException
	 */
	public static Document getReturnDoc(Document doc,String ...codeMsg) throws BusinessException{
		UipHead uipHead = getUipHeadFromXmlDoc(doc);
		return getReturnDoc(uipHead,codeMsg);
	}
	
	/**
	 * 获取返回的报文Document对象
	 * @param uipHead 传入的参数报文对象
	 * @return 返回的报文对象
	 * @throws BusinessException
	 */
	public static Document getReturnDoc(UipHead uipHead, String ...codeMsg) throws BusinessException{
		return getReturnDoc(uipHead,null,codeMsg);
	}

	/**
	 * 获取返回的报文Document对象
	 * @param uipHead 传入的参数报文对象
	 * @return 返回的报文对象
	 * @throws BusinessException
	 */
	public static Document getReturnDoc1(Document doc,String json, String ...codeMsg) throws BusinessException{
		UipHead uipHead = getUipHeadFromXmlDoc(doc);
		Document returnDocument = DocumentHelper.createDocument();
		Element root = returnDocument.addElement("uipResponse");// 创建root节点
		uipHead.setRespTime(String.valueOf(System.currentTimeMillis()));
		addBeanToElement(uipHead, root.addElement("uipHead"));
		Element uipBody = root.addElement("uipBody");
		int len = codeMsg.length;
		if(null == json){
			Map map = new HashMap<>();
			map.put("returnCode","9009");
			map.put("returnMsg","服务异常");
			json=JSON.toJSONString(map);
		}
		uipBody.setText(json);
		return returnDocument;
	}
	public static Document getReturnDoc(UipHead uipHead, Map<String,Object> map, String ...codeMsg) throws BusinessException{
		Document returnDocument = DocumentHelper.createDocument();
		Element root = returnDocument.addElement("uipResponse");// 创建root节点
		uipHead.setRespTime(String.valueOf(System.currentTimeMillis()));
		addBeanToElement(uipHead, root.addElement("uipHead"));
		Element uipBody = root.addElement("uipBody");
		int len = codeMsg.length;
		if(null == map){map = new HashMap<>();}
		if(codeMsg != null && len !=0) {
			map.put("returnCode","0000");
			map.put("returnMsg","OK");
			if(len >=1 && codeMsg[0] != null) {
				map.put("returnCode",codeMsg[0]);
			}
			if(len >=2 && codeMsg[1] != null) {
				map.put("returnMsg",codeMsg[1]);
			}
		}
		if(!map.containsKey("returnCode")){map.put("returnCode","0000");}
		if(!map.containsKey("returnMsg")){map.put("returnMsg","OK");}
		uipBody.setText(JSON.toJSONString(map));
		return returnDocument;
	}
	/**
	 * 获取返回报文的头元素，可以扩展添加一些节点
	 * @param returnDocument 返回的报文对象
	 * @return Element 报文头元素
	 */
	public static Element getReturnHeadElement(Document returnDocument) {
		return getSingleElementByPath(returnDocument, "//uipResponse/uipHead");
	}
	/**
	 * 获取返回报文的体元素，协议报文内容可以添加到此节点中
	 * @param returnDocument 返回的报文对象
	 * @return Element 报文体元素
	 */
	public static Element getReturnBodyElement(Document returnDocument) {
		return getSingleElementByPath(returnDocument, "//uipResponse/uipBody");
	}
	
	/**
	 * 从源报文中获取头信息对象
	 * @param doc 源报文对象
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public static UipHead getUipHeadFromXmlDoc(Document doc) throws BusinessException{
		Element headEle = doc.getRootElement().element("uipHead");
		List<Element> heads = headEle.elements();
		Map<String, String> headMap = new HashMap<String, String>();
		for (Element ele : heads) {
			headMap.put(ele.getName(), ele.getTextTrim());
		}
		UipHead uipHead = new UipHead();

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(UipHead.class);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				Method writeMethod = descriptor.getWriteMethod();
				if (!"class".equals(propertyName) &&
						null != writeMethod && headMap.containsKey(propertyName)) {

					writeMethod.invoke(uipHead,
							new Object[] { headMap.get(propertyName) });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,
					"解析报文头信息时出现错误getUipHead");
		}
		return uipHead;
	}
	
	/**
	 * 添加一个节点
	 * @param eleName 要添加的节点名称
	 * @param eleValue 节点值
	 * @param ele 被添加的节点对象
	 * @throws BusinessException
	 */
	public static void addStringToElement(String eleName,String eleValue, Element ele) throws BusinessException{
		ele.addElement(eleName).setText(eleValue==null?"":eleValue);
	}
	
	/**
	 * 把map中的所有值都添加到一个节点中
	 * @param map
	 * @param ele
	 * @throws BusinessException
	 */
	public static void addMapToElement(Map<String,String> map, Element ele) throws BusinessException{
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = ((null == map.get(key) || "null" == map.get(key)) ?"":map.get(key));
			ele.addElement(key).setText(value);
		}
	}
	
	

	/**
	 * 把一个bean对象的值添加到一个元素节点上，节点名称是对象的属性名称
	 * @param obj 数据对象
	 * @param ele 要添加的目标元素
	 * @throws BusinessException
	 */
	public static void addBeanToElement(Object obj, Element ele) throws BusinessException{
		Class<? extends Object> cls = obj.getClass();
		//Field[] fields = cls.getDeclaredFields();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cls);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				Method readMethod = descriptor.getReadMethod();
				if (!"class".equals(propertyName) && null != readMethod) {
					Object valObj = readMethod.invoke(obj, new Object[] {});
					if(null != valObj) {
						Element attrEle = ele.addElement(propertyName);
//						attrEle.setText(String.valueOf(valObj));
						attrEle.setText(CommonUtil.objToString(valObj, null));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,
					"解析报文头信息时出现错误getUipHead");
		}
	}
	
	//--------------------------输出异常信息-----------------------------------
	/**
	 * 
	 * 获取异常返回报文对象. 
	 * @param doc 请求入参XML对象
	 * @param code 异常编码
	 * @param msg 异常提示信息
	 * @return  协议报文对象
	 *
	 */
	public static Document getExceptionRetDoc(Document doc,String code,String msg){
		Document returnDocument = XmlUtils.getReturnDoc(doc);
		Element ele = XmlUtils.getReturnBodyElement(returnDocument);
		//ele.addElement("returnCode").setText(code);
		//ele.addElement("returnMsg").setText(msg);
		ele.setText("{\"returnCode\":\""+code+"\",\"returnMsg\":\""+msg+"\"}");
		return returnDocument;
	}
	
	/**
	 * 
	 * 若请求的入参不是XML格式，在转换时会报异常信息，用于返回告知请求者参数错误. 
	 * @return XML串
	 *
	 */
	public static String getXmlParseExceptionRetXml(){
		Document returnDocument = DocumentHelper.createDocument();
		Element root = returnDocument.addElement("uipResponse");// 创建root节点
		UipHead uipHead = new UipHead();
		uipHead.setRespTime(String.valueOf(System.currentTimeMillis()));
		
		addBeanToElement(uipHead, root.addElement("uipHead"));
		root.addElement("uipBody");
		
		Element ele = XmlUtils.getReturnBodyElement(returnDocument);
		//ele.addElement("returnCode").setText("0001");
		//ele.addElement("returnMsg").setText("客户端请求的报文不符合XML定义，请检查!");
		ele.setText("{\"returnCode\":\"0001\",\"returnMsg\":\"客户端请求的报文不符合XML定义，请检查!\"}");
		return XmlUtils.format(returnDocument);
	}
	
	/**
	 * 
	 * 如果没有入参，则给出默认入参，body中为空. 
	 * @return xml报文串
	 */
	public static String getDefaultRequestXml(){
		Document returnDocument = DocumentHelper.createDocument();
		Element root = returnDocument.addElement("uipRequest");// 创建root节点
		UipHead uipHead = new UipHead();
		uipHead.setUid(String.valueOf(System.currentTimeMillis()));
		uipHead.setAppCode("0001");
		uipHead.setUipAuthId("001");
		uipHead.setServiceCode("TaoService");
		uipHead.setOperation("method");
		uipHead.setB2bReqTime(String.valueOf(System.currentTimeMillis()));
		uipHead.setUipReqTime(String.valueOf(System.currentTimeMillis()));
		uipHead.setFromIp("127.0.0.1");
		uipHead.setTerminalType("006");
		uipHead.setValidType("0");
		uipHead.setCompressType("0");
		uipHead.setRespTime(String.valueOf(System.currentTimeMillis()));
		addBeanToElement(uipHead, root.addElement("uipHead"));
		root.addElement("uipBody");
		return XmlUtils.format(returnDocument);
	}

	/**
	 *
	 * 获取某些节点对应的值对象.
	 * <p>与 <code>getNodeTexts</code>方法类似，只是把结果封装成了javaBean
	 * </p>
	 * @param doc
	 * @param paramList
	 * @param cls
	 * @return
	 * @throws BusinessException
	 *
	 */
	public static <T> T getBeanFromNodeTexts(Document doc,String xpath,Class<T> cls,ReqParamBean... paramList)
			throws BusinessException{
		if(null == paramList || paramList.length ==0) {
			return null;
		}
		Node node = doc.selectSingleNode(xpath);
		if(node instanceof Element) {
			Map<String,Object> retMap = new HashMap<String,Object>();
			for(ReqParamBean bean:paramList) {
				retMap.put(bean.getKey(),getNodeText((Element)node,bean.getXpath(),
						bean.isNodeMustExist(),bean.isValueMustExist()));
			}
			return CommonUtil.mapToBean(retMap, cls);
		}else {
			throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS, "报文协议中["
					+ xpath + "]节点值不能为空，请核查！");
		}
	}
	

}
