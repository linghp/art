package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 本期结算
 * @author Administrator
 *
 */
public class BenQiJieSuanActivity extends BaseActivity{

	private TextView tv_parice,tv_remainder,tv_time,tv_type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_benqijiesuan);
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
		topView.setTitle("本期结算");
		topView.setBack(R.drawable.back);//返回
		
		tv_parice = (TextView) findViewById(R.id.benqijiesuan_parice);//总金额
		tv_remainder = (TextView) findViewById(R.id.benqijiesuan_parice);//账户余额
		tv_time = (TextView) findViewById(R.id.benqijiesuan_time);//结算时间
		tv_type = (TextView) findViewById(R.id.benqijiesuan_type);//结算类型
	}

	private void initData() {
		String shopid = LocalUserInfo.getInstance(this).getString(Constant.INT_SHOPID);
		String url = BaseServer.HOST + "lhb/timeSettlementEveryOne/" + shopid;
		MyLogger.i("本期结算url：" + url);
		refreshTask(url);
	}

	private void refreshTask(String url) {
		
	}
	private void initListener() {
		// TODO Auto-generated method stub
		
	}
}
