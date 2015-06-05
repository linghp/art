package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

public class CashActivity extends BaseActivity{

	TextView balance,cash,bankcard,quxiao,next;
	EditText money;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();//显示title
		topView.setTitle("提现");
		topView.setBack(R.drawable.back);//返回
		
		balance = (TextView) findViewById(R.id.cash_ainongyuan);//爱农元余额
		cash = (TextView) findViewById(R.id.cash_ketixian);//可提现
		bankcard = (TextView) findViewById(R.id.cash_bankcard);//提现借记卡
		quxiao = (TextView) findViewById(R.id.cash_quxiao);//取消
		next = (TextView) findViewById(R.id.cash_next);//下一步
		money = (EditText) findViewById(R.id.cash_money);//提现金额
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			//下一步
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(CashActivity.this, IdValidateActivity.class, false);
			}
		});
	}
}
