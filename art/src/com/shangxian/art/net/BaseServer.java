package com.shangxian.art.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.ab.http.AbBinaryHttpResponseListener;
import com.ab.http.AbFileHttpResponseListener;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.google.gson.Gson;
import com.shangxian.art.base.DataTools;
import com.shangxian.art.base.MyApplication;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.bean.UserInfo;
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
	public static final String HOST = "http://www.ainonggu666.com/api/";
	protected static final String NET_LOGIN = HOST + "user/login";// 登录接口
	protected static final String NET_ADS = HOST + "abs";// 首页广告列表
	protected static final String NET_ACCOUNT = HOST + "account";// 首页广告列表
	protected static final String NET_PAYMENT = HOST + "payment";// 直接支付
	protected static final String NET_PAY_ORDER = HOST + "pay";// 订单支付
	protected static final String NET_CAPTCHA = HOST + "captcha";// 根据电话号码获取验证码
	protected static final String NET_VALIDCAPTCHA = HOST + "valid/captcha";// 验证结果是否正确
	protected static final String NET_REGIST = HOST + "regist/buyer";// 注册
	protected static final String NET_ORDERS =HOST + "orders/";//我的订单
	protected static final String NET_CANCELORDER =HOST +  "/order/cancel/";//取消订单
	protected static final String NET_DELORDER = HOST + "/order/del/";//删除订单
	protected static final String NET_SEARCH_PRODUCT = HOST + "product"; // 搜索商品信息.
	protected static final String NET_SEARCH_SHOP = HOST + "shop"; // 搜索商品信息.
	protected static final String NET_NEW_PAYPASSWORD_SENDCODE = HOST + "send"; // 发送验证码
	protected static final String NET_NEW_PAYPASSWORD = HOST + "user/password"; //设置支付密码
	protected static final String NET_FOLLOW_PRODUCT = HOST + "aat/product";  //添加商品关注
	protected static final String NET_FOLLOW_SHOP = HOST + "aat/shop";  //添加商铺关注
	protected static final String NET_FOLLOW_PRODUCT_DEL = HOST + "aat/product/";  //删除商品关注
	protected static final String NET_FOLLOW_SHOP_DEL = HOST + "aat/shop/";  //删除商铺关注
	public static final String NET_FOLLOW_PRODUCT_LIST = HOST + "aats?type=product"; //获取产品关注列表 
	public static final String NET_FOLLOW_SHOP_LIST = HOST + "aats?type=shop"; //获取商铺关注列表

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
				System.out.println("http ======================" + "失败"
						+ "=========================");
				if (l != null) {
					l.onHttp(null);
				}
			}

			@Override
			public void onSuccess(int code, String res) {
				System.out.println("http ======================" + res
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
		System.out.println(" toPost responce >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
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
				System.out.println("toPsot -> Failure >>>>>>>>>>>>>>>>>>>  "
						+ code + "  >>>>>>>>>>>>>>>>>>> " + res);
				if (l != null) {
					l.onHttp(null);
				}
			}

			@Override
			public void onSuccess(int code, String res) {
				System.out
						.println("toPsot -> rest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
								+ res);
				if (l != null) {
					if (code != 200) {
						l.onHttp(null);
					} else {
						try {
							JSONObject json = new JSONObject(res);
							int result_code = json.getInt("result_code");
							System.out.println("result_code ================="
									+ result_code);
							if (result_code == 200) {
								String rest = json.getString("result");
								System.out.println("toPsot -> rest >>>>>>> "
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
		System.out.println(" toPost responce >>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
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
				System.out.println("toPsot -> Failure >>>>>>>>>>>>>>>>>>>  "
						+ code + "  >>>>>>>>>>>>>>>>>>> " + res);
				if (l != null) {
					l.onHttp(null);
				}
			}

			@Override
			public void onSuccess(int code, String res) {
				System.out
						.println("toPsot -> rest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
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
			System.out
					.println("toPsotJson -> --json-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
							+ json);
		}
		HttpClients.postDo(url, json, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				System.out
						.println("toPsotJson -> res >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "
								+ res);
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						System.out.println("result_code ================="
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

	protected static void toPostWithToken(String url,
			List<BasicNameValuePair> pairs, final OnHttpListener l) {
		if (pairs == null) {
			pairs = new ArrayList<BasicNameValuePair>();
		}
		HttpClients.toPost(url, pairs, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						System.out.println("result_code ================="
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
	
	protected static void toGet(String url,
			final OnHttpListener l) {
		HttpClients.getDo(url,  new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					// l.onHttp(res);
					try {
						JSONObject json = new JSONObject(res);
						int result_code = json.getInt("result_code");
						System.out.println("result_code ================="
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
					l.onHttp(res);
				}
			}
		});
	}

	protected static void toFile(String url, AbRequestParams params,
			final OnHttpListener l) {
		mAbHttpUtil.post(url, params, new AbFileHttpResponseListener() {
			@Override
			public void onStart() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onFailure(int arg0, String arg1, Throwable arg2) {

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
								.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
								.append('&');
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
						System.out.println("result_code ================="
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

	protected void toDel(String url, final OnHttpListener l){
		HttpClients.delDo(url, new HttpCilentListener() {
			@Override
			public void onResponse(String res) {
				if (l != null) {
					l.onHttp(res);
				}
			}
		});
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
