 
/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.util
 * File Name:		AOPApplication.java 
 * @date:  2015-9-28-下午6:24:46  
 * Copyright (c) 2015, Administrator All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
  
package com.xps.rest.listener;

import com.xps.rest.ws.handler.SpringContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**   
 * 这里写简单介绍（注意后面的". "）. 
 * <p>这里写详细说明
 *	<i>斜体</i><code>属性或方法简单代码</code>
 *	<pre>写大段代码说明</pre>
 * </p> 
 * @author Administrator
 * @version 2015-9-28 下午6:24:46   
 * @since JDK1.7     
 */
public class AOPApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public AOPApplication() {
		try {
			WebApplicationContext wac = (WebApplicationContext)SpringContextHolder.getApplicationContext();
			String name = wac.getServletContext().getInitParameter("rest.service.package");
			singletons.addAll(getPackageClassSet(name));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	
	private Set<Object> getPackageClassSet(String pacageName)
			throws Exception {
		 ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		 Set<Object>  classList = new HashSet<Object>();
       String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +   
               ClassUtils.convertClassNameToResourcePath(pacageName)+"/**/*.class";  
       Resource[] resources = resourcePatternResolver.getResources(pattern);  
       MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);  
       for (Resource resource : resources) {
           if (resource.isReadable()) {  
               MetadataReader reader = readerFactory.getMetadataReader(resource);  
               String className = reader.getClassMetadata().getClassName(); 
               Class<? extends Object> clazz = Class.forName(className);
               Path ann =  clazz.getAnnotation(Path.class);
               if(ann !=null) {
            	   classList.add(clazz.newInstance());  
               }
           }  
       }  
       return classList;  
   }  
}
  