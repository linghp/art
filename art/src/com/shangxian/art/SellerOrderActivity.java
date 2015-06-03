package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.fragment.SellerOrder_All_Fragment;
import com.shangxian.art.view.TopView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class SellerOrderActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout ll_tab1;
	private LinearLayout ll_tab2;
	private LinearLayout ll_tab3;
	private LinearLayout ll_tab4;
	private ViewPager vp_content;

	private List<Fragment> fragments = new ArrayList<Fragment>();
	private FragmentViewPagerAdp adapter;
	protected int curIndex;

	public static String[] orderState = { "PENDING", "SUBMITTED", "PAID",
			"SHIPPING", "COMPLETED", "ORDER_RETURNING", "EVALUATE", "CANCELLED" };
	public static String[] orderStateValue = { "未提交", "待付款", "待发货", "待收货",
			"已完成交易", "退款中", "待评价", "已取消交易" };

	public static Map<String, String> map_orderStateValue = new HashMap<String, String>();
	static {
		for (int i = 0; i < orderState.length; i++) {
			map_orderStateValue.put(orderState[i], orderStateValue[i]);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_seller_order);
		initViews();
		initDatas();
		Listener();
	}

	private void initViews() {
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle("订单管理");

		ll_tab1 = (LinearLayout) findViewById(R.id.ll_tab1);
		ll_tab2 = (LinearLayout) findViewById(R.id.ll_tab2);
		ll_tab3 = (LinearLayout) findViewById(R.id.ll_tab3);
		ll_tab4 = (LinearLayout) findViewById(R.id.ll_tab4);

		vp_content = (ViewPager) findViewById(R.id.vp_content);
	}

	private void initDatas() {
		fragments.add(0, new SellerOrder_All_Fragment(""));
		fragments.add(1, new SellerOrder_All_Fragment(orderState[1]));
		fragments.add(2, new SellerOrder_All_Fragment(orderState[2]));
		fragments.add(3, new SellerOrder_All_Fragment(orderState[3]));

		adapter = new FragmentViewPagerAdp(getSupportFragmentManager(),
				fragments);
		vp_content.setAdapter(adapter);
	}

	private void Listener() {
		ll_tab1.setOnClickListener(this);
		ll_tab2.setOnClickListener(this);
		ll_tab3.setOnClickListener(this);
		ll_tab4.setOnClickListener(this);
		changeUi(0);

		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				changeUi(position);
				curIndex = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public void initDateFirstFragment() {
		((SellerOrder_All_Fragment) fragments.get(curIndex)).getData();
	}

	@Override
	public void onClick(View v) {
		changeUi(v.getId());
	}

	private void changeUi(int i) {
		ll_tab1.setSelected(false);
		ll_tab2.setSelected(false);
		ll_tab3.setSelected(false);
		ll_tab4.setSelected(false);
		switch (i) {
		case R.id.ll_tab1:
		case 0:
			vp_content.setCurrentItem(0);
			ll_tab1.setSelected(true);
			break;
		case R.id.ll_tab2:
		case 1:
			vp_content.setCurrentItem(1);
			ll_tab2.setSelected(true);
			break;
		case R.id.ll_tab3:
		case 2:
			vp_content.setCurrentItem(2);
			ll_tab3.setSelected(true);
			break;
		case R.id.ll_tab4:
		case 3:
			vp_content.setCurrentItem(3);
			ll_tab4.setSelected(true);
			break;
		}
	}
}
