package com.shangxian.art;

import android.os.Bundle;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.StarRatingView;
import com.shangxian.art.view.TopView;
/**
 * 商品详情
 * @author Administrator
 *
 */
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
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);//返回
		topView.setTitle("商品详情");//title文字
	}
}
