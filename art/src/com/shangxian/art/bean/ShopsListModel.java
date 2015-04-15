package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ShopsListModel implements Serializable{
	@Expose
	private Integer shopId;
	@Expose
	private String shopName;
	@Expose
	private String logo;
	@Expose
	private String subTitle;
	@Expose
	private Integer price;
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ShopsListModel [shopId=" + shopId + ", shopName=" + shopName
				+ ", logo=" + logo + ", subTitle=" + subTitle + ", price="
				+ price + "]";
	}
	

}
