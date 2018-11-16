/* 
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.listener
 * File Name:		LoadBizzBeanListener.java 
 * @date:  2015-9-15-下午2:56:08  
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  

package com.xps.rest.listener;

import com.xps.rest.ws.handler.BizzBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.*;

/**
 * 
 * 业务服务处理类启动加载监听. 
 * <p>实现 <code>ServletContextListener</code>并配置在web.xml中用于启支加载标记为
 * <i>@BizBeanService(name="intfName")</i>接口业务类
 * </p> 
 * @author xiongps
 * @version 2015-9-18 下午4:27:30   
 * @since JDK1.7
 */
public class LoadBizBeanListener implements ServletContextListener {
	
    private static final Logger logger = LoggerFactory.getLogger(LoadBizBeanListener.class);
    /**接口业务服务类所有的包名称*/
	private final String PACKAGE_NAME = "ws.packages";
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		BizzBeanFactory.setBeanClassMap(null);
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		String packageNames = contextEvent.getServletContext().getInitParameter(PACKAGE_NAME);
		if(packageNames == null || "".equals(packageNames)) {
			packageNames = "com.xps.rest";
		}
		
		setBizAnnotationClassMap(packageNames.split(","));
	}
	
	/**
	 * 只取有BizzBeanService注解的类,其它的类不作处理
	 * 并把符合条件的类注入给BizzBeanFactory
	 * @param packageNames
	 */
	private void setBizAnnotationClassMap(String []packageNames){
		
		Map<String,BizClassBean> beanMap = new HashMap<>();
		try {
			for(String packageName:packageNames) {
				List<Class<?>> clazzList = getPacageClassList(packageName);
				
				for(Class<?> clazz:clazzList) {
					//校验类上有无BizBeanService注解，若有则是接口类，没有则不处理
					BizBeanService bizzAnn = clazz.getAnnotation(BizBeanService.class);
					if(bizzAnn != null) {
						logger.info(" loading biz接口类:"+("".equals(bizzAnn.scope())?"":bizzAnn.scope()+".")+bizzAnn.name()+"="+clazz.getName()+"protocol:"+ Arrays.toString(bizzAnn.protocol()));
						beanMap.put(("".equals(bizzAnn.scope())?"":bizzAnn.scope()+".")+bizzAnn.name(),
								new BizClassBean(bizzAnn.name(),bizzAnn.scope(),clazz,bizzAnn.protocol()));
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BizzBeanFactory.setBeanClassMap(beanMap);
	}

	public List<Class<?>> getPacageClassList(String pacageName) throws IOException, ClassNotFoundException {  
		 ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		 List<Class<?>>  classList = new ArrayList<Class<?>>();
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +   
                ClassUtils.convertClassNameToResourcePath(pacageName)+"/**/*.class";  
        Resource[] resources = resourcePatternResolver.getResources(pattern);  
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);  
        for (Resource resource : resources) {
            if (resource.isReadable()) {  
                MetadataReader reader = readerFactory.getMetadataReader(resource);  
                String className = reader.getClassMetadata().getClassName();  
                classList.add(Class.forName(className));  
            }  
        }  
        return classList;  
    }


}
