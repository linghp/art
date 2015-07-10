package com.shangxian.art.bean;

import java.io.Serializable;

public class ShengQingJieSuanModel implements Serializable{
	private String personame;
	private String phoneNum;
	private String amount;
	private String remarks;
	public String getPersoname() {
		return personame;
	}
	public void setPersoname(String personame) {
		this.personame = personame;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "ShengQingJieSuanModel [personame=" + personame + ", phoneNum="
				+ phoneNum + ", amount=" + amount + ", remarks=" + remarks
				+ "]";
	}
}
