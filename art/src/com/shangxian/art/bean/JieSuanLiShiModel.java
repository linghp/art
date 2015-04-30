package com.shangxian.art.bean;

import java.io.Serializable;

public class JieSuanLiShiModel implements Serializable{

	private String time;
	private String type;
	private String sprice;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSprice() {
		return sprice;
	}
	public void setSprice(String sprice) {
		this.sprice = sprice;
	}
	@Override
	public String toString() {
		return "JinJiJieSuanModel [time=" + time + ", type=" + type
				+ ", sprice=" + sprice + "]";
	}
	
}
