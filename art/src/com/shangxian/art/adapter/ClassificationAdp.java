package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.constant.Global;

public class ClassificationAdp extends EntityAdapter<ClassificationModel>{

	public ClassificationAdp(Activity mAc, int layoutId,
			List<ClassificationModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_classifiymain, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_classifiymain_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_classifiymain_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_classifiymain_img);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTitle());
		holder.summary.setText(dates.get(position).getSummary());
		
		
		return convertView;
	}
	public static class ViewHolder{
		TextView title,summary;
		ImageView img;
	}
}
