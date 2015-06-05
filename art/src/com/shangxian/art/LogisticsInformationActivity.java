package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class LogisticsInformationActivity extends BaseActivity{

	private EditText et_company,et_number;//公司、快递单号
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logisticsinformation);
		initView();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_logisticsinformation));
		
		
		
	}

	public void doClick(View v){
		switch (v.getId()) {
		case R.id.logisticsinfomation_tv4:
			//取消
			finish();
			break;
		case R.id.logisticsinfomation_tv5:
			//提交
			
			break;

		default:
			break;
		}
	}
}
