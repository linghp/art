package com.shangxian.art.bean;

import java.util.ArrayList;

import com.ab.model.AbResult;

/**
 * 首页数据集合bean
 * @author Administrator
 *
 */
public class HomeData extends AbResult{
	private ArrayList<String> imgList;
	private ArrayList<String> tipsList;
	private ArrayList<GoodBean> goods;
	
	public ArrayList<String> getImgList() {
		return imgList;
	}
	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}
	public ArrayList<String> getTipsList() {
		return tipsList;
	}
	public void setTipsList(ArrayList<String> tipsList) {
		this.tipsList = tipsList;
	}
	public ArrayList<GoodBean> getGoods() {
		return goods;
	}
	public void setGoods(ArrayList<GoodBean> goods) {
		this.goods = goods;
	}
}
