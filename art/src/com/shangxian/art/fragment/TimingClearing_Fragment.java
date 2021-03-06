package com.shangxian.art.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.R;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.net.FollowServer;
import com.shangxian.art.net.FollowServer.OnFollowInfoListener;

/**
 * 定时结算
 * @author Administrator
 *
 */
public class TimingClearing_Fragment extends BaseFragment{
	private View view;
	//private TextView tv_empty;
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
		initlistener();
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		changeUi(UiModel.loading);
	}

	@Override
	public void onResume() {
		super.onResume();
		toLoad();
	}
	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_jiesuanlishi_fragment,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		//tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		mListRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);

		adapter = new ClassityCommodiyAdp(getActivity(), R.layout.item_classitycommodity, null);
		listView.setAdapter(adapter);

	}

	private void initlistener() {
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
					changeUi(UiModel.showData);
					TimingClearing_Fragment.this.info = info;
					if (adapter != null) {
						adapter.upDateList(info.getData());
					}
				} else {
					changeUi(UiModel.noData_noProduct);
				}
			}
		});
	}

	private void loadMore(){

	}
}