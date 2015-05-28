package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.MyOrderActivity;
import com.shangxian.art.MyOrderDetailsActivity;
import com.shangxian.art.R;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultMoreListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 我的订单——全部以及其他状态（待付款，待发货等）
 * 
 * @author ling
 *
 */
public class MyOrder_All_Fragment extends BaseFragment implements
		OnHttpResultListener, OnHttpResultMoreListener,
		OnHeaderRefreshListener, OnFooterLoadListener,OnItemClickListener { 
	private View view;
	private TextView tv_empty;
	private ListView listView;
	private ProgressBar progressBar;
	private AbPullToRefreshView mAbPullToRefreshView;

	private MyOrderListAdapter myOrderListAdapter;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private String status;

	private boolean isScrollListViewFresh;//旋转进度条显示与否
	private int skip = 0; //从第skip+1条开始查询
	private final int pageSize = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyLogger.i("");
		super.onCreate(savedInstanceState);
	}

	public MyOrder_All_Fragment(String status) {
		super();
		this.status = status;
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_order);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		myOrderListAdapter = new MyOrderListAdapter(getActivity(), mOrderItems);
		listView.setAdapter(myOrderListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLogger.i("");
		initMainView();
		initListener();
		//getData();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if(status.equals("")){
			((MyOrderActivity)getActivity()).initDateFirstFragment();  
			
			
		}
	}
	
	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		listView.setOnItemClickListener(this);
	}

	public void getData() {
		if (!isScrollListViewFresh) {
			progressBar.setVisibility(View.VISIBLE);
		}
		MyLogger.i("status: "+status);
		MyOrderServer.toGetOrder(status, this);
	}

	@Override
	public void onStart() {
		MyLogger.i("");
		super.onStart();
	}

	@Override
	public void onResume() {
		MyLogger.i("");
		super.onResume();
	}

	@Override
	public void onPause() {
		MyLogger.i("");
		super.onPause();
	}

	@Override
	public void onStop() {
		MyLogger.i("");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		MyLogger.i("");
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		MyLogger.i("");
		super.onDestroy();
	}

	public void update() {
		if (tv_empty != null)
			if (mOrderItems.size() == 0) {
				tv_empty.setVisibility(View.VISIBLE);
			} else {
				tv_empty.setVisibility(View.GONE);
			}
	}

	@Override
	public void onHttpResult(MyOrderItem_all myOrderItemAll) {
		isScrollListViewFresh = false;
		progressBar.setVisibility(View.GONE);
		mAbPullToRefreshView.onHeaderRefreshFinish();
		if (myOrderItemAll != null) {
			//MyLogger.i(myOrderItemAll.toString());
			skip = 0;
			mOrderItems.clear();
			if (myOrderItemAll.getData() != null) {
			mOrderItems.addAll(myOrderItemAll.getData());
			//MyLogger.i(myOrderItemAll.getData().toString());
			myOrderListAdapter.notifyDataSetChanged();
			}else{
				
			}
		} else {

		}
	}

	@Override
	public void onHttpResultMore(MyOrderItem_all myOrderItemAll) {
		mAbPullToRefreshView.onFooterLoadFinish();
		if (myOrderItemAll != null) {
			 List<MyOrderItem> myOrderItems=myOrderItemAll.getData();
			if (myOrderItems != null&&myOrderItems.size()>0) {
				mOrderItems.addAll(myOrderItems);
				myOrderListAdapter.notifyDataSetChanged();
			} else {
				CommonUtil.toast("已到最后一页", getActivity());
			}
		} else {
			
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		isScrollListViewFresh = true;
		getData();
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		isScrollListViewFresh = true;
		loadMore();
	}

	private void loadMore() {
		skip+=pageSize;
		String json = "{\"skip\":" + skip + ",\"pageSize\":" + pageSize
				+ "}";
		MyOrderServer.toGetOrderMore(status, json, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyLogger.i("onItemClick");
		MyOrderDetailsActivity.startThisActivity(mOrderItems.get(position).getOrderNumber(), getActivity());
	}
}
