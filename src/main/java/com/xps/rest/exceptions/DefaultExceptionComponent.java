/*
 * Project Name:mms-aop 
 * File Name: DefaultExcepComponent.java 
 * Package Name:com.yaoyaohao.tao.exceptions
 * Date:2015-9-17下午4:39:33 
 * Copyright (c) 2015, xiongps All Rights Reserved. 
 * 药药好（杭州）网络科技有限公司
*/  

package com.xps.rest.exceptions;


/**
 * 
 * 常用的异常信息定义. 
 *  
 * @author xiongps
 * @version 2015-9-17 下午4:31:25   
 * @since JDK1.7
 */
public enum DefaultExceptionComponent implements IAcctException{
	
	SYS_ERROR("9999","{0}","系统错误", ExceptionLevel.ERROR),
	NOT_CAST("9998","{0}","转换错误", ExceptionLevel.ERROR),
	NULL_OR_EMPTY("2201","{0}","值不能为空", ExceptionLevel.FAIL),
	NOT_EXISTS("2202","{0}","元素不存在", ExceptionLevel.FAIL),
	NOT_SUPPORT("2203","{0}","不支持的类型", ExceptionLevel.FAIL),
	PARAMETER_ERROR("2204","{0}","请求参数错误", ExceptionLevel.FAIL),
	NOT_SUPPORT_PROTOCOL("2205","{0}","不支持的协议", ExceptionLevel.FAIL),
	EMPTY_RESULT("2206","{0}","未获取到结果", ExceptionLevel.FAIL);

	private String code;
	private String message;
	private String desc;
	private ExceptionLevel level;

	private DefaultExceptionComponent(String code, String message, String desc, ExceptionLevel level) {
		this.code = code;
		this.message = message;
		this.desc = desc;
		this.level = level;
	}
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public ExceptionLevel getLevel() {
		return this.level;
	}

}
