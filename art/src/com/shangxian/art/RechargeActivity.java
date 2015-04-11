package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class RechargeActivity extends BaseActivity{

	EditText money;
	ImageView zhifubao,yinhangka;
	LinearLayout zhifubaolinear,yinhangkalinear;
	TextView quxiao,queren;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
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
		topView.setTitle("充值");
		topView.setBack(R.drawable.back);//返回

		money = (EditText) findViewById(R.id.recharge_balance);

		zhifubao = (ImageView) findViewById(R.id.recharge_zhifubao);
		yinhangka = (ImageView) findViewById(R.id.recharge_yinhangka);
		zhifubaolinear = (LinearLayout) findViewById(R.id.recharge_zhifubao_linear);
		yinhangkalinear = (LinearLayout) findViewById(R.id.recharge_yinhangka_linear);

		quxiao = (TextView) findViewById(R.id.recharge_quxiao);
		queren = (TextView) findViewById(R.id.recharge_queren);

	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initListener() {
		// TODO Auto-generated method stub
		zhifubaolinear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhifubao.setImageResource(R.drawable.sel_t);
				yinhangka.setImageResource(R.drawable.sel_n);
			}
		});
		yinhangkalinear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zhifubao.setImageResource(R.drawable.sel_n);
				yinhangka.setImageResource(R.drawable.sel_t);
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		queren.setOnClickListener(new OnClickListener() {
			//确认
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
