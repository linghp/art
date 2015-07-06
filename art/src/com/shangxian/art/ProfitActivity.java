package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class ProfitActivity extends BaseActivity{
	TextView allprice,pingjun,today,yestoday,all;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profit);
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
		topView.setTitle("农合宝");
		topView.setBack(R.drawable.back);//返回
		allprice = (TextView) findViewById(R.id.profit_allprice);//总金额
		pingjun = (TextView) findViewById(R.id.profit_pingjun);//平均收益率
		today = (TextView) findViewById(R.id.profit_today);//今日收益
		yestoday = (TextView) findViewById(R.id.profit_yesterday);//昨日收益
		all = (TextView) findViewById(R.id.profit_all);//累计收益
	}

	private void initData() {
		
	}

	private void initListener() {
		
	}
}
