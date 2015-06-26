package com.shangxian.art.net;

import java.util.List;

import com.ab.http.AbStringHttpResponseListener;
import com.shangxian.art.bean.ShopsSummaryModel;
/**
 * 商品类
 * @author zyz
 *
 */
public class ShopsServer extends BaseServer{
	public interface OnHttpShopsSummaryListener {
		void onHttpShopsSummary(List<ShopsSummaryModel> model);
	}


	//商铺简介
	public static void toGetShopsSummary(String url,final OnHttpShopsSummaryListener l){
		
	}
}
