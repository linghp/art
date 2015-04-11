package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * 我的订单
 * 
 * @author Administrator
 *
 */
public class MyOrderItem implements Serializable {
	@Expose
	private String addressId;
	@Expose
	private String orderNumber;
	@Expose
	private String status;
	@Expose
	private Integer shopId;
	@Expose
	private String shopName;
	@Expose
	private String shopLogo;
	@Expose
	private List<ProductItemDto> productItemDtos = new ArrayList<ProductItemDto>();
	@Expose
	private float totalPrice;
	@Expose
	private Integer totalQuantity;
	@Expose
	private String createDate;
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public List<ProductItemDto> getProductItemDtos() {
		return productItemDtos;
	}
	public void setProductItemDtos(List<ProductItemDto> productItemDtos) {
		this.productItemDtos = productItemDtos;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "MyOrderItem [addressId=" + addressId + ", orderNumber="
				+ orderNumber + ", status=" + status + ", shopId=" + shopId
				+ ", shopName=" + shopName + ", shopLogo=" + shopLogo
				+ ", productItemDtos=" + productItemDtos + ", totalPrice="
				+ totalPrice + ", totalQuantity=" + totalQuantity
				+ ", createDate=" + createDate + "]";
	}



}