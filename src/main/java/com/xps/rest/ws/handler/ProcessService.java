/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.ws.handler
 * File Name:		ProcessService.java 
 * @date:  2015-9-15-下午2:56:08  
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
 
package com.xps.rest.ws.handler;

import org.dom4j.Document;

/**
 * 
 * 具体业务接口服务接收协议参数与返回协议报文统一接口类. 
 * <p>
 *	  业务接口类必须实现此接口或继承此接口的实现类
 * </p> 
 * @author xiongps
 * @version 2015-9-15 下午3:13:37   
 * @since JDK1.7
 */
public interface ProcessService {

	/**
	 * 
	 * 接收请求协议报文对象，业务处理完成后返回协议报文串.
	 * 
	 * @param protocolContent 协议报文对象
	 * @return 协议报文串
	 * @throws Exception 
	 *
	 */
	String process(ProtocolContent protocolContent) throws Exception;
	
}
