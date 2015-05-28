package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.R;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.net.FollowServer;
import com.shangxian.art.net.FollowServer.OnFollowInfoListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的关注——商品
 * @author Administrator
 *
 */
public class MyConcern_Commodity_Fragment extends Fragment{
	private View view;
	private TextView tv_empty;
	private ListView listView;
	private ClassityCommodiyAdp adapter;
	private SearchProductInfo info;
	private AbPullToRefreshView mListRefreshView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initMainView();	
		return view;
	}
	
	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		mListRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);
		
		adapter = new ClassityCommodiyAdp(getActivity(), R.layout.item_classitycommodity, null);
		listView.setAdapter(adapter);
		toLoad();
		
		mListRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView v) {
				toLoad();
			}
		});
		mListRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {	
			@Override
			public void onFooterLoad(AbPullToRefreshView v) {
				//loadMore();
				toLoad();
			}
		});
	}
	
	private void toLoad(){
		new FollowServer().toFollowList(false, new OnFollowInfoListener (){
			@Override
			public void onFollowInfo(SearchProductInfo info) {
				mListRefreshView.onHeaderRefreshFinish();
				mListRefreshView.onFooterLoadFinish();
				if (info != null && !info.isNull()) {
					MyConcern_Commodity_Fragment.this.info = info;
					if (adapter != null) {
						adapter.upDateList(info.getData());
					}
				} else {
					
				}
			}
		});
	}
	
	private void loadMore(){
		
	}
}
