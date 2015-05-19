package com.shangxian.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.fragment.MyOrder_dfh_Fragment;
import com.shangxian.art.fragment.MyOrder_dfk_Fragment;
import com.shangxian.art.fragment.MyOrder_dsh_Fragment;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 我的订单
 * @author Administrator
 *
 */
public class MyOrderActivity extends BaseActivity implements OnClickListener,HttpCilentListener,OnPageChangeListener{
	private TopView topView;
    private ViewPager  mViewPager;
    private ProgressBar progressBar;

	private ArrayList<Fragment> fragments;
	private TextView tv_first, tv_second, tv_three, tv_four;
	private ImageView img_first, img_second, img_three, img_four;
	
	private Fragment firstFragment, secondFragment, thirdFragment, fourthFragment;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private List<MyOrderItem> mOrderItems_dfk = new ArrayList<MyOrderItem>();
	private List<MyOrderItem> mOrderItems_dfh = new ArrayList<MyOrderItem>();
	private List<MyOrderItem> mOrderItems_dsh = new ArrayList<MyOrderItem>();
	private FragmentViewPagerAdp adapter;
	
//	@"未提交",@"PENDING",@"待付款",@"SUBMITTED",@"待发货",@"PAID",@"待收货",@"SHIPPING",@"已完成交易"
//	,@"COMPLETED",@"退款中",@"ORDER_RETURNING",@"待评价",@"EVALUATE",@"已取消交易",@"CANCELLED"
	public static String[] orderState={"PENDING","SUBMITTED","PAID","SHIPPING","COMPLETED","ORDER_RETURNING","EVALUATE","CANCELLED"};
	private String[] orderStateValue={"未提交","待付款","待发货","待收货","已完成交易","退款中","待评价","已取消交易"};
	private Map<String, String> map_orderStateValue=new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		
		initViews();
		initdata();
		listener();
		initViewPager();
        if (savedInstanceState != null) {
           // mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        getData();
	}
	
	// 获取data
	public void getData() {
			progressBar.setVisibility(View.VISIBLE);
			HttpClients.postDo(Constant.BASEURL + Constant.CONTENT
					+ Constant.ORDERS, "", this);
	}

	private void listener() {
		tv_first.setOnClickListener(this);
		tv_second.setOnClickListener(this);
		tv_three.setOnClickListener(this);
		tv_four.setOnClickListener(this);
	}

	private void initdata() {
		for (int i = 0; i < orderState.length; i++) {
			map_orderStateValue.put(orderState[i], orderStateValue[i]);
		}
	}

	private void initViews() {
		progressBar = (ProgressBar)findViewById(R.id.progress);

		tv_first = (TextView) findViewById(R.id.text_one);
		tv_second = (TextView) findViewById(R.id.text_two);
		tv_three = (TextView) findViewById(R.id.text_three);
		tv_four = (TextView) findViewById(R.id.text_four);

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
		topView.setTitle(getString(R.string.title_activity_my_order));
	}
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("tab", mTabHost.getCurrentTabTag());
    }


	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.vp_content);

		fragments = new ArrayList<Fragment>();
		firstFragment = new MyOrder_All_Fragment(mOrderItems);
		secondFragment = new MyOrder_dfk_Fragment(mOrderItems_dfk);
		thirdFragment = new MyOrder_dfh_Fragment();
		fourthFragment = new MyOrder_dsh_Fragment();

		fragments.add(0, firstFragment);
		fragments.add(1, secondFragment);
		fragments.add(2, thirdFragment);
		fragments.add(3, fourthFragment);

		setBackground_slide(0);
		
		adapter = new FragmentViewPagerAdp(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(adapter);
		//mViewPager.setOffscreenPageLimit(3);
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
			img_first.setBackgroundResource(R.color.blue);
			tv_first.setTextColor(getResources().getColor(R.color.blue));
			((MyOrder_All_Fragment)firstFragment).update();
			break;
		case 1:
			img_second.setBackgroundResource(R.color.blue);
			tv_second.setTextColor(getResources().getColor(R.color.blue));
			((MyOrder_dfk_Fragment)secondFragment).update();
			break;
		case 2:
			img_three.setBackgroundResource(R.color.blue);
			tv_three.setTextColor(getResources().getColor(R.color.blue));
			((MyOrder_dfh_Fragment)thirdFragment).update();
			break;
		case 3:
			img_four.setBackgroundResource(R.color.blue);
			tv_four.setTextColor(getResources().getColor(R.color.blue));
			((MyOrder_dsh_Fragment)fourthFragment).update();
			break;
		}
	}

	@Override
	protected void onResume() {
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
	public void onResponse(String content) {
		MyLogger.i(content);
		progressBar.setVisibility(View.GONE);
		if (!TextUtils.isEmpty(content)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				String result_code = jsonObject.getString("result_code");
				if (result_code.equals("200")) {
					if (jsonObject.getString("reason").equals("success")) {
						String json = jsonObject.getString("result");
						Gson gson = new Gson();
						MyOrderItem_all myOrderItem_all = gson.fromJson(json,
								MyOrderItem_all.class);
						MyLogger.i(myOrderItem_all.toString());
						if (myOrderItem_all != null) {
//							for (LinkedHashMap<String, MyOrderItem> lhp_myOrderItem : myOrderItem_all
//									.getResult()) {
//								for (MyOrderItem myOrderItem : lhp_myOrderItem.values()) {
//									String status=myOrderItem.getStatus();
//									myOrderItem.stateValue=map_orderStateValue.get(status);
//									mOrderItems.add(myOrderItem);
//									if(status.equals(orderState[1])){//待付款
//										mOrderItems_dfk.add(myOrderItem);
//									}else if(status.equals(orderState[2])){//待发货
//										mOrderItems_dfh.add(myOrderItem);
//									}else if(status.equals(orderState[3])){//待收货
//										mOrderItems_dsh.add(myOrderItem);
//									}
//								}
//							}
							for (MyOrderItem myOrderItem : myOrderItem_all.getData()) {
							String status=myOrderItem.getStatus();
							myOrderItem.stateValue=map_orderStateValue.get(status);
							mOrderItems.add(myOrderItem);
							if(status.equals(orderState[1])){//待付款
								mOrderItems_dfk.add(myOrderItem);
							}else if(status.equals(orderState[2])){//待发货
								mOrderItems_dfh.add(myOrderItem);
							}else if(status.equals(orderState[3])){//待收货
								mOrderItems_dsh.add(myOrderItem);
							}
						}
//							List<MyOrderItem> myOrderItems_temp=(List<MyOrderItem>) (myOrderItem_all
//									.getResult().get(0).values());
							MyLogger.i(mOrderItems_dfk.size()+"");
							adapter.notifyDataSetChanged();
						}
						//initViewPager();
					}
					// myToast(jsonObject.getString("result"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		((MyOrder_All_Fragment)firstFragment).update();
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		setBackground_slide(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
}
