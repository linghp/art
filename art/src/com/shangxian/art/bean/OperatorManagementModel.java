package com.shangxian.art.bean;

import java.io.Serializable;

public class OperatorManagementModel implements Serializable{

	private String name;
	private String num;
	private String operator;
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "OperatorManagementModel [name=" + name + ", num=" + num
				+ ", operator=" + operator + "]";
	}
	
}
