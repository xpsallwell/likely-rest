/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.ws.handler
 * File Name:		BizzBeanFactory.java 
 * @date:  2015-9-15-下午2:56:08  
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
 
package com.xps.rest.ws.handler;


import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.listener.BizClassBean;

import java.util.Map;

/**
 * 
 * 简单工厂模式,生产所有接口业务类用于处理具体的业务. 
 * 
 * @author xiongps
 * @version 2015-9-18 下午3:54:57   
 * @since JDK1.7
 */
public class BizzBeanFactory {
	/**所有的业务类集合,key为接口服务名称,value为具体业务处理类对象*/
	private static Map<String,BizClassBean> beanClassMap = null;
	
	private BizzBeanFactory(){}
	
	/**
	 * 根据注解的BizzBeanService的名称取对应的服务类
	 * @param beanName 服务类实体名称
	 * @return ProcessService 具体的服务类对象
	 */
	public static ProcessService getBizzServiceBean(String scope,String beanName,ProtocolType protocolType)
	{
		ProcessService processService =null;
		
		if(beanName == null && "".equals(beanName)) {
			throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,"接口服务名称不能为空");
		}

		BizClassBean bizClassBean = getBeanClassMap().get(beanName);
		if(null == bizClassBean) {
			bizClassBean = getBeanClassMap().get(scope+"."+beanName);
		}
		if(null == bizClassBean){
			throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"接口服务["+beanName+"]不存在,请检查!");
		}
		if( protocolType.getPn() != (protocolType.getPn() & bizClassBean.getPn())) {
			throw new BusinessException(DefaultExceptionComponent.NOT_SUPPORT_PROTOCOL,
					"接口服务["+beanName+"]不支持["+protocolType.toString()+"]协议");
		}
		try {
			processService = (ProcessService) SpringContextHolder.getSingleBean(bizClassBean.getClazz());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,"接口服务["+beanName+"]未找到对应的springBean");
		}

		return processService;
	}
	/**
	 * 
	 * 获取所有的接口服务类集合. 
	 * @return Map 集合
	 *
	 */
	public static Map<String,BizClassBean> getBeanClassMap() {
		if(beanClassMap == null) {
			//未加载到接口类
			throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"系统监听加载服务失败");
		}
		return beanClassMap;
	}
	
	/**
	 * 
	 * 设置接口服务类集合. 
	 * <p>目前加载的方式为启动时加载所有的接口服务类到此集合中</p> 
	 * @param beanClassMap 
	 * @see LoadBizzBeanListener
	 */
	public static void setBeanClassMap(
			Map<String,BizClassBean> beanClassMap) {
		BizzBeanFactory.beanClassMap = beanClassMap;
	}

}
