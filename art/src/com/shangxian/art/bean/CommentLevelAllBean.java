package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 获取评论的好评 中评 差评的数量
 * 
 * @author Administrator
 *
 */
public class CommentLevelAllBean implements Serializable {
	@Expose
	private Integer ZERO=0;
	@Expose
	private Integer NEGATIVE=0;
	@Expose
	private Integer POSITIVE=0;
	public Integer getZERO() {
		return ZERO;
	}
	public void setZERO(Integer zERO) {
		ZERO = zERO;
	}
	public Integer getNEGATIVE() {
		return NEGATIVE;
	}
	public void setNEGATIVE(Integer nEGATIVE) {
		NEGATIVE = nEGATIVE;
	}
	public Integer getPOSITIVE() {
		return POSITIVE;
	}
	public void setPOSITIVE(Integer pOSITIVE) {
		POSITIVE = pOSITIVE;
	}
	@Override
	public String toString() {
		return "CommentLevelAllBean [ZERO=" + ZERO + ", NEGATIVE=" + NEGATIVE
				+ ", POSITIVE=" + POSITIVE + "]";
	}

	
}
