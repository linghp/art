package com.shangxian.art.net;

import java.util.List;

import com.ab.http.AbStringHttpResponseListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.ShopsSummaryModel;
import com.shangxian.art.utils.MyLogger;
/**
 * 商品类
 * @author zyz
 *
 */
public class ShopsServer extends BaseServer{
	public interface OnHttpShopsSummaryListener {
		void onHttpShopsSummary(List<ShopsSummaryModel> model);
	}

	public interface OnHttpJinJiJieSuanListener {
		void onHttpShopsSummary(List<ShopsSummaryModel> model);
	}

	//商铺简介
	public static void toGetShopsSummary(String url,final OnHttpShopsSummaryListener l){
		
	}
	
	//紧急结算
	public static void toJinJiJieSuan(String url, final OnHttpShopsSummaryListener l){
		
	}
	//本期结算
	public static void toBenQiJieSuan(String shopid, CallBack call){
		RequestParams params = getParams();
		MyLogger.i("本期结算url：" + NET_BENQIJIESUAN+shopid);
		toXUtils(HttpMethod.POST, NET_BENQIJIESUAN+shopid, params, null, call);
	}
}
