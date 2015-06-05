package com.shangxian.art.bean;

import java.util.List;

/**
 * 首页轮播
 * @author ling
 *
 */
import com.google.gson.annotations.Expose;

public class HomeadsBeanAll{
	@Expose
	private Integer itemId;
	@Expose
	private String adViewType;
	@Expose
	private List<HomeadsBean> indexDtos;
	@Expose
	private HomeadsBean indexDto;
	

public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public String getAdViewType() {
		return adViewType;
	}


	public void setAdViewType(String adViewType) {
		this.adViewType = adViewType;
	}


	public List<HomeadsBean> getIndexDtos() {
		return indexDtos;
	}


	public void setIndexDtos(List<HomeadsBean> indexDtos) {
		this.indexDtos = indexDtos;
	}


	public HomeadsBean getIndexDto() {
		return indexDto;
	}


	public void setIndexDto(HomeadsBean indexDto) {
		this.indexDto = indexDto;
	}


@Override
	public String toString() {
		return "HomeadsBeanAll [itemId=" + itemId + ", adViewType="
				+ adViewType + ", indexDtos=" + indexDtos + ", indexDto="
				+ indexDto + "]";
	}



public class HomeadsBean {

	@Expose
	private Integer itemId;
	@Expose
	private String imageUrl;
	@Expose
	private String adAction;
	@Expose
	private String dataUrl;
	@Expose
	private Integer itemOrder;
	@Expose
	private Boolean single;
	@Expose
	private Boolean banner;
	public Boolean getBanner() {
		return banner;
	}

	public void setBanner(Boolean banner) {
		this.banner = banner;
	}
	/**
	 * 
	 * @return The itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * 
	 * @param itemId
	 *            The itemId
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * 
	 * @return The imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 
	 * @param imageUrl
	 *            The imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 
	 * @return The adAction
	 */
	public String getAdAction() {
		return adAction;
	}

	/**
	 * 
	 * @param adAction
	 *            The adAction
	 */
	public void setAdAction(String adAction) {
		this.adAction = adAction;
	}

	/**
	 * 
	 * @return The dataUrl
	 */
	public String getDataUrl() {
		return dataUrl;
	}

	/**
	 * 
	 * @param dataUrl
	 *            The dataUrl
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	/**
	 * 
	 * @return The itemOrder
	 */
	public Integer getItemOrder() {
		return itemOrder;
	}

	/**
	 * 
	 * @param itemOrder
	 *            The itemOrder
	 */
	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	/**
	 * 
	 * @return The single
	 */
	public Boolean getSingle() {
		return single;
	}

	/**
	 * 
	 * @param single
	 *            The single
	 */
	public void setSingle(Boolean single) {
		this.single = single;
	}

	@Override
	public String toString() {
		return "HomeadsBean [itemId=" + itemId + ", imageUrl=" + imageUrl
				+ ", adAction=" + adAction + ", dataUrl=" + dataUrl
				+ ", itemOrder=" + itemOrder + ", single=" + single
				+ ", banner=" + banner + "]";
	}

}
}
