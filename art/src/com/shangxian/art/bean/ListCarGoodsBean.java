package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 商品bean
 * @author Administrator
 *
 */
public class ListCarGoodsBean implements Serializable{

	private String cartItemId;
	private String productId;
	private String name;
	private String shopId;
	private Map<String, List<String>> specs;
	private Map<String, String> selectedSpec;
	private String photo;
	private int quantity;
	private float promotionPrice;
	
	public Map<String, List<String>> getSpecs() {
		return specs;
	}
	public void setSpecs(Map<String, List<String>> specs) {
		this.specs = specs;
	}

	
	public Map<String, String> getSelectedSpec() {
		return selectedSpec;
	}
	public void setSelectedSpec(Map<String, String> selectedSpec) {
		this.selectedSpec = selectedSpec;
	}
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
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	@Override
	public String toString() {
		return "ListCarGoodsBean [cartItemId=" + cartItemId + ", productId="
				+ productId + ", name=" + name + ", shopId=" + shopId
				+ ", specs=" + specs + ", selectedSpec=" + selectedSpec
				+ ", photo=" + photo + ", quantity=" + quantity
				+ ", promotionPrice=" + promotionPrice + "]";
	}
	



}
