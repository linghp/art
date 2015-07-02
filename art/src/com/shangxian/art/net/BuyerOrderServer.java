package com.shangxian.art.net;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.BuyerReturnOrderInfo;
import com.shangxian.art.bean.BuyerReturnOrderStat;
import com.shangxian.art.bean.ExpressInfo;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;

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
	public synchronized void toBuyerReturnList(String status, String skip, CallBack back) {
		RequestParams params = getParams();
		params.addBodyParameter("skip", skip);
		params.addBodyParameter("pageSize", "10");
		Type type = new TypeToken<BuyerReturnOrderStat>() {
		}.getType();
		MyLogger.i("退货订单请求地址："+NET_BUYER_RETURN_LIST + status);
		toXUtils(HttpMethod.POST, NET_BUYER_RETURN_LIST + status, params, type,
				back);
		/*List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		pairs.add(new BasicNameValuePair("skip", skip));
		pairs.add(new BasicNameValuePair("pageSize", "10"));
		toPostWithToken2(NET_BUYER_RETURN_LIST + status, pairs, new OnHttpListener() {
			
			@Override
			public void onHttp(String res) {
				MyLogger.i("退货订单请求数据："+res);
				
			}
		});*/
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
	 * @param shippingid
	 * @param call
	 */
	public static void toBuyerReturnOrderExpress(String productId, String orderNum,
			String shippingName, String shippingNum, String shippingId, CallBack call) {
		/*RequestParams params = getParams();
		params.addBodyParameter("shippingName", shippingName);
		params.addBodyParameter("shippingNum", shippingNum);
		params.addBodyParameter("shippingId", shippingId);
		MyLogger.i("买家退货物流url："+NET_BUYER_RETURN_EXPRESS + productId + "/"+ orderNum);
		MyLogger.i("买家退货物流信息url"+NET_BUYER_RETURN_EXPRESS + productId+"快递公司："+shippingName+"运单编号："+shippingNum);
		toXUtils(HttpMethod.POST, NET_BUYER_RETURN_EXPRESS + productId + "/"
				+ orderNum, params, null, call);*/
		BuyerReturnOrderInfo model = new BuyerReturnOrderInfo();
		model.setShippingName(shippingName);
		model.setShippingNum(shippingNum);
		MyLogger.i("买家退货物流信息传入数据"+model.toString());
		Gson gson = new Gson();
		String json = gson.toJson(model);
		/*HttpClients.postDo(NET_BUYER_RETURN_EXPRESS + productId + "/"
				+ orderNum, json, new HttpCilentListener() {
					
					@Override
					public void onResponse(String res) {
						MyLogger.i(""+);
						
					}
				});*/
		RequestParams params = getJsonParams();
		try {
			params.setBodyEntity(new StringEntity(json, "UTF_8"));
			toXUtils(HttpMethod.POST, NET_BUYER_RETURN_EXPRESS + productId + "/"
					+ orderNum, params, null, call);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
