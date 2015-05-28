package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class IandEDetailsModel implements Serializable{
	@Expose
	private Integer tradeId;
	@Expose
	private String tradeNumber;
	@Expose
	private String tradeTitle;
	@Expose
	private Integer totalPrice;
	@Expose
	private String tradeType;
	@Expose
	private String payType;
	@Expose
	private String transDate;
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeNumber() {
		return tradeNumber;
	}
	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}
	public String getTradeTitle() {
		return tradeTitle;
	}
	public void setTradeTitle(String tradeTitle) {
		this.tradeTitle = tradeTitle;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	@Override
	public String toString() {
		return "IandEDetailsModel [tradeId=" + tradeId + ", tradeNumber="
				+ tradeNumber + ", tradeTitle=" + tradeTitle + ", totalPrice="
				+ totalPrice + ", tradeType=" + tradeType + ", payType="
				+ payType + ", transDate=" + transDate + "]";
	}

}
