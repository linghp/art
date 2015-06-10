package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 快递信息
 * 
 * @author libo
 */
public class ExpressInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3547976273449444512L;

	@Expose
	private Integer id;
	
	@Expose
	private String name;
	
	@Expose
	private Integer distPrice;

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

	public Integer getDistPrice() {
		return distPrice;
	}

	public void setDistPrice(Integer distPrice) {
		this.distPrice = distPrice;
	}

	@Override
	public String toString() {
		return "Expressinfo [id=" + id + ", name=" + name + ", distPrice="
				+ distPrice + "]";
	}
}
