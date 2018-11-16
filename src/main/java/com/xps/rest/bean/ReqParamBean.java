package com.xps.rest.bean;

/**
 * 
 * 请求参数bean 取XML某个节点的值，并且用于判断节点是否为可空，传入的值是否可空. 
 * 
 * @author xiongps
 * @version 2015-9-21 上午8:44:24   
 * @since JDK1.7
 */
public class ReqParamBean {

	private String key;
	
	private String xpath;
	
	private boolean nodeMustExist;
	
	private boolean valueMustExist;
	
	public ReqParamBean(String xpath){
		this.xpath = xpath;
		this.key = this.xpath.substring(this.xpath.lastIndexOf("/")+1);
		this.nodeMustExist = false;
		this.valueMustExist = false;
	}
	public ReqParamBean(String xpath,boolean nodeMustExist){
		this.xpath = xpath;
		this.key = this.xpath.substring(this.xpath.lastIndexOf("/")+1);
		this.nodeMustExist = nodeMustExist;
		this.valueMustExist = false;
	}
	public ReqParamBean(String xpath,boolean nodeMustExist,boolean valueMustExist){
		this.xpath = xpath;
		this.key = this.xpath.substring(this.xpath.lastIndexOf("/")+1);
		this.nodeMustExist = nodeMustExist;
		this.valueMustExist = valueMustExist;
	}
	
	public ReqParamBean(String xpath,String key,boolean nodeMustExist,boolean valueMustExist){
		this.key = key;
		this.xpath = xpath;
		this.nodeMustExist = nodeMustExist;
		this.valueMustExist = valueMustExist;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public boolean isNodeMustExist() {
		return nodeMustExist;
	}
	public void setNodeMustExist(boolean nodeMustExist) {
		this.nodeMustExist = nodeMustExist;
	}
	public boolean isValueMustExist() {
		return valueMustExist;
	}
	public void setValueMustExist(boolean valueMustExist) {
		this.valueMustExist = valueMustExist;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	 
	 
}
