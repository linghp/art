package com.shangxian.art.net;

import java.util.List;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.SearchsActivity.UiModel;
import com.shangxian.art.bean.CommentLevelAllBean;
import com.shangxian.art.bean.CommonBeanObject;
import com.shangxian.art.bean.GoodsCommentBean;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.bean.SearchProductInfo;
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
	/*
	 * 返回获取商品评论监听
	 */
	public interface OnHttpResultGetCommentListener {
		void onHttpResultGetComment(List<GoodsCommentBean> goodsCommentBeans);
	}
	/*
	 * 返回获取商品评论监听
	 */
	public interface OnHttpResultGetCommentLevelAllListener {
		void onHttpResultGetCommentLevelAll(CommentLevelAllBean commentLevelAllBean);
	}

	
/**
 * 获取待评论和已评论
 * @param l
 */
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
				CommonBeanObject commonBean=null;
				try {
					commonBean=gson.fromJson(arg0.result, CommonBeanObject.class);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(commonBean!=null&&commonBean.getResult_code().equals("200")){
					l.onHttpResultAddComment(true);
				}else{
					l.onHttpResultAddComment(false);
				}
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
	
	/**
	 * 获取商品评论
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toGetComment(String url_part,String productid,
			final OnHttpResultGetCommentListener l) {
		String url=HOST+url_part+"?productId="+productid;
		MyLogger.i(url);
		toXUtils(HttpMethod.GET, url, null, new TypeToken<List<GoodsCommentBean>>(){}.getType(), new CallBack() {	
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					List<GoodsCommentBean> goodsCommentBeans = (List<GoodsCommentBean>) res;
					if (goodsCommentBeans!=null) {
						MyLogger.i(goodsCommentBeans.toString());
						l.onHttpResultGetComment(goodsCommentBeans);
					}else {
						l.onHttpResultGetComment(null);
					}
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				l.onHttpResultGetComment(null);
			}
		});
	}
	/**
	 * 获取评论的好评 中评 差评的数量
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toGetCommentLevelAll(String productid,
			final OnHttpResultGetCommentLevelAllListener l) {
		String url=HOST+"getLevelAll"+"?productId="+productid;
		MyLogger.i(url);
		toXUtils(HttpMethod.GET, url, null, new TypeToken<CommentLevelAllBean>(){}.getType(), new CallBack() {	
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					CommentLevelAllBean commentLevelAllBean = (CommentLevelAllBean) res;
					if (commentLevelAllBean!=null) {
						MyLogger.i(commentLevelAllBean.toString());
						l.onHttpResultGetCommentLevelAll(commentLevelAllBean);
					}else {
						l.onHttpResultGetCommentLevelAll(null);
					}
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				l.onHttpResultGetCommentLevelAll(null);
			}
		});
	}
}
