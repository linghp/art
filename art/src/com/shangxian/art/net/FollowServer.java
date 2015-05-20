package com.shangxian.art.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.shangxian.art.bean.SearchProductInfo;

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
	public void toFollowGoods(int productId, final OnFollowListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("productId", productId + ""));
		toPostWithToken(NET_FOLLOW_PRODUCT, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
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
	public void toFollowShop(int shopId, final OnFollowListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("shopId", shopId + ""));
		toPostWithToken(NET_FOLLOW_SHOP, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				if (l != null) {
					try {
						l.onFollow(Boolean.valueOf(res));
					} catch (Exception e) {
						e.printStackTrace();
						l.onFollow(false);
					}
				}
			}
		});
	}
	
	public void toFollowGoodsList(Boolean isShop, final OnFollowInfoListener l){
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
