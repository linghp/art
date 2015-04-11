package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.text.GetChars;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PayActivity extends BaseActivity {
	private TopView topView;
	private TextView tv_paymoney,tv_realpaymoney;
	private String orderid;
	private float totalprice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		initViews();
		initdata();
		listener();
	}
	private void initdata() {
		orderid=getIntent().getStringExtra("orderid");
		totalprice=getIntent().getFloatExtra("totalprice", 0);
		tv_paymoney.setText("￥ "+totalprice);
		tv_realpaymoney.setText("￥ "+totalprice);
	}
	
	private void initViews() {
		tv_paymoney=(TextView) findViewById(R.id.tv_paymoney);
		tv_realpaymoney=(TextView) findViewById(R.id.tv_realpaymoney);
		//改变topbar
				topView=(TopView) findViewById(R.id.top_title);
				topView.setActivity(this);
				topView.hideRightBtn_invisible();
				topView.hideCenterSearch();
				topView.showTitle();
				topView.setBack(R.drawable.back);
				topView.setTitle(getString(R.string.title_activity_pay));
	}
	private void listener() {
		// TODO Auto-generated method stub
		
	}
	
	public static void startThisActivity(String orderid,float totalprice,Context context){
		Intent intent=new Intent(context, PayActivity.class);
		intent.putExtra("orderid", orderid);
		intent.putExtra("totalprice", totalprice);
		context.startActivity(intent);
	}
	public void doClick(View view){
		switch (view.getId()) {
		case R.id.tv_cancel:
			myToast("放弃");
			break;
		case R.id.tv_ensure:
			myToast("确定");
			break;

		default:
			break;
		}
	}

}
