package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.fragment.RefundOrder_All_Fragment;
import com.shangxian.art.view.TopView;

/**
 * 退款、订单
 * @author Administrator
 *
 */
public class RefundOrderActivity extends BaseActivity implements OnClickListener,OnPageChangeListener{
	private TopView topView;
    private ViewPager  mViewPager;

	private ArrayList<Fragment> fragments;
	private TextView tv_first, tv_second, tv_three, tv_four;
	private ImageView img_first, img_second, img_three, img_four;
	
	private Fragment firstFragment, secondFragment, thirdFragment, fourthFragment;
	private int currentPosion=0;

	private FragmentViewPagerAdp adapter;
	
//	@"未提交",@"PENDING",@"待付款",@"SUBMITTED",@"待发货",@"PAID",@"待收货",@"SHIPPING",@"已完成交易"
//	,@"COMPLETED",@"退款中",@"ORDER_RETURNING",@"待评价",@"EVALUATE",@"已取消交易",@"CANCELLED"
//                                                       0                     1                     2            3                      4                        5                               6                  7
	public static String[] orderState={"WAIT_SELLER_APPROVAL","WAIT_BUYER_DELIVERY","WAIT_COMPLETED"};
	public static String[] orderStateValue={"待审核","待退货","待退款"};
	public static Map<String, String> map_orderStateValue=new HashMap<String, String>();
	public static boolean isNeedRefresh;//当从订单详情继续点击处理再返回状态更新，onactivityresult难实现，故产生之。
	static{
		for (int i = 0; i < orderState.length; i++) {
			map_orderStateValue.put(orderState[i], orderStateValue[i]);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		
		initViews();
		listener();
		initViewPager();
        if (savedInstanceState != null) {
           // mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
	}
	@Override
	protected void onStart() {
		//((MyOrder_All_Fragment)firstFragment).getData();
		super.onStart();
	}
	
	private void listener() {
		tv_first.setOnClickListener(this);
		tv_second.setOnClickListener(this);
		tv_three.setOnClickListener(this);
		tv_four.setOnClickListener(this);
	}

	public void initDateFirstFragment(){
		((RefundOrder_All_Fragment)firstFragment).getData();
	}
	
//	public void updateData(){
//		MyLogger.i(""+currentPosion);
//		for (int i = 0; i < fragments.size(); i++) {
//			if(currentPosion!=i){
//				((MyOrder_All_Fragment)fragments.get(i)).getData();
//			}
//		}
//	}
	
	private void initViews() {

		tv_first = (TextView) findViewById(R.id.text_one);
		tv_second = (TextView) findViewById(R.id.text_two);
		tv_three = (TextView) findViewById(R.id.text_three);
		tv_four = (TextView) findViewById(R.id.text_four);
		tv_second.setText(orderStateValue[0]);
		tv_three.setText(orderStateValue[1]);
		tv_four.setText(orderStateValue[2]);
		
		img_first = (ImageView) findViewById(R.id.image_one);
		img_second = (ImageView) findViewById(R.id.image_two);
		img_three = (ImageView) findViewById(R.id.image_three);
		img_four = (ImageView) findViewById(R.id.image_four);
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_refundorder));
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("tab", mTabHost.getCurrentTabTag());
    }


	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.vp_content);

		fragments = new ArrayList<Fragment>();
		firstFragment = new RefundOrder_All_Fragment("");
		secondFragment = new RefundOrder_All_Fragment(orderState[0]);
		thirdFragment = new RefundOrder_All_Fragment(orderState[1]);
		fourthFragment = new RefundOrder_All_Fragment(orderState[2]);
//		thirdFragment = new MyOrder_dfh_Fragment();
//		fourthFragment = new MyOrder_dsh_Fragment();

		fragments.add(0, firstFragment);
		fragments.add(1, secondFragment);
		fragments.add(2, thirdFragment);
		fragments.add(3, fourthFragment);

		setBackground_slide(0);
		
		adapter = new FragmentViewPagerAdp(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(adapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setOnPageChangeListener(this);
	}

	private void setBackground_slide(int position) {
		img_first.setBackgroundResource(R.color.transparent);
		img_second.setBackgroundResource(R.color.transparent);
		img_three.setBackgroundResource(R.color.transparent);
		img_four.setBackgroundResource(R.color.transparent);

		tv_first.setTextColor(Color.parseColor("#333333"));
		tv_second.setTextColor(Color.parseColor("#333333"));
		tv_three.setTextColor(Color.parseColor("#333333"));
		tv_four.setTextColor(Color.parseColor("#333333"));

		switch (position) {
		case 0:
			img_first.setBackgroundResource(R.color.green);
			tv_first.setTextColor(getResources().getColor(R.color.green));
			//((MyOrder_All_Fragment)firstFragment).update();
			break;
		case 1:
			img_second.setBackgroundResource(R.color.green);
			tv_second.setTextColor(getResources().getColor(R.color.green));
			//((MyOrder_dfk_Fragment)secondFragment).update();
			break;
		case 2:
			img_three.setBackgroundResource(R.color.green);
			tv_three.setTextColor(getResources().getColor(R.color.green));
			//((MyOrder_dfh_Fragment)thirdFragment).update();
			break;
		case 3:
			img_four.setBackgroundResource(R.color.green);
			tv_four.setTextColor(getResources().getColor(R.color.green));
			//((MyOrder_dsh_Fragment)fourthFragment).update();
			break;
		}
	}

	@Override
	protected void onResume() {
		if(isNeedRefresh){
		((RefundOrder_All_Fragment)fragments.get(currentPosion)).getData();
		isNeedRefresh=false;
		}
		super.onResume();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_one:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.text_two:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.text_three:
			mViewPager.setCurrentItem(2);
			break;
		case R.id.text_four:
			mViewPager.setCurrentItem(3);
			break;
		}
	}
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
	}

	@Override
	public void onPageSelected(int position) {
		currentPosion=position;
		((RefundOrder_All_Fragment)fragments.get(position)).getData();
		setBackground_slide(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
}
