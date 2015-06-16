package com.shangxian.art.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.shangxian.art.bean.MyOrderItem_all;
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
		void onHttpResultAddComment(Boolean issuccess);
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
	public static void toAddComment(final RequestParams params,
			final OnHttpResultAddCommentListener l) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NET_COMMENTADD, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				arg0.printStackTrace();
				MyLogger.i(arg1);
				l.onHttpResultAddComment(false);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				MyLogger.i(arg0.result);
				l.onHttpResultAddComment(true);
			}
		});
//		toPostWithToken(NET_COMMENTADD, pairs, new OnHttpListener() {
//			@Override
//			public void onHttp(String res) {
//				// MyLogger.i(res);
//				if (l != null) {
//					if (TextUtils.isEmpty(res)) {
//						l.onHttpResultAddComment(null);
//					} else {
//						l.onHttpResultAddComment(null);
//					}
//				}
//			}
//		});
	}
}
