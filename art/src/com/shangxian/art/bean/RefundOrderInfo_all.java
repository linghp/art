package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class RefundOrderInfo_all implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private String status;
	@Expose
	private String returnOrderNum;
	@Expose
	private String orderNumber;
	@Expose
	private String orderTime;
	@Expose
	private String returnOrderTime;
	@Expose
	private String handleOrderTime;
	@Expose
	private Integer buyerId;
	@Expose
	private String receiverName;
	@Expose
	private String buyerName;
	@Expose
	private List<RefundOrderInfo> returnOrderItemDtos = new ArrayList<RefundOrderInfo>();
	@Expose
	private Integer totalPrice;
	@Expose
	private String buyerAddress;
	@Expose
	private String orderType;
	@Expose
	private Integer totalQuantity;
	@Expose
	private String returnReason;
	@Expose
	private String returnAttch;
	@Expose
	private String buyerMessege;
	@Expose
	private String sellerReason;
	@Expose
	private String isGoods;
	@Expose
	private String shippingName;
	@Expose
	private String shippingNum;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnOrderNum() {
		return returnOrderNum;
	}

	public void setReturnOrderNum(String returnOrderNum) {
		this.returnOrderNum = returnOrderNum;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getReturnOrderTime() {
		return returnOrderTime;
	}

	public void setReturnOrderTime(String returnOrderTime) {
		this.returnOrderTime = returnOrderTime;
	}

	public String getHandleOrderTime() {
		return handleOrderTime;
	}

	public void setHandleOrderTime(String handleOrderTime) {
		this.handleOrderTime = handleOrderTime;
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

	public List<RefundOrderInfo> getReturnOrderItemDtos() {
		return returnOrderItemDtos;
	}

	public void setReturnOrderItemDtos(List<RefundOrderInfo> returnOrderItemDtos) {
		this.returnOrderItemDtos = returnOrderItemDtos;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

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

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getReturnAttch() {
		return returnAttch;
	}

	public void setReturnAttch(String returnAttch) {
		this.returnAttch = returnAttch;
	}

	public String getBuyerMessege() {
		return buyerMessege;
	}

	public void setBuyerMessege(String buyerMessege) {
		this.buyerMessege = buyerMessege;
	}

	public String getSellerReason() {
		return sellerReason;
	}

	public void setSellerReason(String sellerReason) {
		this.sellerReason = sellerReason;
	}

	public String getIsGoods() {
		return isGoods;
	}

	public void setIsGoods(String isGoods) {
		this.isGoods = isGoods;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingNum() {
		return shippingNum;
	}

	public void setShippingNum(String shippingNum) {
		this.shippingNum = shippingNum;
	}

	@Override
	public String toString() {
		return "RefundOrderInfo_all [status=" + status + ", returnOrderNum="
				+ returnOrderNum + ", orderNumber=" + orderNumber
				+ ", orderTime=" + orderTime + ", returnOrderTime="
				+ returnOrderTime + ", handleOrderTime=" + handleOrderTime
				+ ", buyerId=" + buyerId + ", receiverName=" + receiverName
				+ ", buyerName=" + buyerName + ", returnOrderItemDtos="
				+ returnOrderItemDtos + ", totalPrice=" + totalPrice
				+ ", buyerAddress=" + buyerAddress + ", orderType=" + orderType
				+ ", totalQuantity=" + totalQuantity + ", returnReason="
				+ returnReason + ", returnAttch=" + returnAttch
				+ ", buyerMessege=" + buyerMessege + ", sellerReason="
				+ sellerReason + ", isGoods=" + isGoods + ", shippingName="
				+ shippingName + ", shippingNum=" + shippingNum + "]";
	}

}
