package com.shangxian.art;

import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * 卖家订单管理界面
 * 
 * @author libo
 */
public class SellerOrderManageActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_seller_order_manage);
		TopView top = (TopView) findViewById(R.id.top_title);
		top.showTitle();
		top.setTitle("购物车");
		top.showTitle();
		top.setBack(R.drawable.back);// 返回
		top.showRightBtn();
		top.hideCenterSearch();
	}

	public void doClick_send(View v) {
		SellerOrderActivity.startThisActivity(mAc, true);
	}

	public void doClick_return(View v) {
		SellerOrderActivity.startThisActivity(mAc, false);
	}
}
