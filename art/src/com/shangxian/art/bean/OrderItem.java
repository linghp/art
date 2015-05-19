package com.shangxian.art.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 提交订单
 * 
 * @author Administrator
 *
 */
public class OrderItem implements Serializable{
	private String shopId;
	private List<String> cartOrderItemId;
	private String guestMessage;
	
	public OrderItem(String shopId, List<String> cartOrderItemId,
			String guestMessage) {
		super();
		this.shopId = shopId;
		this.cartOrderItemId = cartOrderItemId;
		this.guestMessage = guestMessage;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public List<String> getCartOrderItemId() {
		return cartOrderItemId;
	}

	public void setCartOrderItemId(List<String> cartOrderItemId) {
		this.cartOrderItemId = cartOrderItemId;
	}

	public String getGuestMessage() {
		return guestMessage;
	}

	public void setGuestMessage(String guestMessage) {
		this.guestMessage = guestMessage;
	}

	@Override
	public String toString() {
		return "OrderItem [shopId=" + shopId + ", cartOrderItemId="
				+ cartOrderItemId + ", guestMessage=" + guestMessage + "]";
	}

	
}