package com.shangxian.art.net;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.base.DataTools;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;

/**
 * 联网基类
 * 
 * @author libo
 * @time 2015/4/9
 */
public class BaseServer {
	/**
	 * 
	 * ----------------------------请求------------------------------
	 * 
	 */
	// public static final String HOST = "http://192.168.1.125:8888/art/api/";
	// public static final String HOSTtest=
	// "http://192.168.0.116:8888/art/api/";
	// public static final String HOST = "http://192.168.0.197:8888/art/api/";
	// public static final String HOSTtest=
	// "http://192.168.0.116:8888/art/api/";
//	 public static final String HOST = "http://192.168.0.197:8888/art/api/";
	public static final String HOST = Constant.BASEURL+Constant.CONTENT+"/";
   // public static final String HOST = "http://192.168.0.170:8888/art/api/";

	protected static final String NET_LOGIN = HOST + "user/login";// 登录接口
	protected static final String NET_ADS = HOST + "abs";// 首页广告列表
	protected static final String NET_ACCOUNT = HOST + "account";// 首页广告列表
	protected static final String NET_PAYMENT = HOST + "payment";// 直接支付
	protected static final String NET_PAY_ORDER = HOST + "pay";// 订单支付
	protected static final String NET_PAY_NOTICE= HOST + "pay/alipayNoice/";// 支付宝支付订单之前通知服务器订单号
	protected static final String NET_CAPTCHA = HOST + "captcha";// 根据电话号码获取验证码
	protected static final String NET_VALIDCAPTCHA = HOST + "valid/captcha";// 验证结果是否正确
	protected static final String NET_REGIST = HOST + "regist/buyer";// 注册
	protected static final String NET_ORDERS = HOST + "orders/";// 我的订单
	protected static final String NET_SELLER_ORDERS = HOST + "orderList/";// 卖家的订单
	protected static final String NET_SELLER_RETURN_ORDERS = HOST + "orderReturn/";// 卖家的退款订单
	protected static final String NET_CANCELORDER = HOST + "order/cancel/";// 取消订单
	protected static final String NET_SENDORDER = HOST + "sendOrder/";// 卖家发货
	protected static final String NET_SELLERORDER_RETURN = HOST + "orderSellerReturn/"; //卖家退货处理
	protected static final String NET_DELORDER = HOST + "order/del/";// 删除订单
	protected static final String NET_SELLER_DELORDER = HOST + "deleteSellerOrder/";// 卖家删除订单
	protected static final String NET_CONFIRMGOODS = HOST + "order/completed/";// 确认收货
	protected static final String NET_ORDERDETAILS = HOST + "order/details?orderNumber=";// 订单详情 
	protected static final String NET_SELLER_ORDERDETAILS = HOST + "sellerorderdails/";// 订单详情 
	protected static final String NET_REFUND = HOST + "orderBuyerReturn/";// 退款/退货申请
	protected static final String NET_FILTER = HOST+"product/find";//分类筛选
	protected static final String NET_COMMENTTO = HOST+"orderEvaluateList";//去获取我的评论
	protected static final String NET_COMMENTADD = HOST+"saveProductComment";//去评论
	protected static final String NET_COMMENTCOUNT = HOST+"getLevelAll?productId=";//获取商品的评论数量个数的统计
	protected static final String NET_SHOPQR = HOST+"shop/code/";//获取商家二维码
	protected static final String NET_ADDSHOPOPERATOR = HOST+"createShopOperator";//添加操作员
	protected static final String NET_UPDATESHOPOPERATOR = HOST+"updateUser";//修改操作员
	protected static final String NET_DELETESHOPOPERATOR = HOST+"deleteUser?userId=";//删除操作员
	protected static final String NET_FINDSHOPOPERATOR = HOST+"findByShop";//获取操作员列表

