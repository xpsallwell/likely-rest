/* 
 * Project Name:mms-aop 
 * File Name:DateUtil.java 
 * Package Name:com.yaoyaohao.tao.util
 * Date:2015-9-16上午8:55:01 
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/  
package com.xps.rest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 日期工具类. 
 * @author xiongps
 * @version 2015-9-18 下午4:48:10   
 * @since JDK1.7
 */
public class DateUtil {
	
	public static final String PATN_NOMAL_24HMS = "yyyy-MM-dd HH:mm:ss";
	public static final String PATN_NOMAL_YMD = "yyyy-MM-dd";
	
	public static final String PATN_NOTSPLIT_24HMS = "yyyyMMddHHmmss";
	public static final String PATN_NOTSPLIT_YMD = "yyyyMMdd";

	public static final String PATN_NOTSPLIT_YM = "yyyyMM";
	
	
	private DateUtil(){};
	
	/**
	 * 
	 * 按给定的格式把date对象转换成日期串. 
	 * @param date 日期对象
	 * @param pattern 日期格式
	 * @return str 格式化后的日期串
	 */
	public static String dateToStr(Date date,String pattern) {
		 SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		 return sdf.format(date);
	}
	
	//获得当前日期
	public static String getCurrentDate() {
		 SimpleDateFormat formatter = new SimpleDateFormat(PATN_NOTSPLIT_YMD);
		 return formatter.format(new Date());
	}

	//获取当前年月
	public static String getCurrentYearAndMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat(PATN_NOTSPLIT_YM);
		return formatter.format(new Date());
	}
	
}
  