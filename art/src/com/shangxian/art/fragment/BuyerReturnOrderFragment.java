package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.R;
import com.shangxian.art.adapter.BuyerReturnOrderAdapter;
import com.shangxian.art.adapter.SellerRefoundOrderAdapter;
import com.shangxian.art.bean.BuyerReturnOrderStat;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundstat;
import com.shangxian.art.constant.Global;
import com.shangxian.art.fragment.BaseFragment.UiModel;
import com.shangxian.art.net.BuyerOrderServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 买家退货订单详情
 * 
 * @author libo
 */
public class BuyerReturnOrderFragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterLoadListener {
	private View view;
	private ListView listView;
	private AbPullToRefreshView mAbPullToRefreshView;

	private BuyerReturnOrderAdapter buyerReturnOrderAdapter;
	private String status;
	private BuyerReturnOrderStat buyerReturnOrderStat;

	public void setNeedFresh(boolean isNeedFresh) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyLogger.i("");
		super.onCreate(savedInstanceState);
	}

	public BuyerReturnOrderFragment(String status) {
		super();
		this.status = status;
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		listView = (ListView) view.findViewById(R.id.lv_action);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		buyerReturnOrderAdapter = new BuyerReturnOrderAdapter(this, R.layout.list_myorder_item, null);
		listView.setAdapter(buyerReturnOrderAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLogger.i("");
		initMainView();
		initListener();
		getData();
		return view;
	}

	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
	}

	public void getData() {
		MyLogger.i("status: " + status);
		new BuyerOrderServer().toBuyerReturnList(status, "0", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					buyerReturnOrderStat = (BuyerReturnOrderStat) res;
					if (!buyerReturnOrderStat.isNull()) {
						if (buyerReturnOrderAdapter != null) {
							changeUi(UiModel.showData);
							buyerReturnOrderAdapter.upDateList(buyerReturnOrderStat.getData());
						}
					} else {
						changeUi(UiModel.noData_noProduct);
					}
				} else {
					changeUi(UiModel.noData_noProduct);
				}
			}
			@Override
			public void onSimpleFailure(int code) {
				changeUi(UiModel.noData_noProduct);
			}
		});
	}

	@Override
	public void onDestroy() {
		MyLogger.i("");
		super.onDestroy();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getData();
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMore();
	}

	private void loadMore() {
		new BuyerOrderServer().toBuyerReturnList(status, buyerReturnOrderStat.getStart() + "", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					buyerReturnOrderStat = (BuyerReturnOrderStat) res;
					if (!buyerReturnOrderStat.isNull()) {
						if (buyerReturnOrderAdapter != null) {
							changeUi(UiModel.showData);
							buyerReturnOrderAdapter.addFootDataList(buyerReturnOrderStat.getData());
						}
					} else {
						CommonUtil.toast("已是最后一页");
					}
				} else {
					CommonUtil.toast("已是最后一页");
				}
			}
			@Override
			public void onSimpleFailure(int code) {
				CommonUtil.toast("加载失败");
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			if (buyerReturnOrderAdapter != null) {
				buyerReturnOrderAdapter.removeDataItem(data.getIntExtra("res",
						Integer.MIN_VALUE));
			}
		}
	}
}