	protected static final String NET_SEARCH_PRODUCT = HOST + "product"; // 搜索商品信息.
	protected static final String NET_SEARCH_SHOP = HOST + "shop"; // 搜索商品信息.
	protected static final String NET_NEW_PAYPASSWORD_SENDCODE = HOST
			+ "user/password/captcha"; // 发送验证码
	protected static final String NET_LOGIN_PASSWORD_SENDCODE = HOST + "send"; // 发送支付密码验证码
	protected static final String NET_NEW_PAYPASSWORD = HOST
			+ "user/payPassword?action=new"; // 设置支付密码
	protected static final String NET_NEW_LOGIN_PASSWORD = HOST
			+ "user/password"; // 设置新的登录密码
	protected static final String NET_UPDATA_LOGIN_PASSWORD = HOST
			+ "user/updatePassword"; // 修改新的登录密码
	protected static final String NET_UPDATA_PAYPASSWORD = HOST
			+ "user/password"; // 修改新的登录密码

	protected static final String NET_FOLLOW_PRODUCT = HOST + "aat/product"; // 添加商品关注
	protected static final String NET_FOLLOW_SHOP = HOST + "aat/shop"; // 添加商铺关注
	protected static final String NET_FOLLOW_PRODUCT_DEL = HOST
			+ "aat/product/"; // 删除商品关注
	protected static final String NET_FOLLOW_SHOP_DEL = HOST + "aat/shop/"; // 删除商铺关注
	public static final String NET_FOLLOW_PRODUCT_LIST = HOST
			+ "aats?type=product"; // 获取产品关注列表
	public static final String NET_FOLLOW_SHOP_LIST = HOST + "aats?type=shop"; // 获取商铺关注列表

	protected static final String NET_UPLOAD_IMG = HOST + "user/uploadPhoto"; // 上传图片
	protected static final String NET_SOGO_REGIST_CODE = HOST + "captcha/"; // 商铺入驻验证码
	protected static final String NET_SOGO_REGIST = HOST + "regist/shop"; // 商铺入驻
	protected static final String NET_NEARLY = HOST + "nearbyGeosearch";//

	public static final String NET_MYORDER_BACK_LIST = HOST
			+ "orderReturnList/";// 通过状态获取退货订单
	protected static final String NET_CANCEL_REFUND = HOST
			+ "orderCancelReturnList/"; // 取消退货订单
	
	protected static final String NET_BUYER_RETURN_LIST = HOST + "orderReturnList/";
	protected static final String NET_BUYER_DELETE_REUTRN_ORDER = HOST + "deleteBuyerOrder/"; //买家删除订单
	protected static final String NET_BUYER_CANCEL_RETURN_ORDER = HOST + "orderCancelReturnList/"; //买家删除订单
	protected static final String NET_BUYER_GET_EXPRESS = HOST + "order/distribution"; //获取快递信息
	protected static final String NET_BUYER_RETURN_EXPRESS = HOST + "orderBuyerReturn/"; //上传退货信息
	/**
	 * 账户与安全  设置  
	 */
//	protected static final String NET_DELIVERYADDRESS = HOST + "receiving";//收货地址管理
	protected static final String NET_DELETEADDRESS = HOST + "receiving/delte";//删除收货地址
	protected static final String NET_DEFAULTADDRESS = HOST + "receiving/default";//设置默认收货地址
	
	
	protected static final String NET_BENQIJIESUAN = HOST + "lhb/timeSettlementEveryOne/";//本期结算
	protected static final String NET_DINGSHIJIESUAN = HOST + "lhb/timeSettlementAll";//定时结算
	/**
	 * 
	 * ----------------------------------------------------------------
	 * 
	 */

	private static AbHttpUtil mAbHttpUtil = null;
	private static Context mContext;
	protected static Gson gson = new Gson();
	protected static UserInfo curUser;
	protected static DataTools tools = DataTools.newInstance();

	public static void toRegistContext(Context mContext) {
		BaseServer.mContext = mContext;
		mAbHttpUtil = AbHttpUtil.getInstance(mContext);
		curUser = LocalUserInfo.getInstance(mContext).getUser();
	}

