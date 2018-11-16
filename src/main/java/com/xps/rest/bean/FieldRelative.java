/** 
 * Project Name:mms-aop 
 * File Name:FieldRelative.java 
 * Package Name:com.yaoyaohao.tao.bean
 * Date:2015-9-15下午3:17:42 
 * Copyright (c) 2015, Administrator All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/  
/**  
 * Project Name:	mms-aop 
 * Package Name:	com.yaoyaohao.tao.bean
 * File Name:		FieldRelative.java 
 * @date:  2015-9-15-下午3:17:42  
 * Copyright (c) 2015, Administrator All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
 */  
  
package com.xps.rest.bean;

/**   
 * @ClassName:  FieldRelative   
 * @Description:TODO(两个字符对应)   
 * @author Administrator
 * @date:   2015-9-15 下午3:17:42   
 *      
 */
/**
 * 
 * 设置两个字段的关系bean. 
 * 
 * @author xiongps
 * @version 2015-9-21 上午8:42:00   
 * @since JDK1.7
 */
public class FieldRelative {

	private String srcField;
	
	private String targetField;
	
	private String format;
	
	public FieldRelative(String srcField) {
		this.srcField = srcField;
		this.targetField = srcField;
	}
	
	public FieldRelative(String srcField,String targetField) {
		this.srcField = srcField;
		this.targetField = targetField;
	}
	
	public FieldRelative(String srcField,String targetField, String format) {
		this.srcField = srcField;
		this.targetField = targetField;
		this.format = format;
	}

	public String getSrcField() {
		return srcField;
	}

	public void setSrcField(String srcField) {
		this.srcField = srcField;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	
	
	
}
  