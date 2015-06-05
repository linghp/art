package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Refund_stat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -282952647372322275L;

	@Expose
	private Integer pageSize;
	@Expose
	private Integer start;
	@Expose
	private List<RefundOrderInfo_all> data = new ArrayList<RefundOrderInfo_all>();
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

	public List<RefundOrderInfo_all> getData() {
		return data;
	}

	public void setData(List<RefundOrderInfo_all> data) {
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
		return "Refund_stat [pageSize=" + pageSize + ", start=" + start
				+ ", data=" + data + ", resultCount=" + resultCount
				+ ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + "]";
	}
}
