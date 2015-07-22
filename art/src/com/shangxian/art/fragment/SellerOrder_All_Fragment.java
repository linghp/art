package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.shangxian.art.SellerOrderActivity;
import com.shangxian.art.MyOrderDetailsActivity;
import com.shangxian.art.R;
import com.shangxian.art.SellerOrderDetailsActivity;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.adapter.MyOrderListAdapter1;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultMoreListener;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 我的订单——全部以及其他状态（待付款，待发货等）
 * 
 * @author ling
 *
 */
public class SellerOrder_All_Fragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	private View view;
	private ListView listView;
	private AbPullToRefreshView mAbPullToRefreshView;

	private MyOrderListAdapter1 myOrderListAdapter;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
	private MyOrderItem myOrderItem;
	private MyOrderItem_all all;
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

	public SellerOrder_All_Fragment(Context context,String status) {
		super();
		this.status = status;
		myOrderListAdapter = new MyOrderListAdapter1(context, this,
				mOrderItems);
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		// v_empty = view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		// progressBar = (ProgressBar) view.findViewById(R.id.progress_order);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// if (status.equals("")) {
		// ((SellerOrderActivity) getActivity()).initDateFirstFragment();
		// }
	}

	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		listView.setOnItemClickListener(this);
	}

	public void getData() {
		MyLogger.i("status: " + status);
		// MyOrderServer.toGetSellerOrder(status, this);
		SellerOrderServer.toGetSellerOrder(status, "0", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				MyLogger.i(">>>>>>>>>发货订单"+res);
				skip = 0;
				mAbPullToRefreshView.onHeaderRefreshFinish();//隐藏刷新控件
				if (res != null) {
					all = (MyOrderItem_all) res;
					if (!all.isNull()) {
						changeUi(UiModel.showData);
						mOrderItems.clear();
						mOrderItems.addAll(all.getData());
						// MyLogger.i(myOrderItemAll.getData().toString());
						myOrderListAdapter.notifyDataSetChanged();
					} else {
						changeUi(UiModel.noData_noProduct);
					}
				} else {
					changeUi(UiModel.noData_noProduct);
				}
			}

			@Override
			public void onSimpleFailure(int code) {
				//skip = 0;
				mAbPullToRefreshView.onHeaderRefreshFinish();//隐藏刷新控件
				changeUi(UiModel.noData_noProduct);
			}
		});
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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		changeUi(UiModel.loading);
		if (!isScrollListViewFresh) {
			// progressBar.setVisibility(View.VISIBLE);
			changeUi(UiModel.loading);
		}
		getData();
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
		// if (v_empty != null)
		if (mOrderItems.size() == 0) {
			// v_empty.setVisibility(View.VISIBLE);
			// listView.setVisibility(View.GONE);
			changeUi(UiModel.loading);
		} else {
			// v_empty.setVisibility(View.GONE);
			// listView.setVisibility(View.VISIBLE);
			changeUi(UiModel.showData);
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
		SellerOrderServer.toGetSellerOrder(status,
				skip+"", new CallBack() {
					@Override
					public void onSimpleSuccess(Object res) {
						mAbPullToRefreshView.onFooterLoadFinish();//隐藏上拉加载更多
						if (res != null) {
							all = (MyOrderItem_all) res;
							if (!all.isNull()) {
								changeUi(UiModel.showData);
								mOrderItems.addAll(all.getData());
								// MyLogger.i(myOrderItemAll.getData().toString());
								myOrderListAdapter.notifyDataSetChanged();
							} else {
								// changeUi(UiModel.noData_noProduct);
								CommonUtil.toast("已是最后一页", getActivity());
							}
						} else {
							skip -= pageSize;
							// changeUi(UiModel.noData_noProduct);
							CommonUtil.toast("已是最后一页", getActivity());
						}
					}

					@Override
					public void onSimpleFailure(int code) {
						skip -= pageSize;
						mAbPullToRefreshView.onFooterLoadFinish();//隐藏上拉加载更多
						CommonUtil.toast("加载失败");
//						changeUi(UiModel.noData_noProduct);
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//订单详情
		SellerOrderDetailsActivity.startThisActivity_MyOrder(
				mOrderItems.get(position).getOrderId() + "", position,getActivity());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		MyLogger.i(requestCode + "--" + resultCode);
		if (resultCode == getActivity().RESULT_OK) {
//			MyOrderItem myOrderItem_Return = (MyOrderItem) data
//					.getSerializableExtra("MyOrderItem");
//			MyLogger.i(myOrderItem_Return.toString());
//			if (myOrderItem != null && myOrderItem_Return != null) {
//				if (!myOrderItem.getStatus().equals(
//						myOrderItem_Return.getStatus())) {
//					myOrderItem.setStatus(myOrderItem_Return.getStatus());
//					myOrderListAdapter.notifyDataSetChanged();
//				}
//			}
//			super.onActivityResult(requestCode, resultCode, data);
			int index = data.getIntExtra("res", -1);
			removeItem(index);
		}
	}

//	public MyOrderItem getMyOrderItem() {
//		return myOrderItem;
//	}
//
//	public void setMyOrderItem(MyOrderItem myOrderItem) {
//		this.myOrderItem = myOrderItem;
//	}
	
	public void removeItem(int index){
		if (index >= 0 && mOrderItems != null && mOrderItems.size() > 0 && index < mOrderItems.size()) {
			mOrderItems.remove(index);
			if (myOrderListAdapter != null) {
				myOrderListAdapter.notifyDataSetChanged();
			}
		}
	}
}
