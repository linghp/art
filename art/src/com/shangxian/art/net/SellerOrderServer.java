package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.bean.SellerRefoundstat;
import com.shangxian.art.utils.MyLogger;

/**
 * 卖家订单
 * 
 * @author libo
 */
public class SellerOrderServer extends BaseServer {

	/**
	 * 卖家删除订单
	 * 
	 * @param l
	 */
	public static void toDelSellerOrder(final MyOrderItem myOrderItem,
			CallBack back) {
		toXUtils(HttpMethod.POST,
				NET_SELLER_DELORDER + myOrderItem.getOrderNumber(),
				getParams(), null, back);
	}

	/**
	 * 卖家发货
	 * 
	 * @param orderId
	 * @param back
	 */
	public static void toSellerSendOrder(String orderId, CallBack back) {
		String url=NET_SENDORDER + orderId;
		MyLogger.i(url);
		toXUtils(HttpMethod.POST, url, getParams(), null,
				back);
	}

	/**
	 * 卖家订单详情
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toGetSellerOrderDetails(String ordernumber, CallBack call) {
		Type type = new TypeToken<MyOrderDetailBean>() {
		}.getType();
		String url=NET_SELLER_ORDERDETAILS + ordernumber;
		MyLogger.i(url);
		toXUtils(HttpMethod.POST, url,
				getParams(), type, call);
	}

	/**
	 * 获取卖家订单列表
	 * 
	 * @param status
	 * @param skip
	 * @param callBack
	 */
	public static void toGetSellerOrder(String status, String skip,
			CallBack callBack) {
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<MyOrderItem_all>() {
		}.getType();
		toXUtils(HttpMethod.POST, NET_SELLER_ORDERS + status, params, type,
				callBack);
	}

	/**
	 * *****************************************************
	 * 
	 * 退货
	 * 
	 * ******************************************************/

	/**
	 * 获取卖家退货订单列表
	 * 
	 * @param status
	 * @param skip
	 * @param callBack
	 */
	public static void toGetSellerReturnOrder(String status, String skip,
			CallBack callBack) {
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<SellerRefoundstat>() {
		}.getType();
		toXUtils(HttpMethod.POST, NET_SELLER_RETURN_ORDERS + status, params,
				type, callBack);
	}

	/**
	 * 卖家退货处理
	 * 
	 * @param status         操作状态
	 * @param orderNum       订单编号
	 * @param productId      商品id
	 * @param returnOrderNum 退货订单编号
	 * @param back
	 */
	public static void toSellerReturnOrderOperation(String status, String orderNum,
			String productId, String returnOrderNum, CallBack back) {
		toXUtils(HttpMethod.POST, NET_SELLERORDER_RETURN + status + "/"
				+ returnOrderNum + "/" + productId + "/" + orderNum,
				getParams(), null, back);
	}

	/**
	 * 卖家审核不通过
	 * 
	 * @param orderNum       订单编号
	 * @param productId      商品id
	 * @param returnOrderNum 退货订单编号
	 * @param back
	 */
	public static void toSellerReturnOrderFialure(String orderNum,
			String productId, String returnOrderNum,CallBack back) {
		toXUtils(HttpMethod.POST, NET_SELLERORDER_RETURN + "FAILURE/"
				+ returnOrderNum + "/" + productId + "/" + orderNum, getParams(), null, back);
	}
	
	/**
	 * 卖家拒绝签收
	 * 
	 * @param orderNum       订单编号
	 * @param productId      商品id
	 * @param returnOrderNum 退货订单编号
	 * @param back
	 */
	public static void toSellerReturnOrderCompletedRefuse(String orderNum,
			String productId, String returnOrderNum, CallBack back) {
		toXUtils(HttpMethod.POST, NET_SELLERORDER_RETURN + "COMPLETED_REFUSE/"
				+ returnOrderNum + "/" + productId + "/" + orderNum, getParams(), null, back);
	}
}