	public static RequestParams getParams() {
		RequestParams params = new RequestParams();
		curUser = LocalUserInfo.getInstance(mContext).getUser();
		MyLogger.i(curUser.toString());
		//params.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		if (curUser != null && !curUser.isNull()) {	
			params.addHeader("User-Token", curUser.getId() + "");
			MyLogger.i(curUser.getId() + "");
			return params;
		} else {
			return params;
		}
	}
	
//	protected static RequestParams getParams() {
//		RequestParams params = new RequestParams();
//		params.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
//		if (curUser != null && !curUser.isNull()) {	
//			params.addHeader("User-Token", /*curUser.getId() + ""*/"2");
//			return params;
//		} else {
//			return params;
//		}
//	}
	
	protected static RequestParams getJsonParams() {
		RequestParams params = new RequestParams();
		params.setContentType("application/json;charset=UTF-8");
		if (curUser != null && !curUser.isNull()) {	
			params.addHeader("User-Token", curUser.getId() + "");
			return params;
		} else {
			return params;
		}
	}

	protected static void toGet(String url, AbRequestParams params,
			final OnHttpListener l) {
		mAbHttpUtil.get(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onFailure(int code, String res, Throwable t) {
				MyLogger.i("http ======================" + "失败"
						+ "=========================");
				if (l != null) {
					l.onHttp(null);
				}
			}

			@Override
			public void onSuccess(int code, String res) {
				MyLogger.i("http ======================" + res
						+ "=========================");
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							if (result_code == 200) {
								l.onHttp(json.getString("result"));
							} else {
								l.onHttp(null);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							l.onHttp(null);
						}
						// l.onHttp(res);
					}
				}
			}
		});
	}
	protected static void toGet_ab_noparams(String url,
			final OnHttpListener l) {
		mAbHttpUtil.get(url, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {
			}
			
			@Override
			public void onFinish() {
			}
			
			@Override
			public void onFailure(int code, String res, Throwable t) {
				MyLogger.i("http ======================" + "失败"
						+ "=========================");
				if (l != null) {
					l.onHttp(null);
				}
			}
			
			@Override
			public void onSuccess(int code, String res) {
				MyLogger.i("http ======================" + res
						+ "=========================");
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							if (result_code == 200) {
								l.onHttp(json.getString("result"));
							} else {
								l.onHttp(null);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							l.onHttp(null);
						}
						// l.onHttp(res);
					}
				}
			}
		});
	}

	protected static void toPost(String url, AbRequestParams params,
			final OnHttpListener l) {
		MyLogger.i(" toPost responce >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
				+ url);
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onFailure(int code, String res, Throwable t) {
				MyLogger.i("toPsot -> Failure >>>>>>>>>>>>>>>>>>>  "
						+ code + "  >>>>>>>>>>>>>>>>>>> " + res);
				if (l != null) {
					l.onHttp(null);
				}
			}
           
			@Override
			public void onSuccess(int code, String res) {
				MyLogger.i("toPsot -> rest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
								+ res);
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							MyLogger.i("result_code ================="
									+ result_code);
							if (result_code == 200) {
								String rest = json.getString("result");
								MyLogger.i("toPsot -> rest >>>>>>> "
										+ rest);
								l.onHttp(rest);
							} else {
								l.onHttp(null);
							}
						} catch (JSONException e) {
							e.printStackTrace();
							l.onHttp(null);
						}
					}
				}
			}
		});
	}

	/**
	 * 原生的数据，不要解析
	 * 
	 * @param url
	 * @param params
	 * @param l
	 */
	protected static void toPost2(String url, AbRequestParams params,
			final OnHttpListener l) {
		MyLogger.i(" toPost responce >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
				+ url);
		mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				MyLogger.i("onFinish");
			}

			@Override
			public void onFailure(int code, String res, Throwable t) {
				MyLogger.i("toPsot -> Failure >>>>>>>>>>>>>>>>>>>  "
						+ code + "  >>>>>>>>>>>>>>>>>>> " + res);
				if (l != null) {
					l.onHttp(null);
				}
			}

			@Override
			public void onSuccess(int code, String res) {
				MyLogger.i("toPsot -> rest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
								+ res);
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						l.onHttp(res);
					}
				}
			}
		});
	}

	protected static void toPostJson(String url, String json,
			final OnHttpListener l) {
		if (json == null) {
			json = "";
		} else {
			MyLogger.i("toPsotJson -> --json-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
							+ json);
		}
		HttpClients.postDo(url, json, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				MyLogger.i("toPsotJson -> res >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
								+ res);
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						MyLogger.i("result_code ================="
								+ result_code);
						if (result_code == 200) {
							l.onHttp(json.getString("result"));
						} else {
							l.onHttp(null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						l.onHttp(null);
					}
				}
			}
		});
	}
	
	/**
	 * 原生的数据，不要解析
	 */
	protected static void toPostJson2(String url, String json,
			final OnHttpListener l) {
		if (json == null) {
			json = "";
		} else {
			MyLogger.i("toPsotJson -> --json-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
					+ json);
		}
		HttpClients.postDo(url, json, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				MyLogger.i("toPsotJson -> res >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
						+ res);
				if (l != null) {
					 l.onHttp(res);
				}
			}
		});
	}

	protected static void toPostWithToken(String url,
			List<BasicNameValuePair> pairs, final OnHttpListener l) {
		if (pairs == null) {
			pairs = new ArrayList<BasicNameValuePair>();
		}
		HttpClients.toPost(url, pairs, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				MyLogger.i(res);
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						MyLogger.i("result_code ================="
								+ result_code);
						if (result_code == 200) {
							l.onHttp(json.getString("result"));
						} else {
							l.onHttp(null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						l.onHttp(null);
					}
				}
			}
		});
	}

	protected static void toGet(String url, final OnHttpListener l) {
		HttpClients.getDo(url, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						MyLogger.i("result_code ================="
								+ result_code);
						if (result_code == 200) {
							l.onHttp(json.getString("result"));
						} else {
							l.onHttp(null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						l.onHttp(null);
					}
				}
			}
		});
	}
	/**
	 * 原生的数据，不要解析
	 */
	protected static void toGet_token_original(String url, final OnHttpListener l) {
		HttpClients.getDo(url, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
				if(!TextUtils.isEmpty(res)){
							l.onHttp(res);
				}else{
						l.onHttp(null);
				}
				}
			}
		});
	}
	/**
	 * 原生的数据，不要解析
	 */
	protected static void toPostWithToken2(String url,
			List<BasicNameValuePair> pairs, final OnHttpListener l) {
		if (pairs == null) {
			pairs = new ArrayList<BasicNameValuePair>();
		}
		HttpClients.toPost(url, pairs, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					// l.onHttp(res);
					MyLogger.i(res);
					l.onHttp(res);
				}
			}
		});
	}

	protected static void toGet2(String uri, Map<String, String> map,
			final OnHttpListener l) {
		StringBuilder sb = new StringBuilder(uri);
		// 如果参数不为空
		if (map != null && map.isEmpty()) {
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					// Post方式提交参数的话，不能省略内容类型与长度
					try {
						sb.append(entry.getKey())
								.append('=')
								.append(URLEncoder.encode(entry.getValue(),
										"UTF-8")).append('&');
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		HttpClients.getDo(sb.toString(), new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						MyLogger.i("result_code ================="
								+ result_code);
						if (result_code == 200) {
							l.onHttp(json.getString("result"));
						} else {
							l.onHttp(null);
						}
					} catch (Exception e) {
						e.printStackTrace();
						l.onHttp(null);
					}
				}
			}
		});
	}

	public static final int ERROR_JSON_EX = 0x00001011;
	public static final int ERROR_GSON2ENTITY_EX = 0x00001012;
	public static final int ERROR_CONN_EX = 0x00001013;

	public static void toXUtils(final HttpMethod method, final String url,
			RequestParams params, final Type type, final CallBack call) {
		if (call == null)
			return;
		new HttpUtils().send(method, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException e, String msg) {
						e.printStackTrace();
						MyLogger.i(msg);
						call.onSimpleFailure(ERROR_CONN_EX);
					}

					@Override
					public void onStart() {
						super.onStart();
						call.onStart();
					}

					@Override
					public void onCancelled() {
						super.onCancelled();
						call.onCancelled();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						call.onLoading(total, current, isUploading);
					}

					@Override
					public void onSuccess(ResponseInfo res) {
						String result = String.valueOf(res.result);
						MyLogger.i(result);
						JSONObject json;
						int result_code = Integer.MIN_VALUE;
						try {
							json = new JSONObject(result);
							result_code = json.getInt("result_code");
							if (result_code == 200) {
								String re = json.getString("result");
								MyLogger.i(re);
								MyLogger.i("xUtils -------> code == "
										+ result_code + " , result == "
										+ result);
								if (type != null && !TextUtils.isEmpty(re)) {
									try {
										Object o = gson.fromJson(re, type);
										if (o == null) {
											call.onSimpleFailure(ERROR_GSON2ENTITY_EX);
										} else {
											call.onSimpleSuccess(o);
										}
									} catch (Exception e) {
										e.printStackTrace();
										call.onSimpleFailure(ERROR_JSON_EX);
									}	
								} else {
									call.onSimpleSuccess(re);
								}
							}else if(result_code == 400){
								String re = json.getString("result");
									call.onDetailFailure(re);
							} else {
								call.onSimpleFailure(result_code);
							}
						} catch (JSONException e) {
							call.onSimpleFailure(ERROR_JSON_EX);
							e.printStackTrace();
						}
					}
				});
	}
	/**
	 * 原生的数据不需要解析json
	 * @param method
	 * @param url
	 * @param params
	 * @param type
	 * @param call
	 */
	public static void toXUtils2(final HttpMethod method, final String url,
			RequestParams params, final CallBack call) {
		if (call == null)
			return;
		new HttpUtils().send(method, url, params,
				new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException e, String msg) {
				e.printStackTrace();
				MyLogger.i(msg);
				call.onSimpleFailure(ERROR_CONN_EX);
			}
			
			@Override
			public void onStart() {
				super.onStart();
				call.onStart();
			}
			
			@Override
			public void onCancelled() {
				super.onCancelled();
				call.onCancelled();
			}
			
			@Override
			public void onLoading(long total, long current,
					boolean isUploading) {
				super.onLoading(total, current, isUploading);
				call.onLoading(total, current, isUploading);
			}
			
			@Override
			public void onSuccess(ResponseInfo res) {
				String result = String.valueOf(res.result);
				MyLogger.i(result);
				if(TextUtils.isEmpty(result)){
					call.onSimpleFailure(ERROR_CONN_EX);
				}else{
					call.onSimpleSuccess(result);
				}
			}
		});
	}

	protected String getRes(String res) {
		if (TextUtils.isEmpty(res))
			throw new NullPointerException();
		try {
			JSONObject json = new JSONObject(res);
			if (json.getInt("result_code") == 200) {
				res = json.getString("result");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}

	protected HttpUtils getHttpUtils() {
		return new HttpUtils();
	}

	protected interface OnHttpListener {
		void onHttp(String res);
	}

	/*
	 * 登录返回监听
	 */
	public interface OnLoginListener {
		void onLogin(UserInfo info);
	}

	/*
	 * 获取爱农币、爱农元返回监听
	 */
	public interface OnAccountSumListener {
		void onAccountSum(AccountSumInfo info);
	}

	public interface OnPaymentListener {
		void onPayment(String res);
	}

	public interface OnPayListener {
		void onPayment(boolean res);
	}
}
