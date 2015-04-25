package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarStoreBean implements Serializable{

	private String shopId;
	private String shopName;
	private String logo;
	private String recommand;
	private List<ListCarGoodsBean> itemDtos;
	
	@Override
	public String toString() {
		return "ListCarStoreBean [shopId=" + shopId + ", shopName=" + shopName
				+ ", logo=" + logo + ", recommand=" + recommand + ", itemDtos="
				+ itemDtos + "]";
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<ListCarGoodsBean> getItemDtos() {
		return itemDtos;
	}
	public void setItemDtos(List<ListCarGoodsBean> itemDtos) {
		this.itemDtos = itemDtos;
	}
	public String getRecommand() {
		return recommand;
	}
	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}



}
