package com.shangxian.art.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.net.BaseServer.OnHttpListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultConfirmGoodsListener;
import com.shangxian.art.utils.MyLogger;

public class CommentServer extends BaseServer {
	/*
	 * 返回监听
	 */
	public interface OnHttpResultListener {
		void onHttpResult(MyOrderItem_all myOrderItemAll);
	}

	/*
	 * 返回下一页监听
	 */
	public interface OnHttpResultMoreListener {
		void onHttpResultMore(MyOrderItem_all myOrderItemAll);
	}
	/*
	 * 返回添加评论监听
	 */
	public interface OnHttpResultAddCommentListener {
		void onHttpResultAddComment(MyOrderItem_all myOrderItemAll);
	}

	

	public static void toPostMyComment(final OnHttpResultListener l) {
		toPostJson(NET_COMMENTTO, "{}", new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				// MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResult(null);
					} else {
						l.onHttpResult(getMyOrderItemAll(res));
					}
				}
			}
		});
	}


	/**
	 * 获取下一页
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toPostOrderMore(String status, String json,
			final OnHttpResultMoreListener l) {
		toPostJson(NET_ORDERS + status, json, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				// MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultMore(null);
					} else {
						l.onHttpResultMore(getMyOrderItemAll(res));
					}
				}
			}
		});
	}

	protected static MyOrderItem_all getMyOrderItemAll(String content) {
		MyOrderItem_all myOrderItem_all = null;
		try {
			Gson gson = new Gson();
			myOrderItem_all = gson.fromJson(content, MyOrderItem_all.class);
			MyLogger.i(myOrderItem_all.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myOrderItem_all;
	}

	/**
	 * 添加评论
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toAddComment(final MyOrderItem myOrderItem,
			final OnHttpResultAddCommentListener l) {
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("orderNumber", myOrderItem
				.getOrderNumber()));
		toPostWithToken(NET_COMMENTADD, pairs, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				// MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultAddComment(null);
					} else {
						l.onHttpResultAddComment(null);
					}
				}
			}
		});
	}
}
