package com.shangxian.art.bean;

import java.io.Serializable;

public class ClassificationModel implements Serializable{

	private String title;
	private String summary;
	private String img;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	@Override
	public String toString() {
		return "ClassificationModel [title=" + title + ", summary=" + summary
				+ ", img=" + img + "]";
	}
	
	
}
