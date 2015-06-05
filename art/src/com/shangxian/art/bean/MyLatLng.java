package com.shangxian.art.bean;

import java.io.Serializable;

public class MyLatLng implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double lat = Double.MIN_VALUE; // 纬度
	private double lng = Double.MIN_VALUE; // 经度

	public MyLatLng() {
		super();
	}

	public MyLatLng(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "MyLatLng [lat=" + lat + ", lng=" + lng + "]";
	}

}
