package com.shangxian.art.bean;

import java.io.Serializable;

/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarStoreBean implements Serializable{

	public String storeid;
	public String storename;
	

	public ListCarStoreBean(String storeid, String storename) {
		this.storeid = storeid;
		this.storename = storename;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}

}
