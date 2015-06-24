package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 商品的评论
 * 
 * @author Administrator
 *
 */
public class GoodsCommentBean implements Serializable {
	@Expose
	private Integer reviewId;
	@Expose
	private Integer productId;
	@Expose
	private String productName;
	@Expose
	private Integer level;
	@Expose
	private String comment;
	@Expose
	private String persionName;
	@Expose
	private List<String> photos = new ArrayList<String>();
	@Expose
	private String userScaleimage;
	@SerializedName("public")
	@Expose
	private Boolean _public;
	public Integer getReviewId() {
		return reviewId;
	}
	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPersionName() {
		return persionName;
	}
	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	public String getUserScaleimage() {
		return userScaleimage;
	}
	public void setUserScaleimage(String userScaleimage) {
		this.userScaleimage = userScaleimage;
	}
	public Boolean get_public() {
		return _public;
	}
	public void set_public(Boolean _public) {
		this._public = _public;
	}
	
	@Override
	public String toString() {
		return "GoodsCommentBean [reviewId=" + reviewId + ", productId="
				+ productId + ", productName=" + productName + ", level="
				+ level + ", comment=" + comment + ", persionName="
				+ persionName + ", photos=" + photos + ", userScaleimage="
				+ userScaleimage + ", _public=" + _public + "]";
	}

}
