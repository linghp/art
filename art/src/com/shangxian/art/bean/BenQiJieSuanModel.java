package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BenQiJieSuanModel implements Serializable{

	@Expose
	private Integer balanceDiscount;
	@Expose
	private Integer amount;//结算金额
	@Expose
	private Integer id;
	@Expose
	private Object phoneNum;
	@Expose
	private String settlementType;//结算类型{ NORMAL,//正常    EMERGENCY//紧急结算
	@Expose
	private Integer shopID;//店铺ID
	@Expose
	private Object personame;
	@SerializedName("settlementTime_last")
	@Expose
	private String settlementTimeLast;//结算前一天时间
	@Expose
	private String shopName;//店铺名字
	@Expose
	private Boolean isFinished;//是否已经结算
	@Expose
	private Boolean finished;
	@Expose
	private Object remarks;
	@Expose
	private String settlementTime;//结算时间
	public Integer getBalanceDiscount() {
		return balanceDiscount;
	}
	public void setBalanceDiscount(Integer balanceDiscount) {
		this.balanceDiscount = balanceDiscount;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Object getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(Object phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(String settlementType) {
		this.settlementType = settlementType;
	}
	public Integer getShopID() {
		return shopID;
	}
	public void setShopID(Integer shopID) {
		this.shopID = shopID;
	}
	public Object getPersoname() {
		return personame;
	}
	public void setPersoname(Object personame) {
		this.personame = personame;
	}
	public String getSettlementTimeLast() {
		return settlementTimeLast;
	}
	public void setSettlementTimeLast(String settlementTimeLast) {
		this.settlementTimeLast = settlementTimeLast;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Boolean getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	public Object getRemarks() {
		return remarks;
	}
	public void setRemarks(Object remarks) {
		this.remarks = remarks;
	}
	public String getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}
	@Override
	public String toString() {
		return "BenQiJieSuanModel [balanceDiscount=" + balanceDiscount
				+ ", amount=" + amount + ", id=" + id + ", phoneNum="
				+ phoneNum + ", settlementType=" + settlementType + ", shopID="
				+ shopID + ", personame=" + personame + ", settlementTimeLast="
				+ settlementTimeLast + ", shopName=" + shopName
				+ ", isFinished=" + isFinished + ", finished=" + finished
				+ ", remarks=" + remarks + ", settlementTime=" + settlementTime
				+ "]";
	}
}
