package com.xps.rest.bean;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 3323662769709542683L;
	private int page;
	private int limit;
	private Map<String,Object> conditions;
	private String sortString="";

	private long total;        //总记录数
    private List<T> rows;    //结果集

	public PageBean(){}
    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     * @param list          page结果
     */
    public PageBean(List<T> list) {
		if(list == null) {
			this.rows = new ArrayList<T>();
			this.total = 0;
		} else  if (list instanceof PageList) {
			PageList<T> page = (PageList<T>) list;
			Paginator paginator = page.getPaginator();
			this.rows = page;
			this.total = paginator.getTotalCount();
        } else {
			this.rows = list;
			this.total =list.size();
		}
    }

	public void setPageList(List<T> list){
		if(list == null) {
			this.rows = new ArrayList<T>();
			this.total = 0;
		} else  if (list instanceof PageList) {
			PageList<T> page = (PageList<T>) list;
			Paginator paginator = page.getPaginator();
			this.rows = page;
			this.total = paginator.getTotalCount();
		} else {
			this.rows = list;
			this.total =list.size();
		}
	}

	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	 
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	public String getSortString() {
		return sortString;
	}

	public void setSortString(String sortString) {
		this.sortString = sortString;
	}

	public PageBounds getPageBounds(){
		if(this.page >0 && this.limit >0
				&& this.sortString != null && !"".equals(this.sortString)) {
			return new PageBounds(this.page,this.limit, Order.formString(this.sortString));
		}
		if(this.page >0 && this.limit >0){
			return new PageBounds(this.page,this.limit);
		}
		return new PageBounds();
	}
	public PageBounds getPageBounds(boolean containsTotalCount){
		if(this.page >0 && this.limit >0
				&& this.sortString != null && !"".equals(this.sortString)) {
			return new PageBounds(this.page,this.limit, Order.formString(this.sortString),containsTotalCount);
		}
		if(this.page >0 && this.limit >0){
			return new PageBounds(this.page,this.limit,containsTotalCount);
		}
		return new PageBounds();
	}
}
