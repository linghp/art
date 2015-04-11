package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.AccountSumInfo;
import com.shangxian.art.net.BaseServer.OnAccountSumListener;
import com.shangxian.art.net.PayServer;
import com.shangxian.art.view.SwitchButton;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.text.GetChars;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PayActivity extends BaseActivity {
	private TopView topView;
	private TextView tv_paymoney, tv_realpaymoney;
	private String orderid;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		
		PayServer.loadAccountSum(new OnAccountSumListener() {
			@Override
			public void onAccountSum(AccountSumInfo info) {
				System.out.println("account ======================+++++++++++++++++" + info ==null ? "null" : info.toString());	
				mAccount = info;
				
			}
		});
		
		initViews();
		initdata();
		listener();
	}

	private void initdata() {
		orderid = getIntent().getStringExtra("orderid");
		totalprice = getIntent().getFloatExtra("totalprice", 0);
		tv_paymoney.setText("￥ " + totalprice);
		tv_realpaymoney.setText("￥ " + totalprice);
	}

	private void initViews() {
		tv_paymoney = (TextView) findViewById(R.id.payt_tv_paymoney);
		tv_realpaymoney = (TextView) findViewById(R.id.tv_realpaymoney);
		
		sb_bi = (SwitchButton) findViewById(R.id.pays_sb_bi);
		sb_yuan = (SwitchButton) findViewById(R.id.pays_sb_yuan);
		
		cb_zhi = (CheckBox) findViewById(R.id.payc_cb_zhi);
		cb_yin = (CheckBox) findViewById(R.id.payc_cb_yin);
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
		sb_bi.setChecked(isBi);
		sb_yuan.setChecked(isYuan);
		cb_zhi.setChecked(isZhi);
		cb_yin.setChecked(isYin);
	}

	private void listener() {
		sb_bi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isBi = isChecked;
				//myToast("" + isBi);
			}
		});
		sb_yuan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isYuan = isChecked;
				//myToast("" + isYuan);
			}
		});
		
		//TODO: --------------------------------------------------------------------
		cb_zhi.setEnabled(false);
		cb_yin.setEnabled(false);
		
		cb_zhi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isZhi = isChecked;
				if (isChecked && isYin) {
					cb_yin.setChecked(! isYin);
				}
			}
		});
		cb_yin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isYin = isChecked;
				if (isChecked && isZhi) {
					cb_zhi.setChecked(! isZhi);
				}
			}
		});
	}

	public static void startThisActivity(String orderid, float totalprice,
			Context context) {
		Intent intent = new Intent(context, PayActivity.class);
		intent.putExtra("orderid", orderid);
		intent.putExtra("totalprice", totalprice);
		context.startActivity(intent);
	}

	public void doClick(View view) {
		switch (view.getId()) {
		case R.id.payt_tv_cancel:
			//myToast("放弃");
			finish();
			break;
		case R.id.payt_tv_ok:
			//myToast("确定");
			okToPay();
			break;
		case R.id.payl_ll_zhi:
			//myToast("支付宝功能暂未开通");
			
			//TODO: ------------                 ------------
			cb_zhi.setChecked(! isZhi);
			break;
		case R.id.payl_ll_yin:
			//myToast("银行卡支付暂未开通");
			
			//TODO: -----------                 -------------
			cb_yin.setChecked(! isYin);
			break;
		}
	}

	private void okToPay() {
		if (match()) {
			
		}
	}

	private boolean match() {
		if (!isBi && !isYuan) {
			myToast("请确认支付方式");
		}
		return true;
	}
}
