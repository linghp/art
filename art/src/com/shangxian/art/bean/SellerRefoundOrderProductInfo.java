package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

/**
 * 卖家退货订单商品详情
 * 
 * @author libo
 *
 */
public class SellerRefoundOrderProductInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6819951857503128614L;

	@Expose
	private Integer id;
	@Expose
	private Integer quantity;
	@Expose
	private String name;
	@Expose
	private String productSacle;
	@Expose
	private String remark;
	@Expose
	private Integer returnSum;
	@Expose
	private Integer productPrice;
	@Expose
	private Integer nowPrice;
	@Expose
	private Integer discount;
	@Expose
	private Integer subtotal;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getProductSacle() {
		return productSacle;
	}

	public void setProductSacle(String productSacle) {
		this.productSacle = productSacle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getReturnSum() {
		return returnSum;
	}

	public void setReturnSum(Integer returnSum) {
		this.returnSum = returnSum;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(Integer nowPrice) {
		this.nowPrice = nowPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "SellerRefoundOrderProductInfo [id=" + id + ", quantity="
				+ quantity + ", name=" + name + ", productSacle="
				+ productSacle + ", remark=" + remark + ", returnSum="
				+ returnSum + ", productPrice=" + productPrice + ", nowPrice="
				+ nowPrice + ", discount=" + discount + ", subtotal="
				+ subtotal + "]";
	}

}
