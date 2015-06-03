package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.R;
import com.shangxian.art.adapter.FollowShopAdapter;
import com.shangxian.art.adapter.ShopsListAdapter;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.bean.ShopsListModel;
import com.shangxian.art.net.FollowServer;
import com.shangxian.art.net.FollowServer.OnFollowInfoListener;

/**
 * 我的关注——商铺
 * @author Administrator
 *
 */
public class MyConcern_Shops_Fragment extends BaseFragment{
	private View view;
	//private TextView tv_empty;
	private ListView listView;
	private FollowShopAdapter adapter;
	protected SearchProductInfo info;
	private AbPullToRefreshView mListRefreshView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initMainView();
		initData();
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		changeUi(UiModel.loading);
	}
	
	private void initData() {
		new FollowServer().toFollowList(true, new OnFollowInfoListener(){
			@Override
			public void onFollowInfo(SearchProductInfo info) {
				mListRefreshView.onHeaderRefreshFinish();
				mListRefreshView.onFooterLoadFinish();
				if (info != null && !info.isNull()) {
					changeUi(UiModel.showData);
					MyConcern_Shops_Fragment.this.info = info;
					if (adapter != null) {
						adapter.upDateList(info.getData());
					}
				} else {
					changeUi(UiModel.noData_noShop);
				}
			}
		});
	}
	
	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		//tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		mListRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);
		adapter = new FollowShopAdapter(getActivity(), R.layout.follow_shop_item, null);
		listView.setAdapter(adapter);
		
		mListRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView v) {
				initData();
			}
		});
		mListRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {	
			@Override
			public void onFooterLoad(AbPullToRefreshView v) {
				//loadMore();
				initData();
			}
		});
	}
}
