package com.shangxian.art.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;

public class HttpClients {
	private static final int SUCCESS = 1;
	private static final int FAIL = 0;
	private static Context mContext;

	public static void registContext(Context context) {
		mContext = context;
	}

	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors
			.newFixedThreadPool(3);

	// post请求
	public static void postDo(final String baseUrl, final String json,
			final HttpCilentListener l) {
		final Handler postHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				int what = msg.what;
				switch (what) {
				case FAIL:
					l.onResponse(null);
					break;
				case SUCCESS:
					String res = (String) msg.obj;
					l.onResponse(res);
					break;
				}
			};
		};
//		final String user_token = ParkApplication.getPrefer().getString(
//				Constants.USER_TOKEN, null);
		final int user_token = LocalUserInfo.getInstance(mContext).getInt(
				Constant.PRE_USER_ID, Integer.MIN_VALUE);
		final HttpClient httpClient = getHttpClient();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpPost postMethod = new HttpPost(baseUrl);
					postMethod.setHeader("Content-Type", "application/json");
					if (user_token != Integer.MIN_VALUE) {
						postMethod.addHeader("user_token", user_token + "");
					}
					if(!TextUtils.isEmpty(json)){
					StringEntity se = new StringEntity(json.trim(), "UTF-8");
					postMethod.setEntity(se);
					}
					// 将参数填入POST
					// Entity中
					MyLogger.i("respone:" + postMethod.toString());
					// 执行POST方法
					HttpResponse response = httpClient.execute(postMethod);
					if (TextUtils.isEmpty(response.toString())
							|| response.getStatusLine().getStatusCode() != 200) {
						int s = response.getStatusLine().getStatusCode();
						MyLogger.i("Code():"
								+ response.getStatusLine().getStatusCode());
						if (!TextUtils.isEmpty(response.toString())) {
							MyLogger.i("respone:" + response.toString());
						}
						postHandler.sendEmptyMessage(FAIL);
						return;
					} else {
						StringBuilder builder = new StringBuilder();
						BufferedReader bufferedReader2 = new BufferedReader(
								new InputStreamReader(response.getEntity()
										.getContent()));
						String str2 = "";
						for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
								.readLine()) {
							builder.append(s);
						}
						Message message = Message.obtain(postHandler, SUCCESS,
								builder.toString());
						postHandler.sendMessage(message);
						MyLogger.d("response: ==================" + builder.toString() + "==================");
					}
				} catch (UnsupportedEncodingException e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				} catch (IOException e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				}
			}
		});
	}

	// get请求
	// public static void getDo(final String baseUrl, final String param,
	// final HttpCilentListener l) {
	// final Handler getHandler = new Handler() {
	// public void handleMessage(android.os.Message msg) {
	// int what = msg.what;
	// switch (what) {
	// case FAIL:
	// l.onResponse(null);
	// break;
	// case SUCCESS:
	// String res = (String) msg.obj;
	// l.onResponse(res);
	// break;
	// }
	// };
	// };
	// final HttpClient httpClient = getHttpClient();
	// final String user_token = ParkApplication.getPrefer().getString(
	// Constants.USER_TOKEN, null);
	// executorService.submit(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// HttpGet getMethod = new HttpGet(baseUrl + "?" + param);
	//
	// // getMethod.addHeader("user_token", user_token);
	// // getMethod.addHeader("Content-Type", "application/json");
	// HttpResponse response = httpClient.execute(getMethod);
	// if (response.getStatusLine().getStatusCode() != 200
	// || TextUtils.isEmpty(response.toString())) {
	// getHandler.sendEmptyMessage(FAIL);
	// return;
	// }
	// StringBuilder builder = new StringBuilder();
	// BufferedReader bufferedReader2 = new BufferedReader(
	// new InputStreamReader(response.getEntity()
	// .getContent()));
	// String str2 = "";
	// for (String s = bufferedReader2.readLine(); s != null; s =
	// bufferedReader2
	// .readLine()) {
	// builder.append(s);
	// }
	// Message message = Message.obtain(getHandler, SUCCESS,
	// builder.toString());
	// getHandler.sendMessage(message);
	// LogUtils.d(builder.toString());
	// } catch (ClientProtocolException e) {
	// getHandler.sendEmptyMessage(FAIL);
	// e.printStackTrace();
	// } catch (IOException e) {
	// getHandler.sendEmptyMessage(FAIL);
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	// 得到HttpClient
	public static HttpClient getHttpClient() {
		HttpParams mHttpParams = new BasicHttpParams();
		// 设置网络链接超时
		// 即:Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(mHttpParams, 20 * 1000);
		// 设置socket响应超时
		// 即:in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(mHttpParams, 20 * 1000);
		// 设置socket缓存大小
		HttpConnectionParams.setSocketBufferSize(mHttpParams, 8 * 1024);
		// 设置是否可以重定向
		HttpClientParams.setRedirecting(mHttpParams, true);

		HttpClient httpClient = new DefaultHttpClient(mHttpParams);
		return httpClient;
	}

	public interface HttpCilentListener {
		void onResponse(String res);
	}
}
