package com.shangxian.art;

import java.io.Serializable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
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
import com.shangxian.art.bean.PayOrderInfo;
import com.shangxian.art.dialog.PayPasswordDialog;
import com.shangxian.art.dialog.PayPasswordDialog.OnScanedListener;
import com.shangxian.art.net.BaseServer.OnAccountSumListener;
import com.shangxian.art.net.BaseServer.OnPayListener;
import com.shangxian.art.net.BaseServer.OnPaymentListener;
import com.shangxian.art.net.PayServer;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.SwitchButton;
import com.shangxian.art.view.TopView;

/**
 * 结算付款
 */
public class PayActivity extends BaseActivity {
	private TopView topView;
	private TextView tv_paymoney, tv_realpaymoney;
	private List<String> orderids;
	private float totalprice;
	private SwitchButton sb_bi;
	private SwitchButton sb_yuan;
	private boolean isBi = true;
	private boolean isYuan = true;
	private boolean isZhi = false;
	private boolean isYin = false;
	private CheckBox cb_zhi;
	private CheckBox cb_yin;
	AccountSumInfo mAccount = new AccountSumInfo();
	private LinearLayout ll_money;
	private LinearLayout ll_scan;
	private EditText et_scan;
	private boolean isOrder;
	private TextView tv_bi;
	private TextView tv_yuan;
	private double lastMon = Double.MIN_VALUE;
	private String type = "ALB_ALY"; // 默认:爱农币+爱农元
	private float price;

