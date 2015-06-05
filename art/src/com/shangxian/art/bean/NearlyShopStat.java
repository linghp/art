package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 附近商铺数据统计
 * 
 * @author Administrator
 *
 */
public class NearlyShopStat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7248541170087340925L;

	@Expose
	private Integer status = Integer.MIN_VALUE;

	@Expose
	private Integer total = Integer.MIN_VALUE; // 总条数

	@Expose
	private Integer size = Integer.MIN_VALUE;

	@Expose
	private List<NearlyShopInfo> contents = new ArrayList<NearlyShopInfo>();

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<NearlyShopInfo> getContents() {
		return contents;
	}

	public void setContents(List<NearlyShopInfo> contents) {
		this.contents = contents;
	}

	public boolean isNull() {
		return status == Integer.MIN_VALUE || total == Integer.MIN_VALUE
				|| size == Integer.MIN_VALUE || contents != null
				|| contents.size() == 0;
	}

	@Override
	public String toString() {
		return "NearlyShopStat [status=" + status + ", total=" + total
				+ ", size=" + size + ", contents=" + contents + "]";
	}
}
