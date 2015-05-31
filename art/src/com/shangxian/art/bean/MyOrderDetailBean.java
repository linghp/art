package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 我的订单
 * 
 * @author Administrator
 *
 */
public class MyOrderDetailBean implements Serializable {
	@Expose
	private Integer orderId;
	@Expose
	private String orderNumber;
	@Expose
	private String orderedDate;
	@Expose
	private Object tradeDate;
	@Expose
	private String payType;
	@Expose
	private Integer buyerId;
	@Expose
	private String buyerName;
	@Expose
	private Integer sellerId;
	@Expose
	private String sellerName;
	@Expose
	private String status;
	@Expose
	private Integer totalPrice;
	@Expose
	private Integer shippingFee;
	@Expose
	private Boolean hasPostage;
	@Expose
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	@Expose
	private String guestMessage;
	@Expose
	private ReceiverInfo receiverInfo;
	@Expose
	private Object returnCause;
	@Expose
	private Integer returnPrice;

	
	
	
public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}

	public Object getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Object tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(Integer shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Boolean getHasPostage() {
		return hasPostage;
	}

	public void setHasPostage(Boolean hasPostage) {
		this.hasPostage = hasPostage;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getGuestMessage() {
		return guestMessage;
	}

	public void setGuestMessage(String guestMessage) {
		this.guestMessage = guestMessage;
	}

	public ReceiverInfo getReceiverInfo() {
		return receiverInfo;
	}

	public void setReceiverInfo(ReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}

	public Object getReturnCause() {
		return returnCause;
	}

	public void setReturnCause(Object returnCause) {
		this.returnCause = returnCause;
	}

	public Integer getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(Integer returnPrice) {
		this.returnPrice = returnPrice;
	}

	@Override
	public String toString() {
		return "MyOrderDetailBean [orderId=" + orderId + ", orderNumber="
				+ orderNumber + ", orderedDate=" + orderedDate + ", tradeDate="
				+ tradeDate + ", payType=" + payType + ", buyerId=" + buyerId
				+ ", buyerName=" + buyerName + ", sellerId=" + sellerId
				+ ", sellerName=" + sellerName + ", status=" + status
				+ ", totalPrice=" + totalPrice + ", shippingFee=" + shippingFee
				+ ", hasPostage=" + hasPostage + ", orderItems=" + orderItems
				+ ", guestMessage=" + guestMessage + ", receiverInfo="
				+ receiverInfo + ", returnCause=" + returnCause
				+ ", returnPrice=" + returnPrice + "]";
	}

	
	
	/*---------------------------------以下为内部类，供上面解析所用----------------------------------------*/	
	public class ReceiverInfo {

		@Expose
		private Integer id;
		@Expose
		private String deliveryAddress;
		@Expose
		private String receiverName;
		@Expose
		private String receiverTel;
		@SerializedName("default")
		@Expose
		private Boolean _default;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDeliveryAddress() {
			return deliveryAddress;
		}
		public void setDeliveryAddress(String deliveryAddress) {
			this.deliveryAddress = deliveryAddress;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getReceiverTel() {
			return receiverTel;
		}
		public void setReceiverTel(String receiverTel) {
			this.receiverTel = receiverTel;
		}
		public Boolean get_default() {
			return _default;
		}
		public void set_default(Boolean _default) {
			this._default = _default;
		}
		@Override
		public String toString() {
			return "ReceiverInfo [id=" + id + ", deliveryAddress="
					+ deliveryAddress + ", receiverName=" + receiverName
					+ ", receiverTel=" + receiverTel + ", _default=" + _default
					+ "]";
		}
		
	}
	
	public class OrderItem {

		@Expose
		private Integer id;
		@Expose
		private Object specs;
		@Expose
		private Integer quantity;
		@Expose
		private String name;
		@Expose
		private Integer price;
		@Expose
		private String productSacle;
		private Integer productSourcePrice;
		private Integer unitPrice;
		private Integer discount;
		private String orderItemStatus;
		public Integer getProductSourcePrice() {
			return productSourcePrice;
		}
		public void setProductSourcePrice(Integer productSourcePrice) {
			this.productSourcePrice = productSourcePrice;
		}
		public Integer getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(Integer unitPrice) {
			this.unitPrice = unitPrice;
		}
		public Integer getDiscount() {
			return discount;
		}
		public void setDiscount(Integer discount) {
			this.discount = discount;
		}
		public String getOrderItemStatus() {
			return orderItemStatus;
		}
		public void setOrderItemStatus(String orderItemStatus) {
			this.orderItemStatus = orderItemStatus;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Object getSpecs() {
			return specs;
		}
		public void setSpecs(Object specs) {
			this.specs = specs;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getPrice() {
			return price;
		}
		public void setPrice(Integer price) {
			this.price = price;
		}
		public String getProductSacle() {
			return productSacle;
		}
		public void setProductSacle(String productSacle) {
			this.productSacle = productSacle;
		}
		@Override
		public String toString() {
			return "OrderItem [id=" + id + ", specs=" + specs + ", quantity="
					+ quantity + ", name=" + name + ", price=" + price
					+ ", productSacle=" + productSacle
					+ ", productSourcePrice=" + productSourcePrice
					+ ", unitPrice=" + unitPrice + ", discount=" + discount
					+ ", orderItemStatus=" + orderItemStatus + "]";
		}
		
		}
}

