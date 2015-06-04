package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import com.ab.view.pullview.AbPullToRefreshView;
import com.shangxian.art.R;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.utils.MyLogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BaseFragment extends Fragment {
//	protected View view;
//	protected TextView tv_empty;
//	protected ListView listView;
//	protected ProgressBar progressBar;
//	protected MyOrderListAdapter myOrderListAdapter;
//	protected List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();
//	protected AbPullToRefreshView mAbPullToRefreshView;
//	private void initMainView() {
//		view = LayoutInflater.from(getActivity()).inflate(
//				R.layout.layout_main_action_frame,
//				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
//		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
//		listView = (ListView) view.findViewById(R.id.lv_action);
//		progressBar=(ProgressBar) view.findViewById(R.id.progress_order);
//		myOrderListAdapter = new MyOrderListAdapter(getActivity(), mOrderItems);
//		listView.setAdapter(myOrderListAdapter);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		MyLogger.i("");
//		initMainView();
//		return view;
//	}
	
	private View loading;
	private View noData;
	private View noWork;
	private ImageView iv_nodata;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		loading = view.findViewById(R.id.loading);
		noData = view.findViewById(R.id.no_data);
		if (view.findViewById(R.id.nodata_icon) != null) {
			iv_nodata = (ImageView) view.findViewById(R.id.nodata_icon);
		}
		noWork = view.findViewById(R.id.no_work);
	}
	
	protected enum UiModel{
		loading,
		noWork,
		showData,
		noData_noShop,
		noData_noProduct,
		noData_noCategory,
		noData_noMingxi,
		noData_noMsg,
		noData_noPingjia
	}
	
	protected void changeUi(UiModel model){ 
		if (loading == null && noData == null && noWork == null) {
			return;
		}
		loading.setVisibility(View.GONE);
		noData.setVisibility(View.GONE);
		noWork.setVisibility(View.GONE);
		switch (model) {
		case loading:
			loading.setVisibility(View.VISIBLE);
			break;
		case showData: break;
		case noWork:
			noWork.setVisibility(View.VISIBLE);
			break;
		case noData_noShop:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.noshop);
			break;
		case noData_noProduct:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.noproduct);
			break;
		case noData_noCategory:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.nocategory);
			break;
		case noData_noMingxi:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.nomingxi);
			break;
		case noData_noMsg:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.nomsg);
			break;
		case noData_noPingjia:
			noData.setVisibility(View.VISIBLE);
			iv_nodata.setImageResource(R.drawable.nopingjia);
			break;
		}
	}
}
