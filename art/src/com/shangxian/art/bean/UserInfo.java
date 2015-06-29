package com.shangxian.art.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author Administrator
 *
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int _id;
	private int id = Integer.MIN_VALUE;
	private int loginType = Integer.MIN_VALUE;// 1,买家；2，卖家
	private String nickName;
	private String phoneNumber;
	private String scalePhoto;// 头像路径
	private String shopId;
	private boolean payed;
    public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public UserInfo() {
		super();
	}


	public UserInfo(int id, int loginType, String nickName, String phoneNumber,
			String scalePhoto, String shopId, boolean payed) {
		super();
		this.id = id;
		this.loginType = loginType;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.scalePhoto = scalePhoto;
		this.shopId = shopId;
		this.payed = payed;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getScalePhoto() {
		return scalePhoto;
	}

	public void setScalePhoto(String scalePhoto) {
		this.scalePhoto = scalePhoto;
	}
	@Override
	public String toString() {
		return "UserInfo [_id=" + _id + ", id=" + id + ", loginType="
				+ loginType + ", nickName=" + nickName + ", phoneNumber="
				+ phoneNumber + ", scalePhoto=" + scalePhoto + ", shopId="
				+ shopId + ", payed=" + payed + "]";
	}
	public boolean isNull(){
		return id == Integer.MIN_VALUE || loginType == Integer.MIN_VALUE;
	}
}
