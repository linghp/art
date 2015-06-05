package com.shangxian.art;

import android.os.Bundle;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;
/**
 * 配送类别维护
 * @author Administrator
 *
 */

public class DeliveryServiceActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deliveryservice);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();//隐藏搜索框
//		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("配送类别维护");
		topView.setBack(R.drawable.back);//返回
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		
	}
}
