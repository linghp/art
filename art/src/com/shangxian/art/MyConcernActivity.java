package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.ShopsListModel;
import com.shangxian.art.fragment.MyConcern_Commodity_Fragment;
import com.shangxian.art.fragment.MyConcern_Shops_Fragment;
import com.shangxian.art.view.TopView;

/**
 * 我的关注
 * @author Administrator
 *
 */
public class MyConcernActivity extends BaseActivity implements OnClickListener,OnPageChangeListener{

	private ProgressBar progressBar;//加载
	private ViewPager  mViewPager;
	private ArrayList<Fragment> fragments;
	private TextView tv_first, tv_second;
	private ImageView img_first, img_second;

	private Fragment firstFragment, secondFragment;
	private List<ClassityCommdityModel> model = new ArrayList<ClassityCommdityModel>();
	private List<ShopsListModel> model_first = new ArrayList<ShopsListModel>();

	private FragmentViewPagerAdp adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myconcern);
		initViews();
		listener();
		initViewPager();
		getData();
	}

	private void initViews() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_my_concern));

		progressBar = (ProgressBar)findViewById(R.id.progress);//加载
		tv_first = (TextView) findViewById(R.id.text_one);//商品
		tv_second = (TextView) findViewById(R.id.text_two);//店铺

		img_first = (ImageView) findViewById(R.id.image_one);
		img_second = (ImageView) findViewById(R.id.image_two);

	}
	private void listener() {
		tv_first.setOnClickListener(this);
		tv_second.setOnClickListener(this);

	}

	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.vp_content);
		fragments = new ArrayList<Fragment>();
		firstFragment = new MyConcern_Commodity_Fragment();
		secondFragment = new MyConcern_Shops_Fragment();

		fragments.add(0, firstFragment);
		fragments.add(1, secondFragment);

		setBackground_slide(0);

		adapter = new FragmentViewPagerAdp(
				getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(adapter);
//		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(this);

	}
	private void setBackground_slide(int position) {
		img_first.setBackgroundResource(R.color.transparent);
		img_second.setBackgroundResource(R.color.transparent);

		tv_first.setTextColor(Color.parseColor("#333333"));
		tv_second.setTextColor(Color.parseColor("#333333"));

		switch (position) {
		case 0:
			img_first.setBackgroundResource(R.color.blue);
			tv_first.setTextColor(getResources().getColor(R.color.blue));
			//((MyConcern_Commodity_Fragment)firstFragment).update();
			break;
		case 1:
			img_second.setBackgroundResource(R.color.blue);
			tv_second.setTextColor(getResources().getColor(R.color.blue));
			//((MyConcern_Shops_Fragment)secondFragment).update();
			break;
		}
	}
	private void getData() {
		progressBar.setVisibility(View.GONE);//隐藏加载

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
		}	
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		setBackground_slide(position);
	}
}
