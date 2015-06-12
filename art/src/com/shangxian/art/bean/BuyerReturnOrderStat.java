package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class BuyerReturnOrderStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3884246477775762017L;

	@Expose
	private Integer pageSize;
	
	@Expose
	private Integer start;
	
	@Expose
	private List<BuyerReturnOrderInfo> data = new ArrayList<BuyerReturnOrderInfo>();
	
	@Expose
	private Integer resultCount;
	
	@Expose
	private Integer pageCount;
	
	@Expose
	private Integer pageIndex;
	
	public boolean isNull(){
		return data == null || data.size() == 0;
	}

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

	public List<BuyerReturnOrderInfo> getData() {
		return data;
	}

	public void setData(List<BuyerReturnOrderInfo> data) {
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
		return "BuyerReturnOrderStat [pageSize=" + pageSize + ", start="
				+ start + ", data=" + data + ", resultCount=" + resultCount
				+ ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + "]";
	}
}
