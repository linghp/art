package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.adapter.CommentToAdapter;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.net.CommentServer;
import com.shangxian.art.net.CommentServer.OnHttpResultListener;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * @Description 商品评价（我的待评价已评价）
 * @author ling
 * @date 2015年6月11日
 */
public class CommentToActivity extends BaseActivity implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnClickListener,OnHttpResultListener {

	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mListView = null;
	private View ll_nonetwork, loading_big;

	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private CommentToAdapter myListViewAdapter = null;

	private TextView tv_reload;
	private int skip = 0; // 从第skip+1条开始查询
	private final int pageSize = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_to_refresh_list);
		initviews();

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);

		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		// ListView数据
		// list = new ArrayList<Map<String, Object>>();

		// 使用自定义的Adapter
		myListViewAdapter = new CommentToAdapter(this, mOrderItems);
		mListView.setAdapter(myListViewAdapter);

		// item被点击事件
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});

		refreshTask();
	}

	private void initviews() {
		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		mListView = (ListView) this.findViewById(R.id.mListView);
		ll_nonetwork = findViewById(R.id.ll_nonetwork);
		loading_big = findViewById(R.id.loading_big);
		
		topView = (TopView) findViewById(R.id.top_title);
		topView.setVisibility(View.VISIBLE);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.mine_goodscomment));
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMoreTask();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		refreshTask();
	}

	private int curPage = 0;
	private void refreshTask() {
		if (!HttpUtils.checkNetWork(mAc) ) {
			ll_nonetwork.setVisibility(View.VISIBLE);
			return;
		}
		CommentServer.toPostMyComment(this);
//		new NearlyServer().toNearlyShop(lng, 10000, 0,
//				new OnNearlyShopListener() {
//					@Override
//					public void onNearly(NearlyShopStat stat) {
//						curPage = 0;
//						MyLogger.i(stat != null ? stat.toString() : "null");
//						if (stat != null && !stat.isNull()) {
//							CommentToActivity.this.stat = stat;
//							if (myListViewAdapter != null) {
//								hideNoData();
//								mListView.setVisibility(View.VISIBLE);
//								myListViewAdapter
//										.upDateList(CommentToActivity.this.stat
//												.getContents());
//								mAbPullToRefreshView.onHeaderRefreshFinish();
//							} else {
//								showNoData(NoDataModel.noShop);
//							}
//						} else {
//							mListView.setVisibility(View.GONE);
//							//ll_nonetwork.setVisibility(View.VISIBLE);
//							showNoData(NoDataModel.noShop);
//						}
//					}
//				});
	}

	public void loadMoreTask() {
		if (!HttpUtils.checkNetWork(mAc)) {
			return;
		}
//		new NearlyServer().toNearlyShop(lng, 10000, ++ curPage,
//				new OnNearlyShopListener() {
//					@Override
//					public void onNearly(NearlyShopStat stat) {
//						MyLogger.i(stat != null ? stat.toString() : "null");
//						if (stat != null && !stat.isNull()) {
//							CommentToActivity.this.stat = stat;
//							if (myListViewAdapter != null) {
//								myListViewAdapter
//										.addFootDataList(CommentToActivity.this.stat
//												.getContents());
//								mAbPullToRefreshView.onFooterLoadFinish();
//							}
//						}
//					}
//				});
		mAbPullToRefreshView.onFooterLoadFinish();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_reload:
			mAbPullToRefreshView.setVisibility(View.GONE);
			ll_nonetwork.setVisibility(View.GONE);
			loading_big.setVisibility(View.VISIBLE);
			refreshTask(); 
			break;
		default:
			break;
		}
	}

	@Override
	public void onHttpResult(MyOrderItem_all myOrderItemAll) {
		loading_big.setVisibility(View.GONE);
		
		mAbPullToRefreshView.onHeaderRefreshFinish();
		if (myOrderItemAll != null) {
			// MyLogger.i(myOrderItemAll.toString());
			//changeUi(UiModel.showData);
			mAbPullToRefreshView.setVisibility(View.VISIBLE);
			skip = 0;
			mOrderItems.clear();
			if (myOrderItemAll.getData() != null) {
				mOrderItems.addAll(myOrderItemAll.getData());
				// MyLogger.i(myOrderItemAll.getData().toString());
				myListViewAdapter.notifyDataSetChanged();
			}
			//updateView_nocontent();
		} else {
			CommonUtil.toast("网络错误", this);
			//changeUi(UiModel.noData_noProduct);
		}
	}

}
