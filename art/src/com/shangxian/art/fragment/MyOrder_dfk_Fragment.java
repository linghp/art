package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.utils.MyLogger;

/**
 * 我的订单——待付款   暂时不用了
 * @author Administrator
 *
 */
public class MyOrder_dfk_Fragment extends Fragment {

	private View view;
	private TextView tv_empty;
	private ListView listView;
	private MyOrderListAdapter myOrderListAdapter;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public MyOrder_dfk_Fragment(List<MyOrderItem> mOrderItems) {
		super();
		this.mOrderItems = mOrderItems;
	}

	public void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame1,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);

		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		//myOrderListAdapter = new MyOrderListAdapter(getActivity(), mOrderItems);
		listView.setAdapter(myOrderListAdapter);
		MyLogger.i(mOrderItems.size() + "");
	}

	public BaseAdapter getAdaper() {
		return myOrderListAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLogger.i("");
		initMainView();
		return view;
	}
	
	public void update() {
		if(tv_empty!=null)
		if (mOrderItems.size() == 0 ) {
			tv_empty.setVisibility(View.VISIBLE);
		} else {
			tv_empty.setVisibility(View.GONE);
		}
	}
}
