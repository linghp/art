package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SearchProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer pageSize = Integer.MIN_VALUE;  //分页每页多少条数据
	
	@Expose
	private Integer start;
	
	@Expose
	private List<ListCarGoodsBean> data = new ArrayList<ListCarGoodsBean>();
	
	@Expose
	private Integer resultCount;
	
	@Expose
	private Integer pageCount;
	
	@Expose
	private Integer pageIndex;

	public SearchProductInfo() {
		super();
	}

	public SearchProductInfo(Integer pageSize, Integer start,
			List<ListCarGoodsBean> data, Integer resultCount,
			Integer pageCount, Integer pageIndex) {
		super();
		this.pageSize = pageSize;
		this.start = start;
		this.data = data;
		this.resultCount = resultCount;
		this.pageCount = pageCount;
		this.pageIndex = pageIndex;
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

	public List<ListCarGoodsBean> getData() {
		return data;
	}

	public void setData(List<ListCarGoodsBean> data) {
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

	public boolean isNull(){
		if (data == null || data.size() == 0 || pageSize == Integer.MIN_VALUE) {
			return true;
		}
		return false;
	}
	
	public boolean isMore(){
		if (resultCount < pageSize || pageIndex == pageCount) {
			return false;
		} 
		return true;
	}
	
	@Override
	public String toString() {
		return "SearchProductInfo [pageSize=" + pageSize + ", start=" + start
				+ ", data=" + data + ", resultCount=" + resultCount
				+ ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + "]";
	}

}
