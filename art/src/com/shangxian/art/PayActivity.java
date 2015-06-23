package com.shangxian.art;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.alipays.AliPayServer;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.bean.CommonBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.PayOrderInfo;
import com.shangxian.art.dialog.PayPasswordDialog;
import com.shangxian.art.dialog.PayPasswordDialog.OnScanedListener;
import com.shangxian.art.net.BaseServer.OnAccountSumListener;
import com.shangxian.art.net.BaseServer.OnPayListener;
import com.shangxian.art.net.BaseServer.OnPaymentListener;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.PayServer;
import com.shangxian.art.net.PayServer.OnPayNoticeListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.SwitchButton;
import com.shangxian.art.view.TopView;

/**
 * 结算付款  现在只支持alb,aly混，支付宝单独付不能混
 */
public class PayActivity extends BaseActivity implements OnPayNoticeListener{
	private TopView topView;
	private TextView tv_paymoney;//tv_realpaymoney;
	private List<String> orderids;
	private String productName;
	//private float totalprice;
	private SwitchButton sb_bi;
	private SwitchButton sb_yuan;
	private View linearLayout1;
	private boolean isBi = true;
	private boolean isYuan = true;
	private boolean isZhi = false;
	private boolean isYin = false;
	private CheckBox cb_zhi;
	private CheckBox cb_yin;
	AccountSumInfo mAccount;
	private LinearLayout ll_money;
	private LinearLayout ll_scan;
	private EditText et_scan;
	/**true为普通的订单，false为扫码支付*/
	private boolean isOrder;
	private TextView tv_bi;
	private TextView tv_yuan;
	//private double lastMon = Double.MIN_VALUE;
	private String type = "ALB_ALY"; // 默认:爱农币+爱农元
	private double price;
	/**自己生成个的支付宝交易号*/
	private String trideno="S"+CommonUtil.getUUID();
	/**扫描支付传的商铺id*/
	private String storeId;

	//private double onlineMon = 0.00;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//int what = msg.what;
			if (isOrder) {
				//money = price + "";
			} else {
				String price_temp=et_scan.getText().toString().trim();
				if(!TextUtils.isEmpty(price_temp)){
					price=Double.parseDouble(price_temp);
				}
			}
			try {
				if (price > 100000000) {
					myToast("超出最大交易金额");
					et_scan.setText(String.format("%.2f", price));
				} else {
					// et_scan.setText(text);
					if (price>0&&mAccount!=null&&!mAccount.isNull()) {
						if (isBi && !isYuan) {
							type = "ALB";
//							if (mon > mAccount.getAlb()) {
//								onlineMon = mon - mAccount.getAlb();
//								tv_realpaymoney.setText("¥ "
//										+ String.format("%.2f", onlineMon));
//							} else {
//								onlineMon = 0.00;
//								tv_realpaymoney.setText("¥ 0.00");
//							}
						}
						if (!isBi && isYuan) {
							type = "ALY";
//							if (mon > mAccount.getAly()) {
//								onlineMon = mon - mAccount.getAly();
//								tv_realpaymoney.setText("¥ "
//										+ String.format("%.2f", onlineMon));
//							} else {
//								onlineMon = 0.00;
//								tv_realpaymoney.setText("¥ 0.00");
//							}
						}
						if (isBi && isYuan) {
							type = "ALB_ALY";
//							if (mon > (mAccount.getAly() + mAccount.getAlb())) {
//								onlineMon = mon - mAccount.getAly()
//										- mAccount.getAlb();
//								tv_realpaymoney.setText("¥ "
//										+ String.format("%.2f", onlineMon));
//							} else {
//								onlineMon = 0.00;
//								tv_realpaymoney.setText("¥ 0.00");
//							}
						}
						if (!isBi && !isYuan) {
							type="";
//							onlineMon = mon;
//							tv_realpaymoney.setText("¥ "
//									+ String.format("%.2f", mon));
						}
					}
				}
			} catch (Exception e) {
				myToast("输入支付金额格式错误");
				if (isOrder) {
					finish();
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		initdata();
		initViews();
		listener();
	}

	private void initdata() {
		price = getIntent().getFloatExtra("totalprice", 0f);
		productName=getIntent().getStringExtra("productName");
		if (price != 0) {
			isOrder = true;
			orderids = (List<String>) getIntent().getSerializableExtra(
					"orderids");
			if (orderids != null) {
				MyLogger.i(orderids.toString());
			}
		} else {
			isOrder = false;
		}
		storeId=getIntent().getStringExtra("storeId");
	}

	private void initViews() {
		tv_paymoney = (TextView) findViewById(R.id.payt_tv_paymoney);
		//tv_realpaymoney = (TextView) findViewById(R.id.tv_realpaymoney);
		tv_bi = (TextView) findViewById(R.id.payt_tv_bi);
		tv_yuan = (TextView) findViewById(R.id.payt_tv_yuan);
		sb_bi = (SwitchButton) findViewById(R.id.pays_sb_bi);
		sb_yuan = (SwitchButton) findViewById(R.id.pays_sb_yuan);
		linearLayout1 = findViewById(R.id.linearLayout1);

		cb_zhi = (CheckBox) findViewById(R.id.payc_cb_zhi);
		cb_yin = (CheckBox) findViewById(R.id.payc_cb_yin);

		ll_money = (LinearLayout) findViewById(R.id.payl_ll_money);
		ll_scan = (LinearLayout) findViewById(R.id.payl_ll_scan);
		et_scan = (EditText) findViewById(R.id.paye_et_scan);

		// et_scan.setFocusable(true);
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_pay));

