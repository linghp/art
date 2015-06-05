package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 卖家退货订单统计
 * @author libo
 *
 */
public class SellerRefoundstat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4523906898765628325L;

	@Expose
	private Integer pageSize;
	
	@Expose
	private Integer start;
	
	@Expose
	private List<SellerRefoundOrderInfo> data = new ArrayList<SellerRefoundOrderInfo>();
	
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

	public List<SellerRefoundOrderInfo> getData() {
		return data;
	}

	public void setData(List<SellerRefoundOrderInfo> data) {
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
		return "SellerRefoundstat [pageSize=" + pageSize + ", start=" + start
				+ ", data=" + data + ", resultCount=" + resultCount
				+ ", pageCount=" + pageCount + ", pageIndex=" + pageIndex + "]";
	}
	
	public boolean isNull(){
		return data == null || data.size() == 0;
	}

}
