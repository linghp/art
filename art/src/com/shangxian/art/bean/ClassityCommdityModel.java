package com.shangxian.art.bean;

import java.io.Serializable;

public class ClassityCommdityModel implements Serializable{

	private String title;
	private String summary;
	private String price;
	private String img;
	private String shop;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	@Override
	public String toString() {
		return "ClassityCommdityModel [title=" + title + ", summary=" + summary
				+ ", price=" + price + ", img=" + img + ", shop=" + shop + "]";
	}
	
	
}
