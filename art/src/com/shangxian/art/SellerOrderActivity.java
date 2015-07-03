package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.fragment.SellerOrder_All_Fragment;
import com.shangxian.art.fragment.SellerRefundOrder_All_Fragment;
import com.shangxian.art.view.TopView;

/**
 * 发货订单和退货订单
 * @author Administrator
 *
 */
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
	private int curOrderType;
	private TextView tv_tab2;
	private TextView tv_tab3;
	private TextView tv_tab4;

	public static String[] orderState = { "PENDING", "SUBMITTED", "PAID",
			"SHIPPING", "COMPLETED", "ORDER_RETURNING", "EVALUATE", "CANCELLED" };
	
	public static String[] orderStateValue = { "未提交", "待付款", "待发货", "待收货",
			"已完成交易", "退款中", "待评价", "已取消交易" };
    
	public static String[] orderReturnStatus = { "NORMAL", "SUCCESS",
		"WAIT_SELLER_APPROVAL", "WAIT_BUYER_DELIVERY", "WAIT_COMPLETED",
		"COMPLETED_REFUSE", "ORDER_RETURNING", "CANCELLED", "FAILURE" };

	public static String[] orderReturnStatusValue = { "正常，不退货", "退款成功",
		"待审核", "待退货", "待退款", "卖家拒绝签收", "已签收，退款成功", "取消",
		"退货失败" };
//	public static String[] orderReturnStatusValue = { "正常，不退货", "退款成功",
//		"等待卖家审核", "等待买家退货", "买家已发货,等待卖家签收", "卖家拒绝签收", "已签收，退款成功", "取消",
//	"退货失败" };

	public static Map<String, String> map_orderStateValue = new HashMap<String, String>();
	public static Map<String, String> map_orderReturnStatusValue = new HashMap<String, String>();
	static {
		for (int i = 0; i < orderState.length; i++) {
			map_orderStateValue.put(orderState[i], orderStateValue[i]);
		}
		for (int i = 0; i < orderReturnStatus.length; i++) {
			map_orderReturnStatusValue.put(orderReturnStatus[i],
					orderReturnStatusValue[i]);
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
	
	private static final String ISORDERSEND = "isOrderSend";
	private static final int ORDER_SEND = 0x00000001;
	private static final int ORDER_RETURN = 0x00000002;
	
	public static void startThisActivity(Activity mAc, Boolean isOrderSend){
		Bundle bundle = new Bundle();
		bundle.putInt(ISORDERSEND, isOrderSend ? ORDER_SEND : ORDER_RETURN);
		Intent intent = new Intent(mAc, SellerOrderActivity.class);
		mAc.startActivity(intent.putExtras(bundle));
	}

	private void initViews() {
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		

		ll_tab1 = (LinearLayout) findViewById(R.id.ll_tab1);
		ll_tab2 = (LinearLayout) findViewById(R.id.ll_tab2);
		ll_tab3 = (LinearLayout) findViewById(R.id.ll_tab3);
		ll_tab4 = (LinearLayout) findViewById(R.id.ll_tab4);
		
		tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
		tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
		tv_tab4 = (TextView) findViewById(R.id.tv_tab4);
		
		vp_content = (ViewPager) findViewById(R.id.vp_content);
	}

	private void initDatas() {
		int toOrder = getIntent().getIntExtra(ISORDERSEND, Integer.MIN_VALUE);
		if (toOrder == Integer.MIN_VALUE) {
			myToast("请求参数错误");
			finish();
		}
		curOrderType = toOrder;
			
		if (isSendOrder()) {
			tv_tab2.setText("待付款");
			tv_tab3.setText("待发货");
			tv_tab4.setText("待收货");
			topView.setTitle("发货订单管理");
			fragments.add(0, new SellerOrder_All_Fragment(this,""));
			fragments.add(1, new SellerOrder_All_Fragment(this,orderState[1]));
			fragments.add(2, new SellerOrder_All_Fragment(this,orderState[2]));
			fragments.add(3, new SellerOrder_All_Fragment(this,orderState[3]));
		} else {
			tv_tab2.setText("待审核");
			tv_tab3.setText("待退货");
			tv_tab4.setText("待退款");
			topView.setTitle("退货订单管理");
			fragments.add(0, new SellerRefundOrder_All_Fragment(this,""));
			fragments.add(1, new SellerRefundOrder_All_Fragment(this,orderReturnStatus[2]));
			fragments.add(2, new SellerRefundOrder_All_Fragment(this,orderReturnStatus[3]));
			fragments.add(3, new SellerRefundOrder_All_Fragment(this,orderReturnStatus[4]));
		}

		adapter = new FragmentViewPagerAdp(getSupportFragmentManager(),
				fragments);
		vp_content.setAdapter(adapter);
	}

	private boolean isSendOrder(){
		return curOrderType == ORDER_SEND;
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