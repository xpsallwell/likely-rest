/*
 * Project Name:mms-aop 
 * File Name: BizzBeanService.java 
 * Package Name:com.yaoyaohao.tao.listener
 * Date:2015-9-17下午4:39:33 
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/  
package com.xps.rest.listener;

import com.xps.rest.ws.handler.ProtocolType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 
 * 注解类 用于标记哪些类是接口业务类. 
 * <p>标记出接口业务类，在项目启动时加载这些类作为业务处理的对象源，使用方式：</br>
 * 		<tt>normal:</tt><code>@BizBeanService(name="intfName")</code>
 * 		<tt>other:</tt><code>@BizBeanService(name="intfName",scope="serviceName")</code>
 * </p> 
 * @author xiongps
 * @version 2015-9-18 下午4:20:43   
 * @since JDK1.7
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BizBeanService {
	/**接口服务名称*/
	String name();
	/**接口服务所属的域名,默认为空*/
	String scope() default "";
	/**接口服务支持的协议类型*/
	ProtocolType[] protocol() default ProtocolType.JSON;
}
