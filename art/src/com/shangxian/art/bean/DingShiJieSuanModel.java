package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DingShiJieSuanModel implements Serializable{

	@Expose
	private List<BenQiJieSuanModel> data = new ArrayList<BenQiJieSuanModel>();
	@Expose
	private Integer pageSize;
	@Expose
	private Integer pageCount;
	@Expose
	private Integer start;
	@Expose
	private Integer resultCount;
	@Expose
	private Integer pageIndex;
	public List<BenQiJieSuanModel> getData() {
		return data;
	}
	public void setData(List<BenQiJieSuanModel> data) {
		this.data = data;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getResultCount() {
		return resultCount;
	}
	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	@Override
	public String toString() {
		return "DingShiJieSuanModel [data=" + data + ", pageSize=" + pageSize
				+ ", pageCount=" + pageCount + ", start=" + start
				+ ", resultCount=" + resultCount + ", pageIndex=" + pageIndex
				+ "]";
	}
	
}
