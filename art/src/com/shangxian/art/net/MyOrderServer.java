package com.shangxian.art.net;

import android.text.TextUtils;

import com.ab.http.AbRequestParams;
import com.google.gson.Gson;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.utils.MyLogger;

public class MyOrderServer extends BaseServer {
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
	 * 返回订单详情监听
	 */
	public interface OnHttpResultOrderDetailsListener {
		void onHttpResultOrderDetails(MyOrderDetailBean myOrderDetailBean);
	}
	/*
	 * 返回取消订单监听
	 */
	public interface OnHttpResultCancelOrderListener {
		void onHttpResultCancelOrder(MyOrderItem myOrderItem);
	}
	/*
	 * 返回删除订单监听
	 */
	public interface OnHttpResultDelOrderListener {
		void onHttpResultDelOrder(MyOrderItem myOrderItem);
	}
	/*
	 * 返回退款申请监听
	 */
	public interface OnHttpResultRefundListener {
		void onHttpResultRefund(CommonBean<Object> commonBean);
	}

	public static void toGetOrder(String status, final OnHttpResultListener l) {
		toPostJson(NET_ORDERS + status, "{}", new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
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
	public static void toGetOrderMore(String status, String json,
			final OnHttpResultMoreListener l) {
		toPostJson(NET_ORDERS + status, json, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
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
	 * 订单详情
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toGetOrderDetails(String ordernumber,
			final OnHttpResultOrderDetailsListener l) {
		toGet(NET_ORDERDETAILS + ordernumber, new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultOrderDetails(null);
					} else {
						l.onHttpResultOrderDetails(getMyOrderOrderDetails(res));
					}
				}
			}

			private MyOrderDetailBean getMyOrderOrderDetails(String res) {
				MyOrderDetailBean myOrderDetailBean=null;
				try {
					Gson gson = new Gson();
					myOrderDetailBean = gson.fromJson(res, MyOrderDetailBean.class);
					MyLogger.i(myOrderDetailBean.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return myOrderDetailBean;
			}
		});
	}
	/**
	 * 取消订单
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toCancelOrder(final MyOrderItem myOrderItem,
			final OnHttpResultCancelOrderListener l) {
		toGet(NET_CANCELORDER + myOrderItem.getOrderNumber(), new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultCancelOrder(null);
					} else {
						l.onHttpResultCancelOrder(getMyOrderItem(myOrderItem,res));
					}
				}
			}
		});
	}
	/**
	 * 删除订单
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toDelOrder(final MyOrderItem myOrderItem,
			final OnHttpResultDelOrderListener l) {
		toGet(NET_DELORDER + myOrderItem.getOrderNumber(), new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultDelOrder(null);
					} else {
						l.onHttpResultDelOrder(getMyOrderItem(myOrderItem,res));
					}
				}
			}
		});
	}
	
	private static MyOrderItem getMyOrderItem(MyOrderItem myOrderItem,String content) {
		MyOrderItem myOrderItem2 = null;
		try {
			Gson gson = new Gson();
			myOrderItem2 = gson.fromJson(content, MyOrderItem.class);
			MyLogger.i(myOrderItem2.toString());
			myOrderItem.setStatus(myOrderItem2.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myOrderItem2;
	}
	
	/**
	 *退款申请
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toRequestRefund(String productid,String orderNumber,String totalPrice,String returnReason,String buyerMessege,
			final OnHttpResultRefundListener l) {
		AbRequestParams params = new AbRequestParams();
		params.put("isGoods", "false");
		params.put("totalPrice", totalPrice);
		params.put("returnReason", returnReason);
		params.put("buyerMessege", buyerMessege);
		toPost2(NET_REFUND+productid+"/"+ orderNumber, params,new OnHttpListener() {
			@Override
			public void onHttp(String res) {
				//MyLogger.i(res);
				if (l != null) {
					if (TextUtils.isEmpty(res)) {
						l.onHttpResultRefund(null);
					} else {
						l.onHttpResultRefund(getCommonBean(res));
					}
				}
			}
		});
	}

	protected static CommonBean<Object> getCommonBean(String res) {
		CommonBean<Object> commonBean = null;
		try {
			Gson gson = new Gson();
			commonBean = gson.fromJson(res, CommonBean.class);
			MyLogger.i(commonBean.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commonBean;
	}
}
