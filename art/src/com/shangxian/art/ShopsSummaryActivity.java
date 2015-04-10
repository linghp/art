package com.shangxian.art;

import android.os.Bundle;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;
/**
 * 商铺简介
 * @author Administrator
 *
 */
public class ShopsSummaryActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopssummary);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.more1);
		topView.setTitle("商铺简介");
		topView.setBack(R.drawable.back);//返回
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}

}
