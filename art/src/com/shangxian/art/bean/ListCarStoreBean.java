package com.shangxian.art.bean;
/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarStoreBean {

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
