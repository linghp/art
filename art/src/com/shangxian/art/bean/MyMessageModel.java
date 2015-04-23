package com.shangxian.art.bean;

import java.io.Serializable;

public class MyMessageModel implements Serializable{

	private String img;
	private String title;
	private String content;
	private String time;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "MyMessageModel [img=" + img + ", title=" + title + ", content="
				+ content + ", time=" + time + "]";
	}
	
}
