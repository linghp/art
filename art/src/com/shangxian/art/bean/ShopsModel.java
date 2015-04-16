package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ShopsModel implements Serializable{
	@Expose
	private Integer id;
	@Expose
	private String owner;
	@Expose
	private String name;
	@Expose
	private String indexLogo;
	@Expose
	private String logo;
	@Expose
	private Integer noticeCount;
	@Expose
	private Integer productCount;
	@Expose
	private Integer newCount;
	@Expose
	private Integer specialCount;
	@Expose
	private List<ProductDto> productDtos = new ArrayList<ProductDto>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIndexLogo() {
		return indexLogo;
	}
	public void setIndexLogo(String indexLogo) {
		this.indexLogo = indexLogo;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getNoticeCount() {
		return noticeCount;
	}
	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Integer getNewCount() {
		return newCount;
	}
	public void setNewCount(Integer newCount) {
		this.newCount = newCount;
	}
	public Integer getSpecialCount() {
		return specialCount;
	}
	public void setSpecialCount(Integer specialCount) {
		this.specialCount = specialCount;
	}
	public List<ProductDto> getProductDtos() {
		return productDtos;
	}
	public void setProductDtos(List<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}
	@Override
	public String toString() {
		return "ShopsModel [id=" + id + ", owner=" + owner + ", name=" + name
				+ ", indexLogo=" + indexLogo + ", logo=" + logo
				+ ", noticeCount=" + noticeCount + ", productCount="
				+ productCount + ", newCount=" + newCount + ", specialCount="
				+ specialCount + ", productDtos=" + productDtos + "]";
	}
	


}
