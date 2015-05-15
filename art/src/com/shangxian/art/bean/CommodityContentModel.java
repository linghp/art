package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommodityContentModel implements Serializable{
	@Expose
	private Integer id;
	@Expose
	private String productNumber;
	@Expose
	private String name;
	@Expose
	private Integer originalPrice;
	@Expose
	private Integer promotionPrice;
	@Expose
	private Integer categoryId;
	@Expose
	private String categoryName;
	@Expose
	private HashMap<String, List<String>> specs;
	@Expose
	private Integer sellerId;
	@Expose
	private String sellerName;
	@Expose
	private Integer shopId;
	@Expose
	private String shopName;
	@Expose
	private String shopLogo;
	@Expose
	private String shopPhoneNumber;
	@Expose
	private Integer inventories;
	@Expose
	private HashMap<String, List<String>> productOptions;
	@Expose
	private String details;
	@Expose
	private String shopAddress;
	@Expose
	private List<String> photos ;
	@Expose
	private Integer evaluateScore;
	@SerializedName("new")
	@Expose
	private Boolean _new;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Integer getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(Integer promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public HashMap<String, List<String>> getSpecs() {
		return specs;
	}
	public void setSpecs(HashMap<String, List<String>> specs) {
		this.specs = specs;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getShopPhoneNumber() {
		return shopPhoneNumber;
	}
	public void setShopPhoneNumber(String shopPhoneNumber) {
		this.shopPhoneNumber = shopPhoneNumber;
	}
	public Integer getInventories() {
		return inventories;
	}
	public void setInventories(Integer inventories) {
		this.inventories = inventories;
	}
	public HashMap<String, List<String>> getProductOptions() {
		return productOptions;
	}
	public void setProductOptions(HashMap<String, List<String>> productOptions) {
		this.productOptions = productOptions;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	public Integer getEvaluateScore() {
		return evaluateScore;
	}
	public void setEvaluateScore(Integer evaluateScore) {
		this.evaluateScore = evaluateScore;
	}
	public Boolean get_new() {
		return _new;
	}
	public void set_new(Boolean _new) {
		this._new = _new;
	}
	@Override
	public String toString() {
		return "CommodityContentModel [id=" + id + ", productNumber="
				+ productNumber + ", name=" + name + ", originalPrice="
				+ originalPrice + ", promotionPrice=" + promotionPrice
				+ ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", specs=" + specs + ", sellerId=" + sellerId
				+ ", sellerName=" + sellerName + ", shopId=" + shopId
				+ ", shopName=" + shopName + ", shopLogo=" + shopLogo
				+ ", shopPhoneNumber=" + shopPhoneNumber + ", inventories="
				+ inventories + ", productOptions=" + productOptions
				+ ", details=" + details + ", shopAddress=" + shopAddress
				+ ", photos=" + photos + ", evaluateScore=" + evaluateScore
				+ ", _new=" + _new + "]";
	}
	
}
