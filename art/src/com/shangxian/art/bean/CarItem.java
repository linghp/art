package com.shangxian.art.bean;

import java.io.Serializable;

/**
 * 购物车item
 * 
 * @author Administrator
 *
 */
public class CarItem implements Serializable{
	public final static int SECTION = 0;
	public final static int ITEM = 1;

	public String id;
	public int type;
	public ListCarStoreBean listCarStoreBean;
	public ListCarGoodsBean listCarGoodsBean;

	public CarItem(int type,ListCarStoreBean listCarStoreBean,ListCarGoodsBean listCarGoodsBean,String id){
		this.id=id;
		this.type=type;
		this.listCarStoreBean=listCarStoreBean;
		this.listCarGoodsBean=listCarGoodsBean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ListCarStoreBean getListCarStoreBean() {
		return listCarStoreBean;
	}

	public void setListCarStoreBean(ListCarStoreBean listCarStoreBean) {
		this.listCarStoreBean = listCarStoreBean;
	}

	public ListCarGoodsBean getListCarGoodsBean() {
		return listCarGoodsBean;
	}

	public void setListCarGoodsBean(ListCarGoodsBean listCarGoodsBean) {
		this.listCarGoodsBean = listCarGoodsBean;
	}
	
	
}
