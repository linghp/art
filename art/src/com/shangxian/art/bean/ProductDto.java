package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ProductDto implements Serializable{

	@Expose
	private String photo;
	@Expose
	private String name;
	@Expose
	private Integer price;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ProductDto [photo=" + photo + ", name=" + name + ", price="
				+ price + "]";
	}
	

}
