package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.R.integer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 商品bean
 * 
 * @author Administrator
 *
 */
public class ListCarGoodsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cartItemId;
	@Expose
	private Integer categoryId;
	@Expose
	private String productId; // 商品id

	@Expose
	private String name; // 商品名称

	@Expose
	private String shopId; // 商铺id

	@Expose
	private Map<String, List<String>> specs;
	private Map<String, String> selectedSpec;
	@Expose
	private String photo; // 图片路径

	@Expose
	private int quantity; // 数量
	@Expose
	private float promotionPrice; // 促销价

	@Expose
	private String reDetails;

	@Expose
	private float originalPrice; // 商品原价
	@Expose
	private float shippingFee; // 运费
	@Expose
	private String shippingName; // 

	@SerializedName("new")
	@Expose
	private Boolean _new; // 是否是最新

	@Expose
	private Integer id;

	/**
	 * 搜索商铺 添加时间 5/20
	 */
	@Expose
	private String shopName;

	@Expose
	private String logo;

	@Expose
	private String subTitle;

	@Expose
	private Integer price;

	/**
	 * 关注商品列表 添加时间 5/20
	 */
	@Expose
	private String productPhoto;

	@Expose
	private String productName;

	/**
	 * 关注商铺列表 添加时间 5/20
	 */
	@Expose
	private String shopLogo; // 商铺关注图片
	
	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReDetails() {
		return reDetails;
	}

	public void setReDetails(String reDetails) {
		this.reDetails = reDetails;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Boolean get_new() {
		return _new;
	}

	public void set_new(Boolean _new) {
		this._new = _new;
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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProductPhoto() {
		return productPhoto;
	}

	public void setProductPhoto(String productPhoto) {
		this.productPhoto = productPhoto;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(float shippingFee) {
		this.shippingFee = shippingFee;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ListCarGoodsBean)) {
			return false;
		} else {
			ListCarGoodsBean bean = (ListCarGoodsBean) o;
			if (bean.id == id && bean.categoryId == categoryId
					&& bean.shopId == shopId && bean.productId == productId) {
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public String toString() {
		return "ListCarGoodsBean [cartItemId=" + cartItemId + ", categoryId="
				+ categoryId + ", productId=" + productId + ", name=" + name
				+ ", shopId=" + shopId + ", specs=" + specs + ", selectedSpec="
				+ selectedSpec + ", photo=" + photo + ", quantity=" + quantity
				+ ", promotionPrice=" + promotionPrice + ", reDetails="
				+ reDetails + ", originalPrice=" + originalPrice
				+ ", shippingFee=" + shippingFee + ", shippingName="
				+ shippingName + ", _new=" + _new + ", id=" + id
				+ ", shopName=" + shopName + ", logo=" + logo + ", subTitle="
				+ subTitle + ", price=" + price + ", productPhoto="
				+ productPhoto + ", productName=" + productName + ", shopLogo="
				+ shopLogo + "]";
	}
}
