package com.shangxian.art;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;
/**
 * 商铺简介
 * @author Administrator
 *
 */
public class ShopsSummaryActivity extends BaseActivity{
	ImageView shopsimg,phoneimg,addressimg;//商铺图片、电话图片、地址图片
	TextView shopsname,guanzu,zhanggui,phone,address,summary;//商铺名字、关注、掌柜名、电话、地址、简介
	
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
		topView.setRightBtnDrawable(R.drawable.collection);
		topView.setTitle("商铺简介");
		topView.setBack(R.drawable.back);//返回
		
		shopsimg = (ImageView) findViewById(R.id.shopssummary_shopsimg);
		phoneimg = (ImageView) findViewById(R.id.shopssummary_phoneimg);
		addressimg = (ImageView) findViewById(R.id.shopssummary_addressimg);
		
		shopsname = (TextView) findViewById(R.id.shopssummary_shopsname);
		guanzu = (TextView) findViewById(R.id.shopssummary_guanzu);
		zhanggui = (TextView) findViewById(R.id.shopssummary_zhanggui);
		phone = (TextView) findViewById(R.id.shopssummary_phone);
		address = (TextView) findViewById(R.id.shopssummary_address);
		summary = (TextView) findViewById(R.id.shopssummary_summary);
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		//拨打电话
		phoneimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+10086));
				startActivity(intent);
			}
		});
		//跳转到定位
		addressimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
