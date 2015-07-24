package com.shangxian.art.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 操作员
 * 
 * @author Administrator
 *
 */
public class ShopOperatorBean implements Serializable {
	@Expose
	private String phoneNumber;
	@Expose
	private String username;
	@Expose
	private String password;
	@SerializedName("nick_name")
	@Expose
	private String nickName;
	@Expose
	private String recentLoginDate;
	@Expose
	private String photo;
	@SerializedName("scale_image")
	@Expose
	private String scaleImage;
	@Expose
	private String id;
	@Expose
	private String name;
	@Expose
	private String email;
	@Expose
	private String mobile;
	@Expose
	private String officeTel;
	@Expose
	private String cardNumber;
	@Expose
	private String payPassword;
	@Expose
	private String description;
	@Expose
	private String details;
	@Expose
	private String receiverName;
	@Expose
	private String receiverTel;
	@Expose
	private String frontPhoto;
	@Expose
	private String reversePhoto;
	@Expose
	private String shopId;
	private  String  receiveSms;// true :接收  false不接收

	
	public String getReceiveSms() {
		return receiveSms;
	}
	public void setReceiveSms(String receiveSms) {
		this.receiveSms = receiveSms;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRecentLoginDate() {
		return recentLoginDate;
	}
	public void setRecentLoginDate(String recentLoginDate) {
		this.recentLoginDate = recentLoginDate;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getScaleImage() {
		return scaleImage;
	}
	public void setScaleImage(String scaleImage) {
		this.scaleImage = scaleImage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public String getFrontPhoto() {
		return frontPhoto;
	}
	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	public String getReversePhoto() {
		return reversePhoto;
	}
	public void setReversePhoto(String reversePhoto) {
		this.reversePhoto = reversePhoto;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	@Override
	public String toString() {
		return "ShopOperatorBean [phoneNumber=" + phoneNumber + ", username="
				+ username + ", password=" + password + ", nickName="
				+ nickName + ", recentLoginDate=" + recentLoginDate
				+ ", photo=" + photo + ", scaleImage=" + scaleImage + ", id="
				+ id + ", name=" + name + ", email=" + email + ", mobile="
				+ mobile + ", officeTel=" + officeTel + ", cardNumber="
				+ cardNumber + ", payPassword=" + payPassword
				+ ", description=" + description + ", details=" + details
				+ ", receiverName=" + receiverName + ", receiverTel="
				+ receiverTel + ", frontPhoto=" + frontPhoto
				+ ", reversePhoto=" + reversePhoto + ", shopId=" + shopId
				+ ", receiveSms=" + receiveSms + "]";
	}
}
