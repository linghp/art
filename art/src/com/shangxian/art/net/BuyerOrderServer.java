package com.shangxian.art.net;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.BuyerReturnOrderStat;
import com.shangxian.art.bean.ExpressInfo;

/**
 * 买家联网操作
 * 
 * @author libo
 */
public class BuyerOrderServer extends BaseServer {
	/**
	 * 获取买家退货列表
	 * 
	 * @param status
	 * @param skip
	 * @param back
	 */
	public void toBuyerReturnList(String status, String skip, CallBack back) {
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<BuyerReturnOrderStat>() {
		}.getType();
		toXUtils(HttpMethod.POST, NET_BUYER_RETURN_LIST + status, params, type,
				back);
	}

	/**
	 * 买家删除退货订单列表
	 * 
	 * @param orderId
	 * @param call
	 */
	public void toBuyerDeleteReturnOrder(String returnOrderNum, CallBack call) {
		RequestParams params = getParams();
		toXUtils(HttpMethod.POST, NET_BUYER_DELETE_REUTRN_ORDER
				+ returnOrderNum, params, null, call);
	}

	/**
	 * 买家取消退货订单
	 * 
	 * @param productId
	 * @param returnOrderNum
	 * @param call
	 */
	public void toBuyerCancelReturnOrder(String productId,
			String returnOrderNum, CallBack call) {
		toXUtils(HttpMethod.POST, NET_BUYER_CANCEL_RETURN_ORDER + productId
				+ "/" + returnOrderNum, getParams(), null, call);
	}

	/**
	 * 买家获取快递信息
	 * 
	 * @param call
	 */
	public void toBuyerGetExpress(CallBack call) {
		Type type = new TypeToken<List<ExpressInfo>>() {
		}.getType();
		toXUtils(HttpMethod.POST, NET_BUYER_GET_EXPRESS, getParams(), type,
				call);
	}

	/**
	 * 买家退货物流信息
	 * 
	 * @param productId
	 * @param orderNum
	 * @param shippingName
	 * @param shippingNum
	 * @param call
	 */
	public void toBuyerReturnOrderExpress(String productId, String orderNum,
			String shippingName, String shippingNum, CallBack call) {
		RequestParams params = getParams();
		params.addBodyParameter("shippingName", shippingName);
		params.addBodyParameter("shippingNum", shippingNum);
		toXUtils(HttpMethod.POST, NET_BUYER_RETURN_EXPRESS + productId + "/"
				+ orderNum, params, null, call);
	}
}
