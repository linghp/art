package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 我的订单
 * 
 * @author Administrator
 *
 */
public class MyOrderItem_all implements Serializable {
	@Expose
	private Integer pageSize;
	@Expose
	private Integer start;
	@Expose
	private List<MyOrderItem> data;
	@Expose
	private Integer resultCount;
	@Expose
	private Integer pageCount;
	@Expose
	private Integer pageIndex;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public List<MyOrderItem> getData() {
		return data;
	}
	public void setData(List<MyOrderItem> data) {
		this.data = data;
	}
	public Integer getResultCount() {
		return resultCount;
	}
	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	@Override
	public String toString() {
		return "MyOrderItem_all [pageSize=" + pageSize + ", start=" + start
				+ ", data=" + data + ", resultCount=" + resultCount
				+ ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + "]";
	}


}