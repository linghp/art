package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

public class AddDeliveryAddressActivity extends BaseActivity{

	private EditText name,num,youbian,address;
	private TextView diqu,quxiao,baocun;
	private ImageView diqu_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adddeliveryaddress);
		initView();
		initListener();
		
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_adddeliveryaddress));
		
		name = (EditText) findViewById(R.id.adddeliveryaddress_name);//收货人
		num = (EditText) findViewById(R.id.adddeliveryaddress_num);//联系方式
		youbian = (EditText) findViewById(R.id.adddeliveryaddress_youzheng);//邮政编码
		address = (EditText) findViewById(R.id.adddeliveryaddress_address);//详细地址
		diqu = (TextView) findViewById(R.id.adddeliveryaddress_address_txt);//地区
		diqu_img = (ImageView) findViewById(R.id.adddeliveryaddress_address_img);//地区img
		quxiao = (TextView) findViewById(R.id.adddeliveryaddress_quxiao);//取消
		baocun = (TextView) findViewById(R.id.adddeliveryaddress_baocun);//保存
	}
	private void initListener() {
		diqu_img.setOnClickListener(new OnClickListener() {
			//选择地区
			@Override
			public void onClick(View v) {
				
				
			}
		});
		baocun.setOnClickListener(new OnClickListener() {
			//保存
			@Override
			public void onClick(View v) {
				
				
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {
			//取消
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}

}
