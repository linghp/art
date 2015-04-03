package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;

public class MerchandiseControlActivity extends BaseActivity{
	private LinearLayout shangpin,peisong,tianjia,sousuo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchandisecontrol);
		initView();
		initData();
		listener();
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		shangpin = (LinearLayout) findViewById(R.id.merchandisecontrol_linear1);
		peisong = (LinearLayout) findViewById(R.id.merchandisecontrol_linear2);
		tianjia = (LinearLayout) findViewById(R.id.merchandisecontrol_linear3);
		sousuo = (LinearLayout) findViewById(R.id.merchandisecontrol_linear4);

	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void listener() {
		// TODO Auto-generated method stub
		shangpin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myToast("商品类别维护");
				
				CommonUtil.gotoActivity(MerchandiseControlActivity.this, ShangPinWeiHuActivity.class, false);
			}
		});
		peisong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myToast("配送类别维护");
			}
		});
		tianjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myToast("添加商品");
			}
		});
		sousuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				myToast("搜索商品");
				CommonUtil.gotoActivity(MerchandiseControlActivity.this, SearchActivity.class, false);
			}
		});
	}

}
