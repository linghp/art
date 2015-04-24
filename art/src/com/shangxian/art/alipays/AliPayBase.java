package com.shangxian.art.alipays;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class AliPayBase {
	// 商户PID
	protected static final String PARTNER = "2088811640351834";
	// 商户收款账号
	protected static final String SELLER = "cqtrsy@163.com";
	// 商户私钥
	public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMxa+FLDYTzVLP7nqJUWYfh8MfNVogsebyK0+1IZuptEs3Oi3QZeqPC5QXA864dZZWhCNBx5um5PjiW0UXxl+aLc+l6oXLm1tqL71Zw8pbKX+xjuCtUaiV7xjl1v5wCMZuawJhWizjBiv4V4cYjtI4lGUoauVgSMPabks48igk9NAgMBAAECgYEAki+ZaIs/6iaNU6QgotYqK3mcuffOPan90pQpHOgu55fTz6r39fCNZfcAauwJiP9YXabOZHhn9zWN+Ebv689/Lc0r490626qYgKWDdxMaPCaoBvCaPEFyFbpj7UrqBPQ8zeqln41gEdZVTQ4/c7W1qFkdv+HE2EFBaxrmegYnbQECQQDloPV6C1OnCkr+jsYkjy+5FnQkD9NS/GTEZexQDe0p3XOexK/qCfrwD7jcwIdW5Uis2mK2q3evG+NZgl8u+7jhAkEA49L6aeYxYJGzVTOL6lJXObCNdU2kSUeiGHyAudlncVYTeqPRaJusmCPAUESarrtJdpCss5HQYIkbjfqyfKoH7QJBANLAEKxreuJDYyMQ/LAPLpisD/oNAEIY3Y8XkHTE41daJUShdmbRtBriAyOwHEbXdwUWBiVraBQx/05mhl+DrmECQQCK+WY8N2mjiP2mWb1eAUCoNmT0S5qOAR6GZVx5An+xiVp0k33onB4a6KQl0tOxBxp3MyXGnIpKeRjP8T/0ABy5AkEAx9BduKKGtJNL+r0h+VHC9prQ6c9AJQqkcW7/PE7j2sR6DF/293bZ8CyzNUdfVIuvKXjHZ0GR5oHzURH3faaNTw==";
	// 商户公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPu5Z2W3PEqcPE+nMEpuHM9Pc0T7rq4gLQsmsg Gx7k7Bc49Bdsk3Ux3Ryk68QsOOI5lygMEKl4LBQeFiyhLVxb2pbZblmtW9Fci2Z2oEK9I3UyB7uu fymeH4T3ow4wq/Il/Ym+T6P0Af7QuLtfXcVSbaDzSkLu7RodkGcLHfj0PQIDAQAB";
	
	protected static final int SDK_PAY_FLAG = 0x00000001;
	protected static final int SDK_CHECK_FLAG = 0x000000002;
	
	protected static Context mContext;
	
	/**
	 * 只能在BaseActivity中初始化Context,不然在下面会报错
	 * 
	 * @param context
	 */
	public static void initContext(Context context){
		mContext = context;
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				
				String resultStatus = payResult.getResultStatus();

				Toast.makeText(mContext, resultInfo + " ********* " +resultStatus ,
						Toast.LENGTH_SHORT).show();
				
				System.out.println(" handler + ><><><><><><><><>< " + resultInfo + " ********* " +resultStatus);
				
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mContext, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mContext, "支付结果确认中",
								Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mContext, "支付失败",
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				/*Toast.makeText(mContext, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();*/
				break;
			}
			default:
				break;
			}
		};
	};
	
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(String order) {
	
		//TODO: ---------------------------------------------------------------------------------------------------------------------------------
		
		// 订单
		//String orderInfo = AliPayTools.createAliPayOrder("oahndbaosdbfoabsdfbaobsdfb", "小馒头", "小小小小鸟", "0.01").toSign();

		// 对订单做RSA 签名
		String sign = SignUtils.sign(order, RSA_PRIVATE);
		
		System.out.println("sigo<><><><><><><><><><><><>" + sign);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			Toast.makeText(mContext, "输入错误", Toast.LENGTH_LONG);
		}

		System.out.println("sigo 222 <><><><><><><><><><><><>" + sign);
		
		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = order + "&sign=\"" + sign + "\"&"
				+ getSignType();

		System.out.println("order <><><><><><><><><><><><>" + payInfo);
		
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask((Activity) mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	/**
	 * 获取签名方式
	 * 
	 * @return
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check() {
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
				mHandler.sendMessage(msg);
			}
		};
		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask((Activity) mContext);
		String version = payTask.getVersion();
		//Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}
}
