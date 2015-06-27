package com.shangxian.art.net;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.CommonBeanObject;
import com.shangxian.art.bean.ShopOperatorBean;

public class ShopOperatorServer extends BaseServer {
	/*
	 * 返回添加操作员监听
	 */
	public interface OnHttpResultAddOperatorListener {
		void onHttpResultAddOperator(String str);
	}
	/*
	 * 返回删除操作员监听
	 */
	public interface OnHttpResultDeleteOperatorListener {
		void onHttpResultDeleteOperator(CommonBean commonBean);
	}

	/*
	 * 返回获取操作员列表监听
	 */
	public interface OnHttpResultGetOperatorListener {
		void onHttpResultGetOperator(CommonBeanObject<List<ShopOperatorBean>> commonBeanObject);
	}

	/**
	 * 添加操作员
	 * 
	 * @param params
	 * @param l
	 */
	public static void onAddOperator_xutils(RequestParams params,
			final OnHttpResultAddOperatorListener l) {
		// RequestParams params = getParams();
		toXUtils2(HttpMethod.POST, NET_ADDSHOPOPERATOR, params, new CallBack() {

			@Override
			public void onSimpleSuccess(Object res) {
				JSONObject jsonObject;
				String resultcode = "";
				try {
					jsonObject = new JSONObject(res.toString());
					resultcode = jsonObject.getString("result_code");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (resultcode.equals("200")) {
					l.onHttpResultAddOperator("添加成功");
					return;
				} else {
					CommonBean commonBean = null;
					try {
						commonBean = gson.fromJson(res.toString(),
								CommonBean.class);
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (commonBean != null) {
						if (commonBean.getResult_code().equals("400")) {
							l.onHttpResultAddOperator(commonBean.getResult());
							return;
						}
					}
				}
				l.onHttpResultAddOperator("添加失败");
			}

			@Override
			public void onSimpleFailure(int code) {
				// TODO Auto-generated method stub
				l.onHttpResultAddOperator("添加失败");
			}
		});
	}
	/**
	 * 修改操作员
	 * 
	 * @param params
	 * @param l
	 */
	public static void onUpdateOperator_xutils(RequestParams params,
			final OnHttpResultAddOperatorListener l) {
		// RequestParams params = getParams();
		toXUtils2(HttpMethod.POST, NET_UPDATESHOPOPERATOR, params, new CallBack() {
			
			@Override
			public void onSimpleSuccess(Object res) {
				JSONObject jsonObject;
				String resultcode = "";
				try {
					jsonObject = new JSONObject(res.toString());
					resultcode = jsonObject.getString("result_code");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (resultcode.equals("200")) {
					l.onHttpResultAddOperator("修改成功");
					return;
				} else {
					CommonBean commonBean = null;
					try {
						commonBean = gson.fromJson(res.toString(),
								CommonBean.class);
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (commonBean != null) {
						if (commonBean.getResult_code().equals("400")) {
							l.onHttpResultAddOperator(commonBean.getResult());
							return;
						}
					}
				}
				l.onHttpResultAddOperator("修改失败");
			}
			
			@Override
			public void onSimpleFailure(int code) {
				// TODO Auto-generated method stub
				l.onHttpResultAddOperator("修改失败");
			}
		});
	}

	/**
	 * 获取操作员列表
	 * 
	 * @param l
	 */
	public static void onGetOperator_xutils(
			final OnHttpResultGetOperatorListener l) {
		RequestParams params = getParams();
		toXUtils2(HttpMethod.POST, NET_FINDSHOPOPERATOR, params,
				new CallBack() {

					@Override
					public void onSimpleSuccess(Object res) {
						CommonBeanObject<List<ShopOperatorBean>> commonBeanObject = null;
						try {
							commonBeanObject = gson.fromJson(
									res.toString(),
									new TypeToken<CommonBeanObject<List<ShopOperatorBean>>>() {
									}.getType());
						} catch (JsonSyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (commonBeanObject != null) {
							l.onHttpResultGetOperator(commonBeanObject);
						} else {
							l.onHttpResultGetOperator(null);
						}
					}

					@Override
					public void onSimpleFailure(int code) {
						// TODO Auto-generated method stub
						l.onHttpResultGetOperator(null);
					}
				});
	}
	/**
	 * 删除操作员
	 * 
	 * @param l
	 */
	public static void onDeleteOperator_xutils(String userid,
			final OnHttpResultDeleteOperatorListener l) {
		RequestParams params = getParams();
		toXUtils2(HttpMethod.POST, NET_DELETESHOPOPERATOR+userid, params,
				new CallBack() {
			
			@Override
			public void onSimpleSuccess(Object res) {
				CommonBean commonBean = null;
				try {
					commonBean = gson.fromJson(
							res.toString(),
							CommonBean.class);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (commonBean != null) {
					l.onHttpResultDeleteOperator(commonBean);
				} else {
					l.onHttpResultDeleteOperator(null);
				}
			}
			
			@Override
			public void onSimpleFailure(int code) {
				// TODO Auto-generated method stub
				l.onHttpResultDeleteOperator(null);
			}
		});
	}

	// public static void onAddOperator(List<BasicNameValuePair> pairs,final
	// OnHttpResultAddOperatorListener l) {
	// toPostWithToken2(NET_ADDSHOPOPERATOR, pairs, new OnHttpListener() {
	// @Override
	// public void onHttp(String res) {
	// // MyLogger.i(res);
	// if (l != null) {
	// if (!TextUtils.isEmpty(res)) {
	// CommonBean commonBean=null;
	// try {
	// commonBean=gson.fromJson(res.toString(), CommonBean.class);
	// } catch (JsonSyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if(commonBean!=null){
	// if(commonBean.getResult_code().equals("200")){
	// l.onHttpResultAddOperator("添加成功");
	// return;
	// }else if(commonBean.getResult_code().equals("400")){
	// l.onHttpResultAddOperator(commonBean.getResult());
	// }
	// }
	// l.onHttpResultAddOperator("添加失败");
	// } else {
	// l.onHttpResultAddOperator("添加失败");
	// }
	// }
	// }
	// });
	// }

}
