package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import com.shangxian.art.view.MyGridView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsItemAdapter extends BaseAdapter {
	private Context mContext;
	private List<List<String>> options;

	public GoodsItemAdapter(Context context, List<List<String>> options){
		this.mContext = context;
		upDataList(options);
	}

	public void upDataList(List<List<String>> options) {
		if (options == null) {
			options = new ArrayList<List<String>>();
		}
		if (!options.equals(this.options)) {
			this.options = options;
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		return options.size();
	}

	@Override
	public List<String> getItem(int position) {
		return options.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		return null;
	}

	private class ViewHolder{
		TextView title;
		MyGridView info;
	}
}
