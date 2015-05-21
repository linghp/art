package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class AddDeliveryAddressModel implements Serializable{

	@Expose
	private String deliveryAddress;
	@Expose
	private String receiverName;
	@Expose
	private String receiverTel;
	@Expose
	private Boolean isDefault;
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverTel() {
		return receiverTel;
	}
	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Override
	public String toString() {
		return "AddDeliveryAddressModel [deliveryAddress=" + deliveryAddress
				+ ", receiverName=" + receiverName + ", receiverTel="
				+ receiverTel + ", isDefault=" + isDefault + "]";
	}
	
	
}
