package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JinJiJieSuanModel implements Serializable{

	@Expose
	private Integer id;
	@Expose
	private Integer shopID;
	@Expose
	private String shopName;
	@Expose
	private Object settlementTime;
	@SerializedName("settlementTime_last")
	@Expose
	private String settlementTimeLast;
	@Expose
	private Integer amount;
	@Expose
	private String settlementType;
	@Expose
	private Boolean isFinished;
	@Expose
	private Integer balanceDiscount;
	@Expose
	private String remarks;
	@Expose
	private String personame;
	@Expose
	private String phoneNum;
	@Expose
	private Boolean finished;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShopID() {
		return shopID;
	}
	public void setShopID(Integer shopID) {
		this.shopID = shopID;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Object getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Object settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getSettlementTimeLast() {
		return settlementTimeLast;
	}
	public void setSettlementTimeLast(String settlementTimeLast) {
		this.settlementTimeLast = settlementTimeLast;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public Boolean getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}
	public Integer getBalanceDiscount() {
		return balanceDiscount;
	}
	public void setBalanceDiscount(Integer balanceDiscount) {
		this.balanceDiscount = balanceDiscount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
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
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	@Override
	public String toString() {
		return "JinJiJieSuanModel [id=" + id + ", shopID=" + shopID
				+ ", shopName=" + shopName + ", settlementTime="
				+ settlementTime + ", settlementTimeLast=" + settlementTimeLast
				+ ", amount=" + amount + ", settlementType=" + settlementType
				+ ", isFinished=" + isFinished + ", balanceDiscount="
				+ balanceDiscount + ", remarks=" + remarks + ", personame="
				+ personame + ", phoneNum=" + phoneNum + ", finished="
				+ finished + "]";
	}
}
