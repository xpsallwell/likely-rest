/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.ws.http
 * File Name:		TaoService.java
 * @date:  2015-9-15-下午2:56:08  
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
  
package com.xps.rest.ws.http;

import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.exceptions.IAcctException;
import com.xps.rest.util.ProtocolUtil;
import com.xps.rest.ws.handler.BizzBeanFactory;
import com.xps.rest.ws.handler.ProcessService;
import com.xps.rest.ws.handler.ProtocolContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Arrays;

/**
 * 
 * 会员所有的接口服务类,统一使用HTTP的POST请求. 
 * <p>
 *     <tt>访问方式：</tt>每个接口服务提供单独的地址：
 *     						{@linkhttp://IP:port/mms-aop/services/CustomerService/{具体的方法名} }
 *    						
 * </p> 
 * @author xiongps
 * @version 2017-07-06 下午2:57:26
 * @since JDK1.7
 */
@Path("/CustomerService")
public class CustomerService extends BaseService{

	 /**
	  * 
	  * 统一接收处理会员所有的接口请求. 
	  * <p>
	  * 	通过path匹配会员所有的POST接口请求，并根据请求的方法找对应的服务类处理具体业务
	  * </p> 
	  * @param method URL中TaoService/后的部分
	  * @param xml 请求参数 协议报文
	  * @return String 协议报文
	  *
	  */
	@POST
	@Path("/{method}")
	@Produces("text/xml;charset=UTF-8") 
	public String serviceMethods(@PathParam("method") String method,@RequestBody String content) {
		return super.handler(method,content);
	}

}
