package com.shangxian.art.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

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
			.newFixedThreadPool(20);

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
		MyLogger.i("URL:" + baseUrl + "         JSON:" + json);
		// final String user_token = ParkApplication.getPrefer().getString(
		// Constants.USER_TOKEN, null);
		final int user_token = LocalUserInfo.getInstance(mContext).getInt(
				Constant.PRE_USER_ID, Integer.MIN_VALUE);
		final HttpClient httpClient = getHttpClient();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpPost postMethod = new HttpPost(baseUrl);
					postMethod.setHeader("Content-Type",
							"application/json;charset=UTF-8");
					if (user_token != Integer.MIN_VALUE) {
						postMethod.addHeader("User-Token", user_token + "");
						//postMethod.addHeader("User-Token", 2 + "");
						MyLogger.i("user-token--" + user_token);
					}
					if (!TextUtils.isEmpty(json)) {
						StringEntity se = new StringEntity(json.trim(), "UTF-8");
						postMethod.setEntity(se);
					}
					// 将参数填入POST
					// Entity中
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
						MyLogger.d("response: =================="
								+ builder.toString() + "==================");
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
				}catch (Exception e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				}
			}
		});
	}

	// get请求
	public static void getDo(final String baseUrl, final HttpCilentListener l) {
		final Handler postHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				int what = msg.what;
				switch (what) {
				case FAIL:
					l.onResponse(null);
					break;
				case SUCCESS:
					String res = (String) msg.obj;
					MyLogger.i(res);
					l.onResponse(res);
					break;
				}
			};
		};
		// final String user_token = ParkApplication.getPrefer().getString(
		// Constants.USER_TOKEN, null);
		final int user_token = LocalUserInfo.getInstance(mContext).getInt(
				Constant.PRE_USER_ID, Integer.MIN_VALUE);
		final HttpClient httpClient = getHttpClient();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpGet postMethod = new HttpGet(baseUrl);
					postMethod.setHeader("Content-Type",
							"application/json;charset=UTF-8");
					if (user_token != Integer.MIN_VALUE) {
						postMethod.addHeader("User-Token", user_token + "");
//						postMethod.addHeader("User-Token", 2 + "");
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
						MyLogger.d("response: =================="
								+ builder.toString() + "==================");
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
				}catch (Exception e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				}
			}
		});
	}

	// get请求
	public static void delDo(final String baseUrl, final HttpCilentListener l) {
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
		// final String user_token = ParkApplication.getPrefer().getString(
		// Constants.USER_TOKEN, null);
		final int user_token = LocalUserInfo.getInstance(mContext).getInt(
				Constant.PRE_USER_ID, Integer.MIN_VALUE);
		final HttpClient httpClient = getHttpClient();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpDelete delete = new HttpDelete(baseUrl);
					// HttpGet postMethod = new HttpGet(baseUrl);
					delete.setHeader("Content-Type",
							"application/json;charset=UTF-8");
					if (user_token != Integer.MIN_VALUE) {
						delete.addHeader("User-Token", user_token + "");
					}
					// 将参数填入POST
					// Entity中
					MyLogger.i("respone:" + delete.toString());
					// 执行POST方法
					HttpResponse response = httpClient.execute(delete);
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
						MyLogger.d("response: =================="
								+ builder.toString() + "==================");
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
				}catch (Exception e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				}
			}
		});
	}

	// post请求
	public static void toPost(final String baseUrl,
			final List<BasicNameValuePair> pairs, final HttpCilentListener l) {
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
		// final String user_token = ParkApplication.getPrefer().getString(
		// Constants.USER_TOKEN, null);
		final int user_token = LocalUserInfo.getInstance(mContext).getInt(
				Constant.PRE_USER_ID, Integer.MIN_VALUE);
		final HttpClient httpClient = getHttpClient();
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					HttpPost postMethod = new HttpPost(baseUrl);
					// postMethod.setHeader("Content-Type", "application/json");
//					postMethod.setHeader("Content-Type",
//							"application/json;charset=UTF-8");
					if (user_token != Integer.MIN_VALUE) {
						postMethod.addHeader("User-Token", user_token + "");
					}
					/*  StringEntity se = new StringEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
					  postMethod.setEntity(se);
					 */
					postMethod.setEntity(new UrlEncodedFormEntity(pairs,
							"utf-8"));
					// 将参数填入POST
					// Entity中
					// 执行POST方法
					HttpResponse response = httpClient.execute(postMethod);
					MyLogger.i("respone:" + response);
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
						MyLogger.d("response: =================="
								+ builder.toString() + "==================");
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
				}catch (Exception e) {
					postHandler.sendEmptyMessage(FAIL);
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// post请求
		public static void toPost1(final String baseUrl,
				final List<BasicNameValuePair> pairs, final HttpCilentListener l) {
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
			// final String user_token = ParkApplication.getPrefer().getString(
			// Constants.USER_TOKEN, null);
			final int user_token = LocalUserInfo.getInstance(mContext).getInt(
					Constant.PRE_USER_ID, Integer.MIN_VALUE);
			final HttpClient httpClient = getHttpClient();
//			httpClient.getParams().setParameter(Http, "UTF-8");
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						HttpPost postMethod = new HttpPost(baseUrl);
//						 postMethod.setHeader("Content-Type", "charset=UTF-8");
						if (user_token != Integer.MIN_VALUE) {
							postMethod.addHeader("User-Token", user_token + "");
						}
						/*  StringEntity se = new StringEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
						  postMethod.setEntity(se);
						 */
						postMethod.setEntity(new UrlEncodedFormEntity(pairs,
								"UTF-8"));
						// 将参数填入POST
						// Entity中
						// 执行POST方法
						HttpResponse response = httpClient.execute(postMethod);
						MyLogger.i("respone:" + response);
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
							MyLogger.d("response: =================="
									+ builder.toString() + "==================");
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
					}catch (Exception e) {
						postHandler.sendEmptyMessage(FAIL);
						e.printStackTrace();
					}
				}
			});
		}
	// 得到HttpClient
	public static HttpClient getHttpClient() {
		HttpParams mHttpParams = new BasicHttpParams();
//		HttpProtocolParams.setContentCharset(mHttpParams, "utf-8");
		//HttpProtocolParams.setContentCharset(mHttpParams, "utf-8");
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