	private double onlineMon = 0.00;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			String money = "";
			if (isOrder) {
				money = price + "";
			} else {
				money = et_scan.getText().toString();
			}
			double mon = Double.MIN_VALUE;
			try {
				mon = Double.parseDouble(money);
				// myToast("handler" + mon +" >>> money==" + money +
				// " >>> isBi ===" + isBi + " >>>> isYuan ==" + isYuan);
				if (mon > 100000000) {
					myToast("超出最大交易金额");
					et_scan.setText(String.format("%.2f", lastMon));
				} else {
					lastMon = mon;
					// et_scan.setText(text);
					if (mon != Double.MIN_VALUE && !mAccount.isNull()) {
						if (isBi && !isYuan) {
							type = "ALB";
							if (mon > mAccount.getAlb()) {
								onlineMon = mon - mAccount.getAlb();
								tv_realpaymoney.setText("¥ "
										+ String.format("%.2f", onlineMon));
							} else {
								onlineMon = 0.00;
								tv_realpaymoney.setText("¥ 0.00");
							}
						}
						if (!isBi && isYuan) {
							type = "ALY";
							if (mon > mAccount.getAly()) {
								onlineMon = mon - mAccount.getAly();
								tv_realpaymoney.setText("¥ "
										+ String.format("%.2f", onlineMon));
							} else {
								onlineMon = 0.00;
								tv_realpaymoney.setText("¥ 0.00");
							}
						}
						if (isBi && isYuan) {
							type = "ALB_ALY";
							if (mon > (mAccount.getAly() + mAccount.getAlb())) {
								onlineMon = mon - mAccount.getAly()
										- mAccount.getAlb();
								tv_realpaymoney.setText("¥ "
										+ String.format("%.2f", onlineMon));
							} else {
								onlineMon = 0.00;
								tv_realpaymoney.setText("¥ 0.00");
							}
						}
						if (!isBi && !isYuan) {
							onlineMon = mon;
							tv_realpaymoney.setText("¥ "
									+ String.format("%.2f", mon));
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
		totalprice = getIntent().getFloatExtra("totalprice", 0f);
		if (totalprice != 0) {
			isOrder = true;
			price = totalprice;
			orderids = (List<String>) getIntent().getSerializableExtra(
					"orderids");
			if (orderids != null) {
				MyLogger.i(orderids.toString());
			}
		} else {
			isOrder = false;
		}
	}

	private void initViews() {
		tv_paymoney = (TextView) findViewById(R.id.payt_tv_paymoney);
		tv_realpaymoney = (TextView) findViewById(R.id.tv_realpaymoney);
		tv_bi = (TextView) findViewById(R.id.payt_tv_bi);
		tv_yuan = (TextView) findViewById(R.id.payt_tv_yuan);
		sb_bi = (SwitchButton) findViewById(R.id.pays_sb_bi);
		sb_yuan = (SwitchButton) findViewById(R.id.pays_sb_yuan);

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
				System.out
						.println("account ======================+++++++++++++++++"
								+ info == null ? "null" : info.toString());
				if (info != null && !info.isNull()) {
					mAccount = info;
					tv_bi.setText("余额"
							+ String.format("%.2f", mAccount.getAlb()) + "元");
					tv_yuan.setText("余额"
							+ String.format("%.2f", mAccount.getAly()) + "元");
				}
			}
		});

		if (isOrder) {
			tv_paymoney.setText("￥ " + totalprice);
			tv_realpaymoney.setText("￥ " + totalprice);
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			}, 200);
		} else {
			tv_realpaymoney.setText("¥ 0.00");
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
				handler.sendEmptyMessage(0);
				// myToast("isBi === " + isBi);
			}
		});
		sb_yuan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isYuan = isChecked;
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
			float totalprice, Activity mAc) {
		Intent intent = new Intent(mAc, PayActivity.class);
		intent.putExtra("orderids", (Serializable) orderids);
		intent.putExtra("totalprice", totalprice);
		mAc.startActivityForResult(intent, 1000);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.payt_tv_cancel:
			// myToast("放弃");
			finish();
			break;
		case R.id.payt_tv_ok:
			// myToast("确定");
			okToPay();
			break;
		case R.id.payl_ll_zhi:
			// myToast("支付宝功能暂未开通");
			// TODO: -------------------------------
			// -----------------------------------
			cb_zhi.setChecked(!isZhi);
			break;
		case R.id.payl_ll_yin:
			// myToast("银行卡支付暂未开通");
			// TODO: -------------------------------
			// --------------------------------------
			cb_yin.setChecked(!isYin);
			break;
		}
	}

	private void okToPay() {
		if (match()) {
			PayPasswordDialog dialog = new PayPasswordDialog(mAc);
			dialog.setMoney("¥ " + String.format("%.2f", lastMon));
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
						info.setAmount((int) (lastMon));
						info.setOrderNumber(orderids);
						info.setPayPassword(pass);
						info.setPayType(type);
						PayServer.toPayOrder(info, new OnPayListener() {
							@Override
							public void onPayment(boolean res) {
								if (res) {
									myToast("支付成功");
									Intent data = new Intent();
									data.putExtra("pay_order_res", true);
									setResult(RESULT_OK, data);
									finish();
								} else {
									myToast("支付失败");
								}
							}
						});
					} else {
						PayServer.toPayment(pass, 3, (int) (lastMon), type,
								new OnPaymentListener() {
									@Override
									public void onPayment(String res) {
										if (res.equals("true")) {
											myToast("支付成功");
											finish();
										} else {
											myToast("支付失败");
										}
									}
								});
					}
				}
			});
			dialog.show();
		}
	}

	private boolean match() {
		if (lastMon == Double.MIN_VALUE) {
			myToast("请输入支付金额");
			return false;
		}
		if (onlineMon > 0  && isZhi) {
			toAlipay(isBi || isYuan);
			return false;
		}
		if (!isBi && !isYuan && !isZhi) {
			myToast("请选择支付方式(注:在线支付暂只支持支付宝)");
			return false;
		}
		return true;
	}
	
	/**
	 * 支付宝支付
	 * 
	 */
	private void toAlipay(final boolean isToOkPay) {
		if (isZhi) {
			AliPayServer.toPay(null, "测试商品", "测试商品1",
					onlineMon + "",
					new com.shangxian.art.alipays.AliPayBase.OnPayListener() {
						@Override
						public void onSuccess(String res) {
							myToast("支付成功");
							if (isToOkPay) {
								okToPay();
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
