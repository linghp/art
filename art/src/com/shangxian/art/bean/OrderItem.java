package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 提交订单
 * 
 * @author Administrator
 *
 */
public class OrderItem implements Serializable{
	private String productId;
	private String specs;
	private int quantity;
	private String recommand;
	
	public OrderItem(String productId, String specs, int quantity,
			String recommand) {
		super();
		this.productId = productId;
		this.specs = specs;
		this.quantity = quantity;
		this.recommand = recommand;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getRecommand() {
		return recommand;
	}
	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}
	
}