package com.shangxian.art;

import android.os.Bundle;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.StarRatingView;

public class CommodityContentActivity extends BaseActivity{

//	private StarRatingView star;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commoditycontent);
		
		initView();
		initData();
		listener();
	}

	private void listener() {
		// TODO Auto-generated method stub
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initView() {
		// TODO Auto-generated method stub
//		star = (StarRatingView) findViewById(R.id.commoditycontent_starRating);
//		star.setSelectNums(1);//设置默认选中星星数
	}
}
