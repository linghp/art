package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.ExpressInfo;

public class ExpressPopuAdapter extends EntityAdapter<ExpressInfo> {

	private int index = -1;
	
	public ExpressPopuAdapter(Activity mAc, int layoutId,
			List<ExpressInfo> dates) {
		super(mAc, layoutId, dates);
	}

	public void changeIndex(int index){
		this.index = index;
		notifyDataSetChanged();
	}
	
	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		TextView title = null;
		if (convertView == null) {
			convertView = inflater();
			title = (TextView) convertView.findViewById(R.id.logistt_tv_title);
			convertView.setTag(title);
		} else {
			title = (TextView) convertView.getTag();
		}
		if (position == index) {
			title.setSelected(true);
		} else {
			title.setSelected(false);
		}
		title.setText(getItem(position).getName());
		return convertView;
	}
}
