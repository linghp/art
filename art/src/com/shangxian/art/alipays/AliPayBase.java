package com.shangxian.art.alipays;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AliPayBase {
	// 商户PID
	protected static final String PARTNER = "2088811640351834";

	// 商户收款账号
	protected static final String SELLER = "cqtrsy@163.com";

	// 商户私钥
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKClyOm2B"
			+ "6LY0cODd825s48r4Ng4UMnzwxzcvR8XIMjQep/l/8js1fvq74e09+SQzYFvPoW5Wl5hJFgO7Hk3aHKaDb4RWVgMI"
			+ "1HeS8hYzl4sg7flPQ9+Gyisn7PSNFYHSM74x1I7MyPb2qEwdxF0Zqo+r+gaFrxIC8Vr0jXg/2A9AgMBAAECgYAkM"
			+ "LTuUZ+uxAGddgSLSAZ3fn8MtsMce9m8qs3AL0mnc6/sTbgt34zlT6veeppN14E0Y4dGQNdm46N4YwRK2nyg2TEwY"
			+ "A84A/4YPF4zAwTv0flvaxWD6IzeBWzSnKPcfB8WF9dhJMzRBFmuXCkbeVwqE9B6Zl1AU/mc1SOjkpPA0QJBANTZ1"
			+ "mGNethF1k7ryQax9c0kBjdn6r5vz0sN6SEQ/nqPnKXx4DnF25lv505iNaJnSXnR7OcSVGX7xa3bisiA/4cCQQDBN"
			+ "sp3I1K1Y53rO2MbCqwVsaakYjwn5psF979KuRDxk8Y2lF7cFA52zf/nFaDBAtsMH2qtcKB1NNBkahFKcWsbAkBB2"
			+ "whmdHS+t5TgJDm1dZg8mVnHCMLrmwKDEVHBJlXmK8rm2RhsJ5iTN7lUWjSIA/cK1vW6ZMRyLVzzNssQ0EC/AkEAmE"
			+ "RVyw5LYCfhjECBGg6PD4mxilrgjZFvGoTkfD7c24VZ8Cl9mHM18urlgXfJ+FIHzsXzjDv2OGgm0vvEJKa9gQJAeqe"
			+ "eV67pqXe1vMR2d/2PcrtwzRWsABgoezgG+UrIAd/Qgux3Z0iZgcwJYwIUMLBO98UQZgYVbocRXdQHrRsp9A==";

//	// 商户公钥
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgpcjptgei2NHDg3fNub"
//			+ "OPK+DYOFDJ88Mc3L0fFyDI0Hqf5f/I7NX76u+HtPfkkM2Bbz6FuVpeYSRYDux5N2hymg2+EVlYDCNR3kvIWM5eLI"
//			+ "O35T0PfhsorJ+z0jRWB0jO+MdSOzMj29qhMHcRdGaqPq/oGha8SAvFa9I14P9gPQIDAQAB";

	protected static final int SDK_PAY_FLAG = 0x00000001;
	protected static final int SDK_CHECK_FLAG = 0x000000002;

	protected static Context mContext;

	/**
	 * 只能在BaseActivity中初始化Context,不然在下面会报错
	 * 
	 * @param context
	 */
	public static void initContext(Context context) {
		mContext = context;
	}

	/**
	 * 及时支付，及时到账 ()
	 * 
	 * @param order 订单
	 * @param l
	 */
	protected static void pay(String order, final OnPayListener l) {
		if (l != null) {
			final Handler payhandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg != null) {
						PayResult payResult = new PayResult((String) msg.obj);
						// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
						String resultInfo = payResult.getResult();
						String resultStatus = payResult.getResultStatus();
						// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
						if (TextUtils.equals(resultStatus, "9000")) {
							l.onSuccess(resultInfo);
						} else {
							// 判断resultStatus 为非“9000”则代表可能支付失败
							// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
							if (TextUtils.equals(resultStatus, "8000")) {
								l.on8000();
							} else {
								l.onFailed("支付失败");
							}
						}
					} else {
						l.onFailed("支付失败");
					}
				}
			};
			// 对订单做RSA 签名
			String sign = SignUtils.sign(order, RSA_PRIVATE);

			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = order + "&sign=\"" + sign + "\"&"
					+ getSignType();
			Runnable payRunnable = new Runnable() {
				@Override
				public void run() {
					try {
						// 构造PayTask 对象
						PayTask alipay = new PayTask((Activity) mContext);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo);
						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						Log.i("***************** Pay *******************",
								result);
						payhandler.sendMessage(msg);
					} catch (Exception e) {
						payhandler.sendMessage(null);
					}
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	}

	/**
	 * 获取签名方式
	 * 
	 * @return
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 * @param l
	 */
	public static void check(final OnCheckListener l) {
		final Handler checkHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int waht = msg.what;
				if (waht == SDK_CHECK_FLAG && l != null) {
					l.onCheck((boolean) msg.obj);
				}
			}
		};
		Runnable checkRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask((Activity) mContext);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();
				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				checkHandler.sendMessage(msg);
			}
		};
		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}

	/**
	 * get the sdk version. 
	 * 获取SDK版本号
	 */
	public String getSDKVersion() {
		PayTask payTask = new PayTask((Activity) mContext);
		String version = payTask.getVersion();
		// Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
		return version;
	}

	public interface OnCheckListener {
		void onCheck(boolean isCheck);
	}

	public interface OnPayListener {
		void onSuccess(String res);

		void onFailed(String msg);

		void on8000();
	}
}
