/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.ws.handler
 * File Name:		AbstractProcessService.java 
 * @date:  2015-9-15-下午2:56:08  
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
 
package com.xps.rest.ws.handler;

import com.alibaba.fastjson.JSONObject;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.util.XmlUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * 业务接口抽象实现类. 
 * <p>此类实现了业务接口中的<code>process(doc)</code>方法，将业务处理过程分为三步：</br>
 * 		<tt>第一步：</tt>解析doc取出相应的参数并校验参数是否必传，是否为空等</br>
 * 		<tt>第二步：</tt>处理具体业务，使用第一步收集的数据进行业务处理，完成后返回协议报文对象</br>
 * 		<tt>第三步：</tt>格式化报文对象并返回</br>
 * <tt>使用方式：</tt>
 * 			新建立业务处理类<i>extends</i>此抽象类，eg:
 * 		<pre>
 * 			class MyClass extends AbstractProcessService&lt;Map&lt;String,Object&gt;&gt; {}
 *	     </pre>
 *	则说明需要把协议参数装载到Map对象中使用，具体的使用方法参见<code>getClientParameterBean</code>
 *   与<code>comboBizz</code>
 * </p> 
 * @author xiongps
 * @version 2015-9-15 下午3:21:26   
 * @since JDK1.7  
 * @param <T> javaBean或集合对象
 */
public abstract class AbstractProcessService<T> implements ProcessService {
	/**打印日志对象，子类继承后可以直接使用*/
	protected static final Logger logger = LoggerFactory
			.getLogger(AbstractProcessService.class);
	/**请求处理成功默认编码*/
	protected static final String DEFAULT_SUCCESS_CODE = "0000";
	/**请求处理成功默认消息*/
	protected static final String DEFAULT_SUCCESS_MSG = "请求处理成功";

	/**
	 * 实现接口业务处理过程
	 * 
	 * @param protocolContent 入参
	 * @return String 返回协议报文
	 * @see ProcessService
	 * #process(org.dom4j.Document)
	 */
	@Override
	public String process(ProtocolContent protocolContent) throws Exception {
		String retStr = "";
		if (protocolContent != null) {
			T bean =null;
			switch (protocolContent.getProtocolType()){
				case JSON:
					bean = this.getClientParameterBean(protocolContent.getJson());
					break;
				case XML:
				case XJ:
					bean = this.getClientParameterBean(protocolContent.getDocument());
					break;
			}
			ProtocolContent retDoc = this.comboBiz(protocolContent, bean);
			if (retDoc == null) {
				throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,
						"业务处理完成后必须返回协议XML对象");
			}
			retStr = this.encodeFormat(this.formatReturn(retDoc));
		}
		return retStr;
	}

	/**
	 * 收集协议入参(子类实现). 
	 * <p>
	 * 	 根据传入的报文参数拼装自己的BEAN对象或把参数装入集合中,主要是完成
	 *  参数的收集与参数校验功能
	 * </p>
	 * @param jsonObject
	 *            入参对象
	 * @return T   
	 * 			    在继承此类时注入的javaBean或集合对象
	 * @throws BusinessException
	 */
	protected abstract T getClientParameterBean(JSONObject jsonObject)
			throws Exception;


	/**
	 * 收集协议入参(子类实现).
	 * <p>
	 * 	 根据传入的报文参数拼装自己的BEAN对象或把参数装入集合中,主要是完成
	 *  参数的收集与参数校验功能
	 * </p>
	 * @param document
	 *            入参对象
	 * @return T
	 * 			    在继承此类时注入的javaBean或集合对象
	 * @throws BusinessException
	 */
	protected abstract T getClientParameterBean(Document document)
			throws Exception;

	/**
	 * 根据参数处理接口业务
	 * <p>
	 * 	  具体的业务处理过程也由子类实现,<i>注：此类中不支持事务控制，若业务中出现多处增删改请在service服务中处理</i>
	 * </p>
	 * @param protocolContent
	 *            入参协议对象
	 * @param bean
	 *            <code>getClientParameterBean</code>返回的对象
	 * @return 返回协议报文
	 * @throws BusinessException
	 */
	protected abstract ProtocolContent comboBiz(ProtocolContent protocolContent, T bean)
			throws Exception;


	/**
	 * 格式化输出协议对象
	 * 
	 * @param protocolContent 格式化对象
	 * @return 协议字符串
	 */
	protected String formatReturn(ProtocolContent protocolContent) {
		if(protocolContent==null
			|| protocolContent.getProtocolType() == ProtocolType.JSON) {
			return protocolContent.getJson().toJSONString();
		}
		Document returnDocument = protocolContent.getDocument();
		return XmlUtils.format(returnDocument);
	}

	protected String encodeFormat(String returnStr) {
		try {
			return URLEncoder.encode(returnStr,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(DefaultExceptionComponent.NOT_CAST,
					"URLEncoder.encode has UnsupportedEncodingException error");
		}
	}
}
