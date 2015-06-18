package com.shangxian.art.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.base.DataTools;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.utils.MyLogger;

/**
 * 关注相关接口
 * 
 * @author libo
 *
 */
public class FollowServer extends BaseServer {
	private boolean isSave = false;

	public FollowServer() {
		super();
	}

	public FollowServer(boolean isSave) {
		this.isSave = isSave;
	}
	
	/**
	 * 关注商品
	 * 
	 * @param productId 商品id
	 * @param l
	 */
	public void toFollowGoods(String productId, final OnFollowListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("productId", productId + ""));
		MyLogger.i("关注商品地址"+NET_FOLLOW_PRODUCT +"productId:"+productId);
		toPostWithToken(NET_FOLLOW_PRODUCT, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
						MyLogger.i("关注商品后返回的数据"+res);
						l.onFollow(Boolean.valueOf(res));
					} catch (Exception e) {
						e.printStackTrace();
						l.onFollow(false);
					}
				}
			}
		});
	}
	
	/**
	 * 关注商铺
	 * 
	 * @param shopId 商铺id
	 * @param l
	 */
	public void toFollowShop(String shopId, final OnFollowListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("shopId", shopId + ""));
		MyLogger.i("关注商铺地址"+NET_FOLLOW_SHOP+"shopId:"+shopId);
		toPostWithToken(NET_FOLLOW_SHOP, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
						MyLogger.i("关注商铺后返回的数据"+res);
						l.onFollow(Boolean.valueOf(res));
					} catch (Exception e) {
						e.printStackTrace();
						l.onFollow(false);
					}
				}
			}
		});
	}
	
	public void toDelFollowGoods(String id, Boolean isShop, final CallBack call){	
		MyLogger.i("取消商铺关注地址："+NET_FOLLOW_SHOP_DEL+"取消商品关注地址："+NET_FOLLOW_PRODUCT_DEL);
		toXUtils(HttpMethod.DELETE, (isShop ? NET_FOLLOW_SHOP_DEL : NET_FOLLOW_PRODUCT_DEL) + id, getParams(), null, call);
	}
	
	public void toFollowList(Boolean isShop, final OnFollowInfoListener l){
		MyLogger.i("获取商铺关注地址："+NET_FOLLOW_SHOP_LIST+"获取商品关注地址："+NET_FOLLOW_PRODUCT_LIST);
		toGet2(isShop ? NET_FOLLOW_SHOP_LIST : NET_FOLLOW_PRODUCT_LIST, null, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					SearchProductInfo info = gson.fromJson(res, SearchProductInfo.class);
					l.onFollowInfo(info);
				}
			}
		});
	}

	public interface OnFollowListener {
		void onFollow(boolean isFollow);
	}
	
	public interface OnFollowInfoListener{
		void onFollowInfo(SearchProductInfo info);
	}
}
