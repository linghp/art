package com.shangxian.art.net;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.MyOrderDetailBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.bean.SellerRefoundstat;

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
		toXUtils(HttpMethod.POST, NET_SELLER_DELORDER + myOrderItem.getOrderNumber(), getParams(), null, back);
	}
	
	/**
	 * 卖家发货
	 * 
	 * @param orderId
	 * @param back
	 */
	public static void toSellerSendOrder(String orderId, CallBack back){
		toXUtils(HttpMethod.POST, NET_SENDORDER + orderId, getParams(), null, back);
	}
	
	/**
	 * 卖家订单详情
	 * 
	 * @param status
	 * @param json
	 * @param l
	 */
	public static void toGetSellerOrderDetails(String ordernumber, CallBack call) {
		Type type = new TypeToken<MyOrderDetailBean>(){}.getType();
		toXUtils(HttpMethod.POST, NET_SELLER_ORDERDETAILS + ordernumber, getParams(), type, call);
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
	
	/** *****************************************************
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
			CallBack callBack){
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<SellerRefoundstat>() {
		}.getType();
		toXUtils(HttpMethod.POST, NET_SELLER_RETURN_ORDERS + status, params, type,
				callBack);
	}
}
