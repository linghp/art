package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ClassificationModel implements Serializable{
	
	@Expose
	private Integer id;
	@Expose
	private String name;
	@Expose
	private Integer level;
	@Expose
	private String subTitle;
	@Expose
	private Integer sortOrder;
	@Expose
	private String photo;
	@Expose
	private Integer pid;
	public boolean isexpand;
	@Expose
	private List<ClassificationModel> productCategoryDtos = new ArrayList<ClassificationModel>();

	@Override
	public String toString() {
		return "ClassificationModel [id=" + id + ", name=" + name + ", level="
				+ level + ", subTitle=" + subTitle + ", sortOrder=" + sortOrder
				+ ", photo=" + photo + ", pid=" + pid
				+ ", productCategoryDtos=" + productCategoryDtos + "]";
	}
	public List<ClassificationModel> getProductCategoryDtos() {
		return productCategoryDtos;
	}
	public void setProductCategoryDtos(List<ClassificationModel> productCategoryDtos) {
		this.productCategoryDtos = productCategoryDtos;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
