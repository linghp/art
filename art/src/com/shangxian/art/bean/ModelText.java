package com.shangxian.art.bean;

import java.io.Serializable;

public class ModelText implements Serializable{

	private String from;
	private String to;
	private String amount;
	private String payPassword;
	private String payType;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Override
	public String toString() {
		return "ModelText [from=" + from + ", to=" + to + ", amount=" + amount
				+ ", payPassword=" + payPassword + ", payType=" + payType + "]";
	}
	
}
