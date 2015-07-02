package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.BuyerReturnOrderActivity;
import com.shangxian.art.R;
import com.shangxian.art.ReturnOrderDetailsActivity;
import com.shangxian.art.adapter.BuyerReturnOrderAdapter;
import com.shangxian.art.bean.BuyerReturnOrderInfo;
import com.shangxian.art.bean.BuyerReturnOrderStat;
import com.shangxian.art.net.BuyerOrderServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

/**
 * 买家退货订单详情
 * 
 * @author libo
 */
public class BuyerReturnOrderFragment extends BaseFragment implements
OnHeaderRefreshListener, OnFooterLoadListener{
	private View view;
	private ListView listView;
	private AbPullToRefreshView mAbPullToRefreshView;

	private BuyerReturnOrderAdapter buyerReturnOrderAdapter;
	private String status;
	private BuyerReturnOrderStat buyerReturnOrderStat;
	private BuyerOrderServer server;
	private List<BuyerReturnOrderInfo> dates=new ArrayList<BuyerReturnOrderInfo>();

	public void setNeedFresh(boolean isNeedFresh) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyLogger.i("");
		super.onCreate(savedInstanceState);
	}

	public BuyerReturnOrderFragment(Activity activity,String status) {
		super();
		this.status = status;
		buyerReturnOrderAdapter = new BuyerReturnOrderAdapter(activity,this,R.layout.list_myorder_item, dates);
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

		listView.setAdapter(buyerReturnOrderAdapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initMainView();
		initListener();
		server = new BuyerOrderServer();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (status.equals("")) {
			((BuyerReturnOrderActivity) getActivity()).initDateFirstFragment();

		}
	}


	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MyLogger.i("退款订单》》》》》》》》"+buyerReturnOrderStat.getData().get(position).getBuyerId());
//				 Bundle bundle = new Bundle();
//				 bundle.putString("time", buyerReturnOrderStat.getData().get(position).getReturnOrderTime());
//				 CommonUtil.gotoActivityWithData(BuyerReturnOrderFragment.this, ReturnOrderDetailsActivity.class, bundle, false);
			ReturnOrderDetailsActivity.startThisActivity(buyerReturnOrderStat.getData().get(position).getReturnOrderTime()//退款时间
					, buyerReturnOrderStat.getData().get(position).getStatus()//退款状态
					, buyerReturnOrderStat.getData().get(position).getStatus()//货物状态
					, buyerReturnOrderStat.getData().get(position).getReturnOrderTime()//是否需要退还货物
					, "¥"+CommonUtil.priceConversion(buyerReturnOrderStat.getData().get(position).getTotalPrice())//退还金额
					, buyerReturnOrderStat.getData().get(position).getReturnOrderTime()//退款原因
					, buyerReturnOrderStat.getData().get(position).getReturnOrderTime()//退款说明
					, getActivity());
			MyLogger.i("退款订单》》》》》》》》"+buyerReturnOrderStat.getData().get(position).getReturnOrderTime());
			}
		});
	}

	
	
	public void getData() {
		MyLogger.i("status:》》》》》》》》》》 " + status);
		
		server.toBuyerReturnList(status, "0", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				mAbPullToRefreshView.onHeaderRefreshFinish();//隐藏刷新控件
				MyLogger.i("退款订单>>>>>>>>"+res);

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
				mAbPullToRefreshView.onHeaderRefreshFinish();//隐藏刷新控件
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
		server.toBuyerReturnList(status, buyerReturnOrderStat.getStart() + "", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				mAbPullToRefreshView.onFooterLoadFinish();//隐藏上拉加载更多
				MyLogger.i("退款订单>>>>>>>>"+res);
//				if (res != null) {
//					buyerReturnOrderStat = (BuyerReturnOrderStat) res;
//					if (!buyerReturnOrderStat.isNull()) {
//						if (buyerReturnOrderAdapter != null) {
//							changeUi(UiModel.showData);
//							buyerReturnOrderAdapter.addFootDataList(buyerReturnOrderStat.getData());
//						}
//					} else {
//						CommonUtil.toast("已是最后一页");
//
//					}
//				} else {
//					CommonUtil.toast("已是最后一页");
//				}
				if (res != null) {
					buyerReturnOrderStat = (BuyerReturnOrderStat) res;
					if (!buyerReturnOrderStat.isNull()) {
						if (buyerReturnOrderAdapter != null) {
							changeUi(UiModel.showData);
							buyerReturnOrderAdapter.upDateList(buyerReturnOrderStat.getData());
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
				mAbPullToRefreshView.onFooterLoadFinish();//隐藏上拉加载更多
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
