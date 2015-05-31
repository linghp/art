package com.shangxian.art;

import android.os.Bundle;
import android.widget.TextView;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.view.TopView;

/**
 * 退货订单详情
 * @author zyz
 *
 */
public class ReturnOrderDetailsActivity extends BaseActivity{
	private TextView tv_time,tv_return,tv_goods,tv_istrue,tv_money,tv_reason,tv_explain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returnorderdetails);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.addto);
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_iandedetailscontent));
		
		tv_time = (TextView) findViewById(R.id.returnorderdetails_tv1);//退款时间
		tv_return = (TextView) findViewById(R.id.returnorderdetails_tv2);//退款状态
		tv_goods = (TextView) findViewById(R.id.returnorderdetails_tv3);//货物状态
		tv_istrue = (TextView) findViewById(R.id.returnorderdetails_tv4);//是否需要退还货物
		tv_money = (TextView) findViewById(R.id.returnorderdetails_tv5);//退还金额
		tv_reason = (TextView) findViewById(R.id.returnorderdetails_tv6);//退款原因
		tv_explain = (TextView) findViewById(R.id.returnorderdetails_tv7);//退款说明
	}

	private void initData() {
		
		
	}

	private void initListener() {
		
		
	}
}
