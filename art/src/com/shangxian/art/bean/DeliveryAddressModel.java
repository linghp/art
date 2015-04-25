package com.shangxian.art.bean;

import java.io.Serializable;

public class DeliveryAddressModel implements Serializable{

	private String name;
	private String num;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "DeliveryAddressModel [name=" + name + ", num=" + num
				+ ", address=" + address + "]";
	}
	
}
