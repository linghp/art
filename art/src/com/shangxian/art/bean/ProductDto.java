package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class ProductDto implements Serializable{

	private int id;
	
	@Expose
	private String photo;
	@Expose
	private String name;
	@Expose
	private Integer price;
	@Expose
	private String productId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", photo=" + photo + ", name=" + name
				+ ", price=" + price + ", productId=" + productId + "]";
	}
}
