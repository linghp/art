package com.shangxian.art.bean;

import java.io.Serializable;

public class SubClassificationModel implements Serializable{

	private String subtitle;
	private String subimg;
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSubimg() {
		return subimg;
	}
	public void setSubimg(String subimg) {
		this.subimg = subimg;
	}
	
	@Override
	public String toString() {
		return "SubClassificationModel [subtitle=" + subtitle + ", subimg="
				+ subimg + "]";
	}
	
}
