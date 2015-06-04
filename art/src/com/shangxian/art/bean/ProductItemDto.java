package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 我的订单
 * 
 * @author ling
 *
 */
public class ProductItemDto implements Serializable {
	@Expose
	private String id;
	@Expose
	private HashMap<String, String> specs;
	@Expose
	private Integer quantity;
	private Integer productSourcePrice;
	private Integer unitPrice;
	private Integer discount;
	@Expose
	private String name;
	@Expose
	private float price;
	@Expose
	private String productSacle;
	@Expose
	private String orderItemStatus;

	public Integer getProductSourcePrice() {
		return productSourcePrice;
	}

	public void setProductSourcePrice(Integer productSourcePrice) {
		this.productSourcePrice = productSourcePrice;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getSpecs() {
		return specs;
	}

	public void setSpecs(HashMap<String, String> specs) {
		this.specs = specs;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getProductSacle() {
		return productSacle;
	}

	public void setProductSacle(String productSacle) {
		this.productSacle = productSacle;
	}

	@Override
	public String toString() {
		return "ProductItemDto [id=" + id + ", specs=" + specs + ", quantity="
				+ quantity + ", productSourcePrice=" + productSourcePrice
				+ ", unitPrice=" + unitPrice + ", discount=" + discount
				+ ", name=" + name + ", price=" + price + ", productSacle="
				+ productSacle + ", orderItemStatus=" + orderItemStatus + "]";
	}

}