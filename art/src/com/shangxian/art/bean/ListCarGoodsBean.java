package com.shangxian.art.bean;
/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarGoodsBean {

	public String goodsId;
	public String goodsName;
	public String storeId;
	public int goodsNum;
	public float price;
	
	public ListCarGoodsBean(String goodsId, String goodsName, String storeId,
			int goodsNum,float price) {
		super();
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.storeId = storeId;
		this.goodsNum = goodsNum;
		this.price=price;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	


}
