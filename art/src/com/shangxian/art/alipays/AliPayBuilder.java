package com.shangxian.art.alipays;

/**
 * 支付宝数据封装类
 * 
 * @author libo
 * @time 2015/4/23
 */
public class AliPayBuilder {
	private String partner = "partner=" + "\"" + "2088811640351834" + "\"";      // 签约合作者身份ID(PID)
	private String seller_id = "&seller_id=" + "\"" + "cqtrsy@163.com" + "\"";   // 签约卖家支付宝账号

	private String out_trade_no;                                                 // 商户网站唯一订单号
	private String subject;                                                      // 商品名称
	private String body;                                                         // 商品详情
	private String total_fee;                                                    // 商品金额

	private String notify_url = "&notify_url=" + "\""
			+ "http://test.peoit.com/api/login" + "\"";                          // 服务器异步通知页面路径
	private String service = "&service=\"mobile.securitypay.pay\"";              // 服务接口名称， 固定值
	private String payment_type = "&payment_type=\"1\"";                         // 支付类型， 固定值
	private String _input_charset = "&_input_charset=\"utf-8\"";                 // 参数编码， 固定值

	// 设置未付款交易的超时时间
	// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
	// 取值范围：1m～15d。
	// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
	// 该参数数值不接受小数点，如1.5h，可转换为90m。
	private String it_b_pay = "&it_b_pay=\"30m\"";

	// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
	// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
	// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	private String return_url = "&return_url=\"m.alipay.com\"";
	// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
	private String paymethod = "&paymethod=\"expressGateway\"";
	// 是否用银行支付
	private boolean isBank = false;

	/**
	 * AliPayTools构造方法
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 */
	private AliPayBuilder(String order_id, String good_name, String good_det,
			String good_price) {
		this.out_trade_no = "&out_trade_no=" + "\"" + order_id + "\"";
		this.subject = "&subject=" + "\"" + good_name + "\"";
		this.body = "&body=" + "\"" + good_det + "\"";
		this.total_fee = "&total_fee=" + "\"" + good_price + "\"";
	}
	
	public static AliPayBuilder createAliPayOrder(String order_id, String good_name, String good_det,String good_price){
		return new AliPayBuilder(order_id, good_name, good_det, good_price);
	}

	/**
	 * 注册用户信息
	 *
	 * @param pid
	 *            // 用户PID(支付宝账户id, 为注册支付宝账户生成, 固定值)
	 * @param sel_id
	 *            // 收款账户(固定值)
	 * @return
	 */
	public AliPayBuilder userInfo(String pid, String sel_id) {
		this.partner = "partner=" + "\"" + pid + "\"";
		this.seller_id = "&seller_id=" + "\"" + sel_id + "\"";
		return this;
	}

	/**
	 * 设置是否用银行支付
	 * 
	 * @param isBank
	 * @return
	 */
	public AliPayBuilder isBank(boolean isBank) {
		this.isBank = isBank;
		return this;
	}

	/**
	 * 设置服务器异步通知页面路径
	 * 
	 * @param url
	 * @return
	 */
	public AliPayBuilder notify_url(String url) {
		this.notify_url = "&notify_url=" + "\"" + url + "\"";
		return this;
	}

	public AliPayBuilder outTime(String time) {
		this.it_b_pay = "&it_b_pay=" + "\"" + time + "\"";
		return this;
	}

	public String toSign() {
		StringBuffer s = new StringBuffer();
		s.append(partner).append(seller_id)
		.append(out_trade_no)		
		.append(subject)
		.append(body)
		.append(total_fee)		
		.append(notify_url)
		.append(service)
		.append(payment_type)	
		.append(_input_charset)
		.append(it_b_pay)
		.append(return_url);
		if (isBank) {
			s.append(paymethod);
		}
		return s.toString();
	}
}
