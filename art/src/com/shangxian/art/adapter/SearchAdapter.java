package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.SearchModel;
import com.shangxian.art.utils.TimeUtil;

public class SearchAdapter extends EntityAdapter<SearchModel>{

	public SearchAdapter(Activity mAc, int layoutId, List<SearchModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_search, null);
			holder.title = (TextView) convertView.findViewById(R.id.search_title);
			holder.time = (TextView) convertView.findViewById(R.id.search_time);
			holder.img = (ImageView) convertView.findViewById(R.id.search_img);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTitle());
		
		
		holder.time.setText(TimeUtil.getCurrentData());
		
		
		return convertView;
	}
	public static class ViewHolder{
		TextView title,time;
		ImageView img;
	}

}
