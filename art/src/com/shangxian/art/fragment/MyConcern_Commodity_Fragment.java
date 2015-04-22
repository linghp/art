package com.shangxian.art.fragment;

import java.util.ArrayList;
import java.util.List;

import com.shangxian.art.R;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.bean.ClassityCommdityModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的关注——商品
 * @author Administrator
 *
 */
public class MyConcern_Commodity_Fragment extends Fragment{
	private View view;
	private TextView tv_empty;
	private ListView listView;
	private List<ClassityCommdityModel> model = new ArrayList<ClassityCommdityModel>();
	private ClassityCommodiyAdp adapter;
	
	public MyConcern_Commodity_Fragment(List<ClassityCommdityModel> model) {
		super();
		this.model = model;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initMainView();
		return view;
	}
	private void initMainView() {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_main_action_frame,
				(ViewGroup) getActivity().findViewById(R.id.vp_content), false);
		tv_empty = (TextView) view.findViewById(R.id.tv_empty);
		listView = (ListView) view.findViewById(R.id.lv_action);
		
		for (int i = 0; i < 20; i++) {
			ClassityCommdityModel m = new ClassityCommdityModel();
			m.setName("商品列表"+i);
			model.add(m);
		}
		adapter = new ClassityCommodiyAdp(getActivity(), R.layout.item_classitycommodity, model);
		listView.setAdapter(adapter);
	}
	
	public void update() {
		if(tv_empty!=null)
		if (model.size() == 0 ) {
			tv_empty.setVisibility(View.VISIBLE);
		} else {
			tv_empty.setVisibility(View.GONE);
		}
	}
}
