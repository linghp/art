package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 *  立即购买去结算时上传的json实体
 * @author ling
 * 
 */
public class NowBuySettlementBean implements Serializable{
	@Expose
	private Product product;
	@Expose
	private String addressId;
	@Expose
	private String guestMessage;
	
	
	
	public String getAddressId() {
		return addressId;
	}



	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public String getGuestMessage() {
		return guestMessage;
	}



	public void setGuestMessage(String guestMessage) {
		this.guestMessage = guestMessage;
	}



	@Override
	public String toString() {
		return "NowBuySettlementBean [product=" + product + ", addressId="
				+ addressId + ", guestMessage=" + guestMessage + "]";
	}



	public static class Product {

		@Expose
		private String productId;
		@Expose
		private Map<String, String> specs;
		@Expose
		private Integer buyCount;
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public Map<String, String> getSpecs() {
			return specs;
		}
		public void setSpecs(Map<String, String> specs) {
			this.specs = specs;
		}
		public Integer getBuyCount() {
			return buyCount;
		}
		public void setBuyCount(Integer buyCount) {
			this.buyCount = buyCount;
		}
		@Override
		public String toString() {
			return "Product [productId=" + productId + ", specs=" + specs
					+ ", buyCount=" + buyCount + "]";
		}
		
	}
}
