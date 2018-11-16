package com.xps.rest.bean;

/**
 * 
 * 报文头对象. 
 * 
 * @author xiongps
 * @version 2015-9-21 上午8:45:51   
 * @since JDK1.7
 */
public class UipHead {
 
	private String uid;
	
	private String appCode;
	
	private String uipAuthId;
	
	private String serviceCode;
	
	private String operation;
	
	private String b2bReqTime;
	
	private String uipReqTime;
	
	private String respTime;
	
	private String fromIp;
	
	private String validType;
	
	private String terminalType;
	
	private String compressType;
	
	private String remark1;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getUipAuthId() {
		return uipAuthId;
	}

	public void setUipAuthId(String uipAuthId) {
		this.uipAuthId = uipAuthId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getB2bReqTime() {
		return b2bReqTime;
	}

	public void setB2bReqTime(String b2bReqTime) {
		this.b2bReqTime = b2bReqTime;
	}

	public String getUipReqTime() {
		return uipReqTime;
	}

	public void setUipReqTime(String uipReqTime) {
		this.uipReqTime = uipReqTime;
	}

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getCompressType() {
		return compressType;
	}

	public void setCompressType(String compressType) {
		this.compressType = compressType;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	
	
	

}
