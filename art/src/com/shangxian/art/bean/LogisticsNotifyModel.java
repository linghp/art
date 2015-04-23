package com.shangxian.art.bean;

import java.io.Serializable;

public class LogisticsNotifyModel implements Serializable{

	private String title;
	private String img;
	private String content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "LogisticsNotifyModel [title=" + title + ", img=" + img
				+ ", content=" + content + "]";
	}
	
}
