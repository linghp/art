package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassityCommdityModel implements Serializable{
	@Expose
	private Integer categoryId;
	@Expose
	private Integer id;
	@Expose
	private String photo;
	@Expose
	private String name;
	@Expose
	private String reDetails;
	@Expose
	private Integer originalPrice;
	@Expose
	private Integer promotionPrice;
	@Expose
	private Integer shopId;
	@SerializedName("new")
	@Expose
	private Boolean _new;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReDetails() {
		return reDetails;
	}
	public void setReDetails(String reDetails) {
		this.reDetails = reDetails;
	}
	public Integer getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Integer getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public Boolean get_new() {
		return _new;
	}
	public void set_new(Boolean _new) {
		this._new = _new;
	}
	@Override
	public String toString() {
		return "ClassityCommdityModel [categoryId=" + categoryId + ", id=" + id
				+ ", photo=" + photo + ", name=" + name + ", reDetails="
				+ reDetails + ", originalPrice=" + originalPrice
				+ ", promotionPrice=" + promotionPrice + ", shopId=" + shopId
				+ ", _new=" + _new + "]";
	}
	
}
