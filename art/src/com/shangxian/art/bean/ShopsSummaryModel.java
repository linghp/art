package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ShopsSummaryModel implements Serializable{

	@Expose
	private String owner;
	@Expose
	private String name;
	@Expose
	private String logo;
	@Expose
	private List<String> phoneNumbers = new ArrayList<String>();
	@Expose
	private String address;
	@Expose
	private String details;
	@Expose
	private Object consignee;
	@Expose
	private Object conPhoneNumber;
	@Expose
	private Object conAddress;
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Object getConsignee() {
		return consignee;
	}
	public void setConsignee(Object consignee) {
		this.consignee = consignee;
	}
	public Object getConPhoneNumber() {
		return conPhoneNumber;
	}
	public void setConPhoneNumber(Object conPhoneNumber) {
		this.conPhoneNumber = conPhoneNumber;
	}
	public Object getConAddress() {
		return conAddress;
	}
	public void setConAddress(Object conAddress) {
		this.conAddress = conAddress;
	}
	@Override
	public String toString() {
		return "ShopsSummaryActivityModel [owner=" + owner + ", name=" + name
				+ ", logo=" + logo + ", phoneNumbers=" + phoneNumbers
				+ ", address=" + address + ", details=" + details
				+ ", consignee=" + consignee + ", conPhoneNumber="
				+ conPhoneNumber + ", conAddress=" + conAddress + "]";
	}
	
	
}
