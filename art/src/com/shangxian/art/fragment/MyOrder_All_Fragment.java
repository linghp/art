package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
		OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {
	private View view;
	private View v_empty;
	private ListView listView;
	private ProgressBar progressBar;
	private AbPullToRefreshView mAbPullToRefreshView;

	private MyOrderListAdapter myOrderListAdapter;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private MyOrderItem myOrderItem;
	private String status;

	private boolean isScrollListViewFresh;// 旋转进度条显示与否
	private int skip = 0; // 从第skip+1条开始查询
	private final int pageSize = 10;

	private String returnStatus;// 当从订单详情继续点击处理再返回状态更新，onactivityresult难实现，故产生之。
	private String returnRefundStatus;// 退款状态
	public static final String DELETE = "delete";

	public void setNeedFresh(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public void setNeedFresh_Refund(String returnRefundStatus) {
		this.returnRefundStatus = returnRefundStatus;
	}

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
				R.layout.layout_main_action_frame1,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		v_empty = view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_order);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		myOrderListAdapter = new MyOrderListAdapter(getActivity(), this,
				mOrderItems);
		listView.setAdapter(myOrderListAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLogger.i("");
		initMainView();
		initListener();
		// getData();
		return view;
	}
	
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//		super.onViewCreated(view, savedInstanceState);
//		changeUi(UiModel.loading);
//	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (status.equals("")) {
			((MyOrderActivity) getActivity()).initDateFirstFragment();

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
			//changeUi(UiModel.loading);
		}
		MyLogger.i("status: " + status);
		MyOrderServer.toGetOrder(status, this);
	}

	@Override
	public void onStart() {
		MyLogger.i("");
		if (myOrderItem != null) {
			if (!TextUtils.isEmpty(returnStatus)) {// 当从订单详情继续点击处理再返回状态更新，onactivityresult难实现，故产生之。
				if (returnStatus.equals(DELETE)) {
					mOrderItems.remove(myOrderItem);
				} else {
					myOrderItem.setStatus(returnStatus);
				}
				myOrderListAdapter.notifyDataSetChanged();
				returnStatus = null;
			} else if (!TextUtils.isEmpty(returnRefundStatus)) {
				myOrderItem.getProductItemDtos().get(0)
						.setOrderItemStatus(returnRefundStatus);
				myOrderListAdapter.notifyDataSetChanged();
			}
		}
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

	public void updateView_nocontent() {
		if (v_empty != null)
			if (mOrderItems.size() == 0) {
				v_empty.setVisibility(View.VISIBLE);
				//listView.setVisibility(View.GONE);
				//changeUi(UiModel.noData_noProduct);
			} else {
				//v_empty.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				//changeUi(UiModel.showData);
			}
	}

	@Override
	public void onHttpResult(MyOrderItem_all myOrderItemAll) {
		isScrollListViewFresh = false;
		progressBar.setVisibility(View.GONE);
		
		mAbPullToRefreshView.onHeaderRefreshFinish();
		if (myOrderItemAll != null) {
			// MyLogger.i(myOrderItemAll.toString());
			//changeUi(UiModel.showData);
			skip = 0;
			mOrderItems.clear();
			if (myOrderItemAll.getData() != null) {
				mOrderItems.addAll(myOrderItemAll.getData());
				// MyLogger.i(myOrderItemAll.getData().toString());
				myOrderListAdapter.notifyDataSetChanged();
			}
			updateView_nocontent();
		} else {
			CommonUtil.toast("网络错误", getActivity());
			//changeUi(UiModel.noData_noProduct);
		}
	}

	@Override
	public void onHttpResultMore(MyOrderItem_all myOrderItemAll) {
		mAbPullToRefreshView.onFooterLoadFinish();
		if (myOrderItemAll != null) {
			List<MyOrderItem> myOrderItems = myOrderItemAll.getData();
			if (myOrderItems != null && myOrderItems.size() > 0) {
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
		skip += pageSize;
		String json = "{\"skip\":" + skip + ",\"pageSize\":" + pageSize + "}";
		MyOrderServer.toGetOrderMore(status, json, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		myOrderItem = mOrderItems.get(position);
		// Intent intent = new Intent(getActivity(),
		// MyOrderDetailsActivity.class);
		// intent.putExtra(MyOrderDetailsActivity.INTENTDATAKEY,
		// myOrderItem.getOrderNumber());
		// startActivityForResult(intent, 1);
		MyOrderDetailsActivity.startThisActivity_MyOrder(
				myOrderItem.getOrderNumber(), getActivity(), this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		MyLogger.i(requestCode + "--" + resultCode);
		if (resultCode == getActivity().RESULT_OK) {
			MyOrderItem myOrderItem_Return = (MyOrderItem) data
					.getSerializableExtra("MyOrderItem");
			if (myOrderItem != null && myOrderItem_Return != null) {
				if (!myOrderItem.getStatus().equals(
						myOrderItem_Return.getStatus())) {
					myOrderItem.setStatus(myOrderItem_Return.getStatus());
					myOrderListAdapter.notifyDataSetChanged();
				}
			}else if(data.getBooleanExtra("isdelete", false)){//订单详情中删除订单返回
				mOrderItems.remove(myOrderItem);
				myOrderListAdapter.notifyDataSetChanged();
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	public MyOrderItem getMyOrderItem() {
		return myOrderItem;
	}

	public void setMyOrderItem(MyOrderItem myOrderItem1) {
		if(myOrderItem!=null){//从我的订单付款-》订单详情->退款，越界异常，这里只设置状态就可以保证里面的商品不为空
			myOrderItem.setStatus(myOrderItem1.getStatus());
		}else{
		this.myOrderItem = myOrderItem1;
		}
	}

}
