package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;
/**
 * 商品详情
 * @author Administrator
 *
 */
public class CommodityContentActivity extends BaseActivity{

	//	private StarRatingView star;
	private LinearLayout shangpu;


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
		shangpu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonUtil.gotoActivity(CommodityContentActivity.this, ShopsActivity.class, false);
			}
		});
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

		shangpu = (LinearLayout) findViewById(R.id.commoditycontent_shangpu);
	}
}
