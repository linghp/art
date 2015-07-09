package com.shangxian.art;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

/**
 * 
 * @author Administrator
 *
 */
public class YiJieSuanActivity extends BaseActivity{

	TextView num,price,jindu,jiesuantime,name,phone,shenqingtime,chexiao,chaxun;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yishenqing);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideCenterSearch();//隐藏搜索框
		topView.hideRightBtn_invisible();//隐藏右按钮
		topView.showTitle();
		topView.setTitle("紧急结算");
		topView.setBack(R.drawable.back);//返回
		
		num = (TextView) findViewById(R.id.yishenqing_num);//申请编号
		price = (TextView) findViewById(R.id.yishenqing_price);//申请金额
		jindu = (TextView) findViewById(R.id.yishenqing_jindu);//进度
		jiesuantime = (TextView) findViewById(R.id.yishenqing_time);//申请结算时间
		name = (TextView) findViewById(R.id.yishenqing_name);//申请人
		phone = (TextView) findViewById(R.id.yishenqing_phone);//申请人手机号
		shenqingtime = (TextView) findViewById(R.id.yishenqing_time1);//申申请时间
		chexiao = (TextView) findViewById(R.id.yishenqing_chexiao);//撤销
		chaxun = (TextView) findViewById(R.id.yishenqing_jinduchaxun);//进度查询
	}

	private void initData() {
		
		
	}

	private void initListener() {
		chexiao.setOnClickListener(new OnClickListener() {
			//撤销
			@Override
			public void onClick(View arg0) {
				
				
			}
		});
		
		chaxun.setOnClickListener(new OnClickListener() {
			//进度查询
			@Override
			public void onClick(View arg0) {
				
				
			}
		});
	}
}
