package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.shangxian.art.R;
import com.shangxian.art.adapter.MyOrderListAdapter;
import com.shangxian.art.bean.ListCarStoreBean;
import com.shangxian.art.bean.MyOrderItem;
import com.shangxian.art.bean.MyOrderItem_all;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.MyLogger;

public class MyOrder_dfh_Fragment extends Fragment implements HttpCilentListener {

	private View view;
	private ProgressBar progressBar;
	private ListView listView;
	private MyOrderListAdapter myOrderListAdapter;
	private List<MyOrderItem> mOrderItems = new ArrayList<MyOrderItem>();

	private boolean hasActivitiesData = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMainView();
	}

	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);

		progressBar = (ProgressBar) view.findViewById(R.id.progress);

		listView = (ListView) view.findViewById(R.id.lv_action);
		myOrderListAdapter = new MyOrderListAdapter(getActivity(), mOrderItems);
		listView.setAdapter(myOrderListAdapter);

		//getActivitiesData();
	}



	// 获取data
	public void getActivitiesData() {
		if (!hasActivitiesData) {
			progressBar.setVisibility(View.VISIBLE);
			HttpClients.postDo(Constant.BASEURL + Constant.CONTENT
					+ Constant.ORDERS, "", this);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup p = (ViewGroup) view.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return view;
	}

	@Override
	public void onResponse(String content) {
		MyLogger.i(content);
		progressBar.setVisibility(View.GONE);
		if (!TextUtils.isEmpty(content)) {
			try {
				JSONObject jsonObject = new JSONObject(content);
				String result_code = jsonObject.getString("result_code");
				if (result_code.equals("200")) {
					if (jsonObject.getString("reason").equals("success")) {
						String json = jsonObject.getString("result");
						Gson gson = new Gson();
						MyLogger.i(json);
						MyOrderItem_all myOrderItem_all = gson.fromJson(
								"{\"result\":" + json + "}",
								MyOrderItem_all.class);
						MyLogger.i(myOrderItem_all.toString());
						if (myOrderItem_all != null) {
							for (MyOrderItem myOrderItem : myOrderItem_all
									.getResult().get(0).values()) {
								mOrderItems.add(myOrderItem);
							}
						}
						myOrderListAdapter.notifyDataSetChanged();
					}
					// myToast(jsonObject.getString("result"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
