package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.shangxian.art.utils.MyLogger;

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
	public String orderNumber="";
	@Expose
	private String status;
	@Expose
	public String shopId="";
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

	@Expose
	private String buyerAddress;
	@Expose
	private String orderType;
	@Expose
	private String oneTimeStatus;
	@Expose
	private String shippingNum;
	@Expose
	private String shippingName;

	/** 卖家 **/

	@Expose
	private Integer buyerId;

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOneTimeStatus() {
		return oneTimeStatus;
	}

	public void setOneTimeStatus(String oneTimeStatus) {
		this.oneTimeStatus = oneTimeStatus;
	}

	public String getShippingNum() {
		return shippingNum;
	}

	public void setShippingNum(String shippingNum) {
		this.shippingNum = shippingNum;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Integer shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Expose
	private String receiverName;
	@Expose
	private String buyerName;
	@Expose
	private Integer productPrice;
	@Expose
	private Integer shippingFee;
	@Expose
	private Integer orderId;
	@Expose
	private String orderTime;

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

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
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
				+ ", createDate=" + createDate + ", buyerAddress="
				+ buyerAddress + ", orderType=" + orderType
				+ ", oneTimeStatus=" + oneTimeStatus + ", shippingNum="
				+ shippingNum + ", shippingName=" + shippingName + ", buyerId="
				+ buyerId + ", receiverName=" + receiverName + ", buyerName="
				+ buyerName + ", productPrice=" + productPrice
				+ ", shippingFee=" + shippingFee + ", orderId=" + orderId
				+ ", orderTime=" + orderTime + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof MyOrderItem)) {
			return false;
		} else {
			MyOrderItem bean = (MyOrderItem) o;
			// MyLogger.i(bean.orderNumber+"$$$$$"+orderNumber);
			// MyLogger.i(bean.shopId+"$$$$$"+shopId);
			// MyLogger.i((bean.orderNumber.trim().equals(orderNumber.trim())
			// &&bean.equals(shopId))+"");
			if (bean.orderNumber.equals(orderNumber)
					&& bean.shopId.equals(shopId)) {
				MyLogger.i("");
				return true;
			} else {
				return false;
			}
		}
	}

}