		upDataView();
	}

	private void upDataView() {
		if (isOrder) {
			ll_scan.setVisibility(View.INVISIBLE);
			ll_money.setVisibility(View.VISIBLE);
		} else {
			ll_scan.setVisibility(View.VISIBLE);
			ll_money.setVisibility(View.INVISIBLE);
		}

		PayServer.loadAccountSum(new OnAccountSumListener() {
			@Override
			public void onAccountSum(AccountSumInfo info) {
				if (info != null && !info.isNull()) {
					MyLogger.i(info.toString());
					mAccount = info;
					tv_bi.setText("余额"
							+ String.format("%.2f", mAccount.getAlb()) + "元");
					tv_yuan.setText("余额"
							+ String.format("%.2f", mAccount.getAly()) + "元");
					if(mAccount.getAly_Alb()<price){
						linearLayout1.setVisibility(View.VISIBLE);
					}
				}
			}
		});

		if (isOrder) {
			tv_paymoney.setText("￥ " + price);
			//tv_realpaymoney.setText("￥ " + totalprice);
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			}, 200);
		} else {
			//tv_realpaymoney.setText("¥ 0.00");
		}

		sb_bi.setChecked(isBi);
		sb_yuan.setChecked(isYuan);
		cb_zhi.setChecked(isZhi);
		cb_yin.setChecked(isYin);
	}

	private void listener() {
		sb_bi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isBi = isChecked;
				if(isChecked){
					cb_zhi.setChecked(false);
				}
				handler.sendEmptyMessage(0);
				// myToast("isBi === " + isBi);
			}
		});
		sb_yuan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isYuan = isChecked;
				if(isChecked){
					cb_zhi.setChecked(false);
				}
				handler.sendEmptyMessage(0);
				// myToast("isYuan === " + isYuan);
			}
		});

		// TODO:
		// --------------------------------------------------------------------
		cb_zhi.setEnabled(false);
		cb_yin.setEnabled(false);

		cb_zhi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isZhi = isChecked;
				if (isChecked && isYin) {
					cb_yin.setChecked(!isYin);
				}
			}
		});
		cb_yin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isYin = isChecked;
				if (isChecked && isZhi) {
					cb_zhi.setChecked(!isZhi);
				}
			}
		});

		et_scan.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				handler.sendEmptyMessage(0);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public static void startThisActivity(List<String> orderids,
			float totalprice, String productName,Activity mAc) {
		Intent intent = new Intent(mAc, PayActivity.class);
		intent.putExtra("orderids", (Serializable) orderids);
		intent.putExtra("totalprice", totalprice);
		intent.putExtra("productName", productName);
		mAc.startActivityForResult(intent, 1000);
	}
	/**
	 * 扫描支付
	 * @param id
	 * @param mAc
	 */
	public static void startThisActivity(String storeId,Activity mAc) {
		Intent intent = new Intent(mAc, PayActivity.class);
		intent.putExtra("storeId", storeId);
		mAc.startActivity(intent);
	}

	public static void startThisActivity_Fragment(List<String> orderids,
			float totalprice, String productName,Activity mAc, Fragment fragment) {
		Intent intent = new Intent(mAc, PayActivity.class);
		intent.putExtra("orderids", (Serializable) orderids);
		intent.putExtra("totalprice", totalprice);
		intent.putExtra("productName", productName);
		fragment.startActivityForResult(intent, 111);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.payt_tv_cancel:
			// myToast("放弃");
			if (orderids.size() == 1) {
				MyOrderDetailsActivity
						.startThisActivity(
								orderids.get(0),
								PayActivity.this);
			}
			finish();
			break;
		case R.id.payt_tv_ok:
			// myToast("确定");
			if (isPayed(true)) {
				okToPay();
			}
			break;
		case R.id.payl_ll_zhi:
			// myToast("支付宝功能暂未开通");
			// TODO: -------------------------------
			// -----------------------------------
			cb_zhi.setChecked(!isZhi);
			sb_bi.setChecked(false);
			sb_yuan.setChecked(false);
			break;
		case R.id.payl_ll_yin:
			 myToast("银行卡支付暂未开通");
			// TODO: -------------------------------
			// --------------------------------------
			//cb_yin.setChecked(!isYin);
			break;
		}
	}

	private void okToPay() {
		if (match()) {
			PayPasswordDialog dialog = new PayPasswordDialog(mAc);
			dialog.setMoney("¥ " + String.format("%.2f", price));
			if (isBi && !isYuan) {
				dialog.setType("爱农币");
			}
			if (!isBi && isYuan) {
				dialog.setType("爱农元");
			}
			if (isBi && isYuan) {
				dialog.setType("爱农币+爱农元");
			}
			dialog.setOnScanedListener(new OnScanedListener() {
				@Override
				public void onScan(String pass) {
					// myToast(pass);
					if (isOrder) {
						PayOrderInfo info = new PayOrderInfo();
						// info.setAmount((int) (lastMon));
						info.setOrderNumber(orderids);
						info.setPayPassword(pass);
						info.setPayType(type);
						PayServer.toPayOrder(info, new OnPayListener() {
							@Override
							public void onPayment(boolean res) {
								if (res) {
									paySuccessAfter();
								} else {
									myToast("支付失败");
								}
							}
						});
					} else if(!TextUtils.isEmpty(storeId)){
						// PayServer.toPayment(pass, 3, (int) (lastMon), type,
						// new OnPaymentListener() {
						// @Override
						// public void onPayment(String res) {
						// if (res.equals("true")) {
						// myToast("支付成功");
						// finish();
						// } else {
						// myToast("支付失败");
						// }
						// }
						// });
						new PayServer().toPayment(pass, storeId, price,
								type, new CallBack() {
									@Override
									public void onSimpleSuccess(Object res) {
										myToast("支付成功");
										finish();
									}

									@Override
									public void onSimpleFailure(int code) {
										myToast("支付失败");
									}
								});
					}
				}
			});
			dialog.show();
		}
	}

	private void paySuccessAfter(){
		myToast("支付成功");
		Intent data = new Intent();
		MyOrderItem myOrderItem = new MyOrderItem();
		myOrderItem
				.setStatus(MyOrderActivity.orderState[2]);
		MyLogger.i(myOrderItem.toString());
		data.putExtra("pay_order_res", true);
		data.putExtra("MyOrderItem", myOrderItem);
		if (orderids.size() == 1) {
			MyOrderDetailsActivity
					.startThisActivity(
							orderids.get(0),
							PayActivity.this);
		}
		setResult(RESULT_OK, data);
		finish();
	}
	
	private boolean match() {
		if (price == 0) {
			myToast("请输入支付金额");
			return false;
		}else if (isZhi) {
			if(isOrder){
			toPayNotice();
			}else{
			toAlipay();
			}
			return false;
		}else if (!isBi && !isYuan && !isZhi) {
			myToast("请选择支付方式(注:在线支付暂只支持支付宝)");
			return false;
		}else if(isBi&&!isYuan){
			if(mAccount.getAlb()<price){
				myToast("爱农币余额不足");	
				return false;
			}
		}else if(!isBi&&isYuan){
			if(mAccount.getAly()<price){
				myToast("爱农元余额不足");	
				return false;
			}
		}else if(isBi&&isYuan){
			if(mAccount.getAly_Alb()<price){
				myToast("余额不足");	
				return false;
			}
		}
		return isPayed(true);
	}

	private void toPayNotice() {
		PayServer.toPaymentNotice(trideno, "ALIPAY", orderids, this);
	}

	/**
	 * 支付宝支付
	 * 
	 */
	private void toAlipay() {
		if (isZhi) {
			int userid=getUserId();
			if(userid>0){
			AliPayServer.toPay(trideno, productName, userid+"", price + "",
					new com.shangxian.art.alipays.AliPayBase.OnPayListener() {
						@Override
						public void onSuccess(String res) {
							if(isOrder){
								paySuccessAfter();
							}else{
							myToast("支付成功");
							finish();
							}
						}

						@Override
						public void onFailed(String msg) {
							myToast("支付失败");
						}

						@Override
						public void on8000() {
							myToast("等待支付结果确认");
						}
					});
			}
		}
	}

	@Override
	public void onPayNotice(CommonBean commonBean) {
		if(commonBean!=null){
			if(commonBean.getResult_code().equals("200")){
				toAlipay();
			}else{
				myToast(commonBean.getResult());
			}
		}else{
			
		}
	}
}
