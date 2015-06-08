package com.shangxian.art.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shangxian.art.MyOrderDetailsActivity;
import com.shangxian.art.R;
import com.shangxian.art.RefundOrderActivity;
import com.shangxian.art.SellerOrderReturnDetailsActivity;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.adapter.MyRefundOrderListAdapter;
import com.shangxian.art.adapter.SellerRefoundOrderAdapter;
import com.shangxian.art.bean.BaseBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.bean.RefundOrderInfo;
import com.shangxian.art.bean.RefundOrderInfo_all;
import com.shangxian.art.bean.Refund_stat;
import com.shangxian.art.bean.SellerRefoundOrderInfo;
import com.shangxian.art.bean.SellerRefoundstat;
import com.shangxian.art.constant.Global;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.MyOrderServer;
import com.shangxian.art.net.MyOrderServer.OnHttpResultListener;
import com.shangxian.art.net.MyOrderServer.OnHttpResultMoreListener;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.net.call.BaseCallBack;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.utils.MyLogger;

/**
 * 我的订单——全部以及其他状态（待付款，待发货等）
 * 
 * @author ling
 *
 */
public class SellerRefundOrder_All_Fragment extends BaseFragment implements
		OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	private View view;
	private ListView listView;
	private AbPullToRefreshView mAbPullToRefreshView;

	private SellerRefoundOrderAdapter myOrderListAdapter;
	private List<SellerRefoundOrderInfo> alls = new ArrayList<SellerRefoundOrderInfo>();
	private String status;

	protected SellerRefoundstat refoundstat;
	private SellerRefoundstat myOrderItem;
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (myOrderListAdapter != null) {
				myOrderListAdapter.removeItem(Global.sellerReFundOrder);
				Global.sellerReFundOrder = null;
			}
		};
	};

	Thread removeSllerRefoundOrderThread = new Thread(){
		public void run() {
			while (true){
				if (Global.sellerReFundOrder != null) {
					handler.sendEmptyMessage(0);
				} else {
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	};
	
	public void setNeedFresh(boolean isNeedFresh) {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		MyLogger.i("");
		super.onCreate(savedInstanceState);
		removeSllerRefoundOrderThread.start();
	}

	public SellerRefundOrder_All_Fragment(String status) {
		super();
		this.status = status;
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		// tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		// progressBar = (ProgressBar) view.findViewById(R.id.progress_order);
		mAbPullToRefreshView = (AbPullToRefreshView) view
				.findViewById(R.id.mPullRefreshView);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));

		myOrderListAdapter = new SellerRefoundOrderAdapter(getActivity(),
				R.layout.list_myorder_item, alls);
		listView.setAdapter(myOrderListAdapter);
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		// if (status.equals("")) {
		// ((RefundOrderActivity) getActivity()).initDateFirstFragment();
		// }
	}

	private void initListener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		// listView.setOnItemClickListener(this);
	}

	public void getData() {
		MyLogger.i("status: " + status);

		SellerOrderServer.toGetSellerReturnOrder(status, "0", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				mAbPullToRefreshView.onHeaderRefreshFinish();
				alls.clear();
				if (res != null) {
					changeUi(UiModel.showData);
					refoundstat = (SellerRefoundstat) res;
					if (refoundstat != null && !refoundstat.isNull()) {
						if (!refoundstat.isNull() && myOrderListAdapter != null) {
							myOrderListAdapter.upDateList(refoundstat.getData());
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
				mAbPullToRefreshView.onHeaderRefreshFinish();
				MyLogger.i("failure >>>>> " + code + " ,res >>>>> ");
			}
		});
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
		// if (tv_empty != null)
		if (alls.size() == 0) {
			// tv_empty.setVisibility(View.VISIBLE);
			changeUi(UiModel.noData_noCategory);
		} else {
			// tv_empty.setVisibility(View.GONE);
			changeUi(UiModel.noData_noCategory);
		}
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
		SellerOrderServer.toGetSellerReturnOrder(status, refoundstat.getStart()
				+ "", new CallBack() {
			@Override
			public void onSimpleSuccess(Object res) {
				mAbPullToRefreshView.onHeaderRefreshFinish();
				alls.clear();
				changeUi(UiModel.showData);
				if (res != null) {
					refoundstat = (SellerRefoundstat) res;
					if (refoundstat != null && !refoundstat.isNull()
							&& myOrderListAdapter != null) {
						myOrderListAdapter.addFootDataList(alls);
					}
				}
			}

			@Override
			public void onSimpleFailure(int code) {
				// changeUi(UiModel.);
				mAbPullToRefreshView.onHeaderRefreshFinish();
				MyLogger.i("failure >>>>> " + code + " ,res >>>>> ");
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			if (myOrderListAdapter != null) {
				myOrderListAdapter.removeItem(data.getIntExtra("res", Integer.MIN_VALUE));
			}
		}
	}
}
