package com.shangxian.art.bean;

import java.io.Serializable;

import com.baidu.mapapi.model.LatLng;

/**
 * 商铺位置信息 (跳转地图封装数据)
 * 
 * @author libo
 *
 */
public class ShopLocInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7187545990729805839L;

	private String id;

	private String title;

	private String address;

	private String photo;

	private MyLatLng lng;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MyLatLng getLng() {
		return lng;
	}

	public void setLng(MyLatLng lng) {
		this.lng = lng;
	}

	public LatLng getLatLng() {
		if (lng==null||lng.getLat() == Double.MIN_VALUE
				|| lng.getLng() == Double.MIN_VALUE)
			throw new IllegalStateException("lat and lng is null");
		return new LatLng(lng.getLat(), lng.getLng());
	}

	@Override
	public String toString() {
		return "ShopLocInfo [id=" + id + ", title=" + title + ", address="
				+ address + ", photo=" + photo + ", lng=" + lng + "]";
	}

}
