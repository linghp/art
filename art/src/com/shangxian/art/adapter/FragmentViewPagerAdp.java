package com.shangxian.art.adapter;


import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


public class FragmentViewPagerAdp extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private FragmentManager fragmentManager;
	
	public FragmentViewPagerAdp(FragmentManager fragmentManager,
			 List<Fragment> fragments) {
		super(fragmentManager);
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
	}
	
	@Override
	public Fragment getItem(int position) {
	
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return fragments.size();
	}

	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	

}
