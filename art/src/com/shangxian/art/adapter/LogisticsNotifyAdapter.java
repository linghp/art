package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.LogisticsNotifyModel;

public class LogisticsNotifyAdapter extends EntityAdapter<LogisticsNotifyModel>{

	public LogisticsNotifyAdapter(Activity mAc, int layoutId,
			List<LogisticsNotifyModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_logisticsnotify, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_logisticsnotify_title);
			holder.img = (ImageView) convertView.findViewById(R.id.item_logisticsnotify_img);
			holder.content = (TextView) convertView.findViewById(R.id.item_logisticsnotify_content);
			holder.look = (TextView) convertView.findViewById(R.id.item_logisticsnotify_look);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTitle());
		holder.content.setText(dates.get(position).getContent());
		/*Imageloader_homePager.displayImage(Constant.BASEURL
				+ dates.get(position).getPhoto(),
				holder.img,
				new Handler(), null);*/
		holder.look.setOnClickListener(new OnClickListener() {
			//点击查看详情
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}
	public static class ViewHolder{
		TextView title,content;
		ImageView img;
		TextView look;
	}

}
