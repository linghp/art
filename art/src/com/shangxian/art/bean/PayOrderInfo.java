package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单支付详情
 * 
 * @author libo
 *
 */
public class PayOrderInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> orderNumber;
	private int amount;
	private String payPassword;
	private String payType;

	public PayOrderInfo() {
		super();
	}

	public PayOrderInfo(List<String> orderNumber, int amount,
			String payPassword, String payType) {
		super();
		this.orderNumber = orderNumber;
		this.amount = amount;
		this.payPassword = payPassword;
		this.payType = payType;
	}

	public List<String> getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(List<String> orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
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
		return "PayOrderInfo [orderNumber=" + orderNumber + ", amount="
				+ amount + ", payPassword=" + payPassword + ", payType="
				+ payType + "]";
	}

}
