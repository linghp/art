package com.shangxian.art.adapter;


import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;


public class FragmentViewPagerAdp extends PagerAdapter implements OnPageChangeListener {

	private List<Fragment> fragments;
	private FragmentManager fragmentManager;
	private ViewPager viewPager;
	private int currentPageIndex = 0;
	private OnExtraPageChangeListener onExtraPageChangeListener;
	
	public FragmentViewPagerAdp(FragmentManager fragmentManager,
			ViewPager viewPager, List<Fragment> fragments) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
//		viewPager.setOffscreenPageLimit(2);
		//viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		this.viewPager.setOnPageChangeListener(this);
	}
	
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(fragments.get(position).getView());
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			ft.commitAllowingStateLoss();
			fragmentManager.executePendingTransactions();
		}

		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView());
		}

		return fragment.getView();
	}

	@Override
	public void onPageScrollStateChanged(int i) {
		if (null != onExtraPageChangeListener) {
			onExtraPageChangeListener.onExtraPageScrollStateChanged(i);
		}
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {
		if (null != onExtraPageChangeListener) {
			onExtraPageChangeListener.onExtraPageScrolled(i, v, i2);
		}
	}

	@Override
	public void onPageSelected(int i) {
		fragments.get(currentPageIndex).onPause();
		// fragments.get(currentPageIndex).onStop();
		if (fragments.get(i).isAdded()) {
			// fragments.get(i).onStart();
			fragments.get(i).setUserVisibleHint(true);
			fragments.get(i).onResume();
		}
		currentPageIndex = i;

		if (null != onExtraPageChangeListener) {
			onExtraPageChangeListener.onExtraPageSelected(i);
		}
		
	}
	
	public OnExtraPageChangeListener getOnExtraPageChangeListener() {
		return onExtraPageChangeListener;
	}

	public void setOnExtraPageChangeListener(
			OnExtraPageChangeListener onExtraPageChangeListener) {
		this.onExtraPageChangeListener = onExtraPageChangeListener;
	}
	
	public static class OnExtraPageChangeListener {
		public void onExtraPageScrolled(int i, float v, int i2) {
		}

		public void onExtraPageSelected(int i) {
		}

		public void onExtraPageScrollStateChanged(int i) {
		}
	}

}
