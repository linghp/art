package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ab.view.pullview.AbPullToRefreshView;
import com.shangxian.art.adapter.SearchAdapter;
/**
 * 搜索界面
 */
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.SearchModel;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.net.SearchServer;
import com.shangxian.art.net.SearchServer.OnSearchProductListener;
import com.shangxian.art.view.TopView;

/**
 * 搜索
 * 
 * @author Administrator
 *
 */
public class SearchActivity extends BaseActivity {
	ListView list;
	List<SearchModel> model;
	SearchAdapter adapter;
	private AbPullToRefreshView mAbPullToRefreshView;
	private ListView mListView;
	private View ll_nonetwork;
	private View loading_big;
	private LinearLayout footLayout;
	private LinearLayout l_laoding;
	private LinearLayout l_toLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();
		listener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightText("取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});

		topView.showCenterSearch();
		topView.hideLeftBtnz();// 隐藏左按钮
		topView.hideRightBtn_invisible();
		list = (ListView) findViewById(R.id.search_list);
		addFoot();
	}

	private void addFoot() {
		footLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.search_loadmore, null);
		l_laoding = (LinearLayout) footLayout
				.findViewById(R.id.search_loadmore_loading);
		l_toLoad = (LinearLayout) footLayout
				.findViewById(R.id.search_loadmore_toload);
		l_toLoad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		list.addFooterView(footLayout);
	}

	
	
	private void initData() {
		model = new ArrayList<SearchModel>();
		for (int i = 0; i < 5; i++) {
			SearchModel m = new SearchModel();
			m.setTitle("特价美食" + (1 + i));
			model.add(m);
		}
		// adapter = new SearchAdapter(this, R.layout.item_search, model);
		// list.setAdapter(adapter);
	}

	private void listener() {

	}
}
