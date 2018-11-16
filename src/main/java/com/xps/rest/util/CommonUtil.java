/*
 * Project Name:mms-aop 
 * File Name: CommonUtil.java 
 * Package Name:com.yaoyaohao.tao.util
 * Date:2015-9-17下午4:39:33 
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/ 
package com.xps.rest.util;


import com.xps.rest.bean.FieldRelative;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 
 * 通用工具类，定义一些常用的方法. 
 *  
 * @author xiongps
 * @version 2015-9-17 下午4:33:27   
 * @since JDK1.7
 */
public class CommonUtil {
	
	/**
	 * 
	 * 获取一个N位的随机数字符串. 
	 *  
	 * @param n 位数
	 * @return 随机数字串
	 *
	 */
	public static String randomNumber(int n) {
		StringBuilder sb = new StringBuilder();
        Random rand = new Random();
         do {
        	 int randInt  = rand.nextInt(99999);
             sb.append(randInt);
         }while(sb.length() <= n);
         return sb.substring(0, n);
	}

	/**
	 * 
	 * map对象转换成javaBean. 
	 * <p>map中的key与javaBean的属性对应<i>相同</i>
	 * </p> 
	 * @param map 需要转换的Map集合
	 * @param cls 目标javaBean类
	 * @return 目标javaBean对象
	 * @throws BusinessException 
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T mapToBean(Map<String,Object> map,Class<T> cls)
	throws BusinessException {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cls); 
			T obj = cls.newInstance();
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for (int i = 0; i< propertyDescriptors.length; i++) {
	            PropertyDescriptor descriptor = propertyDescriptors[i];
	            String propertyName = descriptor.getName();
	            if (map.containsKey(propertyName)) {
	                Object value = map.get(propertyName);
	                Method method =  descriptor.getWriteMethod();
	                if(null == method) {
	                	throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,
	                			cls.getName()+"."+propertyName+"没有set方法不符合javabean规范!");
	                }
	                Class ptype = method.getParameterTypes()[0];
	                Object args[] = new Object[1];
					if (Integer.class == ptype) {//兼容1.00的情况
						args[0] = (int)Double.parseDouble(String.valueOf(value));
					}  else if(Long.class == ptype) {
	                	args[0] = Long.valueOf(String.valueOf(value));
	                } else if(String.class == ptype) {
	                	args[0] = String.valueOf(value);
	                } else if(ptype == List.class) {
	                	args[0] = (List)value;
	                } else if(ptype == Map.class) {
	                	args[0] = (Map)value;
	                } else if(ptype == Date.class) {
	                	args[0] = (Date)value;
	                } else if(ptype == BigDecimal.class) {
						args[0] = new BigDecimal((String)value);
					} else {
	                	args[0] = value;
	                }
	                method.invoke(obj, args);
	            }
	        }
	        return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,"CommonUtil.mapToBean转换错误");
		}
	}
	
	/**
	 * 
	 * javaBean转换成Map集合，javaBean的属性对应于map中的key. 
	 * @param bean 数据对象
	 * @return Map 集合
	 * @throws BusinessException 
	 *
	 */
	public static Map<String,Object> toMap(Object bean)
			throws BusinessException{
		try {
			Class <? extends Object> type = bean.getClass();
	        Map<String,Object> returnMap = new HashMap<String,Object>();
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);

	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for (int i = 0; i< propertyDescriptors.length; i++) {
	            PropertyDescriptor descriptor = propertyDescriptors[i];
	            String propertyName = descriptor.getName();
	            if (!propertyName.equals("class")) {
	                Method readMethod = descriptor.getReadMethod();
	                Object result = readMethod.invoke(bean, new Object[0]);
	                if (result != null) {
	                    returnMap.put(propertyName, result);
	                } else {
	                    returnMap.put(propertyName, "");
	                }
	            }
	        }
	        return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(DefaultExceptionComponent.SYS_ERROR,"CommonUtil.toMap对象转换成map错误");
		}
	}
	
	/**
	 * 
	 * 从一个Map集合中取一部分需要的值并且把对应的value值强转为String类型. 
	 * @param map 数据集合
	 * @param fields 需要获取的字段值定义，若不传则默认源Map数据集合中所有的数据的value都转换成字符串类型
	 * @return Map&lt;String,String&gt;类型的目标数据集合
	 * @throws BusinessException 
	 *
	 */
	public static Map<String,String> subMapAsValueString(Map<String,Object> map, 
														 FieldRelative...fields)
					throws BusinessException{
		Map<String,String> retMap = new HashMap<String,String>();
		if(null == fields || fields.length == 0) {
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				Object objVal = map.get(key);
				if(null != objVal) {
					if(objVal instanceof List  
							|| objVal instanceof Map 
							|| objVal instanceof Object[]) {
						//列表 集合和数组都不处理 没法转成字符串
						continue;
					}
					retMap.put(key, objToString(objVal,null));
				}
			}
		} else {
			for(FieldRelative f:fields) {
				if(map.containsKey(f.getSrcField())) {
					retMap.put(f.getTargetField(),objToString(
							map.get(f.getSrcField()),f.getFormat()));
				}
			}
		}
		return retMap;
	}
	
	
	
	/**
	 * 校验一个字符串值是否为空或null值，若为空则抛出异常提示. 
	 * @param str 字符串
	 * @param emsg 若为空时的异常提示信息
	 * @throws BusinessException 
	 *
	 */
	public static void checkNullEmpty(String str,String emsg) 
			throws BusinessException{
		if(null == str || "".equals(str)) {
			throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,
					emsg);
		}
	}
	
	/**
	 * 校验一个对象值是否为空或null值，若为空则抛出异常提示. 
	 * @param str 字符串
	 * @param emsg 若为空时的异常提示信息
	 * @throws BusinessException 
	 *
	 */
	public static void checkNullEmpty(Object str,String emsg) 
			throws BusinessException{
		if(null == str) {
			throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,
					emsg);
		}
	}
	

	/**  
	 * @Title: compareDate
	 * @Description: 比较两个日期型变量 如果d1大于d2则抛出异常
	 * @param d1
	 * @param d2
	 * @param emsg
	 * @throws BusinessException  void  
	 * @date: 2015-9-24 上午11:59:01   
	*/
	public static void compareDate(Date d1, Date d2,String emsg) 
			throws BusinessException{
		if(d1.compareTo(d2) > 0) {
			throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
					emsg);
		}
	}

	/**
	 * 简单基本类型数据，判断是否包含在一个数组中.
	 * <p>只作简单的<code>==</code>或<code>equals</code>判断</p>
	 * @param datas 数据集合数组
	 * @param data 要校验的数据
	 * @return boolean 若包含此对象则返回<code>true</code>,否则返回<code>false</code>.
	 * @throws Exception  
	 *
	 */
	public static <T> boolean contains(T []datas,T data) throws Exception {
		for(T t:datas ) {
			if(t == data || t.equals(data)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 把对象按给定的格式转成字符串，主要针对BigDecimal日期等. 
	 * @param obj 源对象
	 * @param format 需要转的目标格式 为null或""会给定默认值，BigDecimal 保留两位小数，
	 * Date为YYYYMMDD,Timestamp为YYYYMMDDhhmmss
	 * @return 目标串
	 */
	public static String objToString(Object obj,String format) {
		String val = "";
		if(obj instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat(
					(format==null||"".equals(format))?"0.00":format);
			val = df.format((BigDecimal)obj);
		} else if(obj instanceof Timestamp){
			val = DateUtil.dateToStr((Timestamp)obj,
					(format==null||"".equals(format))? DateUtil.PATN_NOTSPLIT_24HMS:format);
		} else if(obj instanceof Date) {
			val = DateUtil.dateToStr((Date)obj,
					(format==null||"".equals(format))? DateUtil.PATN_NOTSPLIT_YMD:format);
		} else {
			if(null == obj){
				obj = "";
			}
			val = String.valueOf(obj);
		}
		return val;
	}
	
	/**
	 * 
	 * 把string强转成需要的对象. 
	 *  
	 * @param str 源字符串
	 * @param T 目标对象
	 * @param emsg 异常提示信息
	 * @param isMayNullOrEmpty 是否允许为null或空
	 * @return 目标对象
	 * @throws BusinessException 
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> T stringToObj(String str, Class<T> T,String emsg,boolean isMayNullOrEmpty) throws BusinessException{
		if(!isMayNullOrEmpty && (null == str || "".equals(str))){
			throw new BusinessException(DefaultExceptionComponent.NULL_OR_EMPTY,
					emsg);
		}
		try{
			return (T)str;
		}catch(Exception e){
			throw new BusinessException(DefaultExceptionComponent.NOT_CAST,
					emsg);
		}
	}
	
	/**  
	 * @Title: stringToInteger
	 * @Description: 将字符串转换为Integer类型  
	 * @param str 需要转换的字符串
	 * @param flag true-允许为空 false-不允许为空
	 * @param emsg 异常提示信息
	 * @return
	 * @throws BusinessException  Integer  
	 * @date: 2015-9-24 下午7:23:34   
	*/
	public static Integer stringToInteger(String str, boolean flag, String emsg) throws BusinessException{
		if(null == str || "".equals(str)) {
			if(flag){
				return null;
			}else{
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}						
		}else{
			try{
				return Integer.valueOf(str);
			}catch(Exception e){
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}
		}
	}
	
	/**  
	 * @Title: stringToLong
	 * @Description: 将字符串转换为Long类型  
	 * @param str 需要转换的字符串
	 * @param flag true-允许为空
	 * @param emsg 异常提示信息
	 * @return
	 * @throws BusinessException  Long  
	 * @date: 2015-9-24 下午7:24:47   
	*/
	public static Long stringToLong(String str, boolean flag, String emsg) throws BusinessException{
		if(null == str || "".equals(str)) {
			if(flag){
				return null;
			}else{
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}						
		}else{
			try{
				return Long.valueOf(str);
			}catch(Exception e){
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}
		}
	}
	
	/**  
	 * @Title: stringToBigDecimal
	 * @Description: 将字符串转换为BigDecimal类型  
	 * @param str 需要转换的字符串
	 * @param flag true-允许为空
	 * @param emsg 异常提示信息
	 * @return
	 * @throws BusinessException  BigDecimal  
	 * @date: 2015-9-24 下午7:24:51   
	*/
	public static BigDecimal stringToBigDecimal(String str, boolean flag, String emsg) throws BusinessException{
		if(null == str || "".equals(str)) {
			if(flag){
				return null;
			}else{
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}						
		}else{
			try{
				return new BigDecimal(str);
			}catch(Exception e){
				throw new BusinessException(DefaultExceptionComponent.PARAMETER_ERROR,
						emsg);
			}
		}
	}

	public static boolean isIn(Comparable res,Comparable[]arrs) {
		if(res == null || arrs==null||arrs.length == 0) {
			return false;
		}
		if(res.getClass() != (arrs[0]).getClass()) {
			return false;
		}

		for(Comparable a:arrs) {
			if(res.compareTo(a) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * BigDecimal 四舍五入转成double并保留两位小数
	 * @param b
	 * @return
     */
	public static String bigDecimalHalfUpTo2Scale(BigDecimal b){
		if(b == null) {
			return "0.00";
		}
		double a = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat df=new DecimalFormat("########0.00");
		return df.format(a);
	}

	public static void main(String []args) {
		System.out.println(bigDecimalHalfUpTo2Scale(new BigDecimal("78854440")));
	}
}
