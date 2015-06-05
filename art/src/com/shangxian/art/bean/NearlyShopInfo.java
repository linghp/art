package com.shangxian.art.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 附近商铺
 * @author libo
 *
 */
public class NearlyShopInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private String title;
	
	@Expose
	private List<String> location = new ArrayList<String>();
	
	@Expose
	private String city;
	
	@SerializedName("create_time")
	@Expose
	private String createTime;
	
	@SerializedName("geotable_id")
	@Expose
	private String geotableId;
	
	@Expose
	private String address;
	
	@Expose
	private String tags;
	
	@Expose
	private String province;
	
	@Expose
	private String serviceDesc;
	
	@Expose
	private String officeTel;
	
	@Expose
	private String mobile;
	
	@Expose
	private String shopName;
	
	@Expose
	private Integer uid;
	
	@Expose
	private String id;
	
	@SerializedName("coord_type")
	@Expose
	private String coordType;
	
	@Expose
	private String type;
	
	@Expose
	private String distance;
	
	@Expose
	private String weight;
	
	@Expose
	private String shopId;
	
	@Expose
	private String indexLogo;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取经度
	 * @return
	 */
	public double getLat(){
		try {
			return Double.valueOf(location.get(1));
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 获取纬度
	 * @return
	 */
	public double getLng(){
		try {
			return Double.valueOf(location.get(0));
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 获取经纬度
	 * @return
	 */
	public MyLatLng getLatLng(){
		MyLatLng lat = new MyLatLng(Double.valueOf(location.get(1)), Double.valueOf(location.get(0)));
		return lat;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGeotableId() {
		return geotableId;
	}

	public void setGeotableId(String geotableId) {
		this.geotableId = geotableId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoordType() {
		return coordType;
	}

	public void setCoordType(String coordType) {
		this.coordType = coordType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getIndexLogo() {
		return indexLogo;
	}

	public void setIndexLogo(String indexLogo) {
		this.indexLogo = indexLogo;
	}

	@Override
	public String toString() {
		return "NearlyShopInfo [title=" + title + ", location=" + location
				+ ", city=" + city + ", createTime=" + createTime
				+ ", geotableId=" + geotableId + ", address=" + address
				+ ", tags=" + tags + ", province=" + province
				+ ", serviceDesc=" + serviceDesc + ", officeTel=" + officeTel
				+ ", mobile=" + mobile + ", shopName=" + shopName + ", uid="
				+ uid + ", id=" + id + ", coordType=" + coordType + ", type="
				+ type + ", distance=" + distance + ", weight=" + weight
				+ ", shopId=" + shopId + ", indexLogo=" + indexLogo + "]";
	}	
}
