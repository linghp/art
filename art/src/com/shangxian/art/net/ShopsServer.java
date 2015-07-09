package com.shangxian.art.net;

import java.util.List;

import com.ab.http.AbStringHttpResponseListener;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.ShengQingJieSuanModel;
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

	public interface OnHttpShengQingJieSuanListener {
		void onHttpShengQingJieSuan(String res);
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
	//定时结算
	public static void toDingShiJieSuan(String shopid, CallBack call){
		RequestParams params = getParams();
		MyLogger.i("定时结算url：" + NET_DINGSHIJIESUAN+shopid);
		toXUtils(HttpMethod.POST, NET_DINGSHIJIESUAN+shopid, params, null, call);
	}
	//紧急结算
	public static void toJinJiJieSuan(CallBack call){
		RequestParams params = getParams();
		MyLogger.i("紧急结算url：" + NET_JINJIJIESUAN);
		toXUtils(HttpMethod.POST, NET_JINJIJIESUAN, params, null, call);
	}
	//撤销结算
	public static void toCheXiaoJieSuan(String id, CallBack call){
		RequestParams params = getParams();
		MyLogger.i("撤销结算url：" + NET_CHEXIAOJIESUAN+id);
		toXUtils(HttpMethod.POST, NET_CHEXIAOJIESUAN+id, params, null, call);
	}
	//申请结算
	public static void toShenQingJieSuan(String date,String name,String phone, String price, String content, final OnHttpShengQingJieSuanListener l){
		/*RequestParams params = getParams();
		toXUtils(HttpMethod.POST, NET_CHEXIAOJIESUAN+date, params, null, call);*/
		MyLogger.i("申请结算url：" + NET_SHENGQINGJIESUAN+date);
		ShengQingJieSuanModel shengQingModel = new ShengQingJieSuanModel();
		shengQingModel.setPersoname(name);
		shengQingModel.setPhoneNum(phone);
		shengQingModel.setAmount(price);
		shengQingModel.setRemarks(content);
		Gson gson = new Gson();
		String json = gson.toJson(shengQingModel);
		System.out.println("申请结算url：" + NET_SHENGQINGJIESUAN+date+"传入json数据："+json);
		toPostJson(NET_CHEXIAOJIESUAN+date, json, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i(">>>>>>>>>>申请结算数据："+res);
				System.out.println(">>>>>>>>>>申请结算数据："+res);
				if (res != null) {
					l.onHttpShengQingJieSuan("申请成功");
				}else {
					l.onHttpShengQingJieSuan("申请失败");
				}
			}
		});
	}
}
