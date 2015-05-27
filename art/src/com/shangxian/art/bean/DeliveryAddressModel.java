package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryAddressModel implements Serializable{
	@Expose
	private String id;
	@Expose
	private String deliveryAddress;
	@Expose
	private String receiverName;
	@Expose
	private String receiverTel;
	@SerializedName("default")
	@Expose
	private Boolean _default;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Boolean get_default() {
		return _default;
	}
	public void set_default(Boolean _default) {
		this._default = _default;
	}
	@Override
	public String toString() {
		return "DeliveryAddressModel [id=" + id + ", deliveryAddress="
				+ deliveryAddress + ", receiverName=" + receiverName
				+ ", receiverTel=" + receiverTel + ", _default=" + _default
				+ "]";
	}


}
