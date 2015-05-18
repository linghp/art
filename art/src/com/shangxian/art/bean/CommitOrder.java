package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 提交订单
 * 
 * @author Administrator
 *
 */
public class CommitOrder implements Serializable {
	private String addressId;
	private List<OrderItem> cartItems;

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public List<OrderItem> getOrderItems() {
		return cartItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.cartItems = orderItems;
	}

	@Override
	public String toString() {
		return "CommitOrder [addressId=" + addressId + ", orderItems="
				+ cartItems + "]";
	}

}