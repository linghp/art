package com.shangxian.art.bean;

import java.io.Serializable;

/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarGoodsBean implements Serializable{

	private String productId;
	private String name;
	private String shopId;
	private String specs;
	private String photo;
	private int quantity;
	private float promotionPrice;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(float promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	@Override
	public String toString() {
		return "ListCarGoodsBean [productId=" + productId + ", name=" + name
				+ ", shopId=" + shopId + ", specs=" + specs + ", photo="
				+ photo + ", quantity=" + quantity + ", promotionPrice="
				+ promotionPrice + "]";
	}
	



}
