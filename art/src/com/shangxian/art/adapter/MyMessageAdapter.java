package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.MyMessageModel;

public class MyMessageAdapter extends EntityAdapter<MyMessageModel>{

	public MyMessageAdapter(Activity mAc, int layoutId,
			List<MyMessageModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_mymessage, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_mymessage_title);
			holder.time = (TextView) convertView.findViewById(R.id.item_mymessage_time);
			holder.img = (ImageView) convertView.findViewById(R.id.item_mymessage_img);
			holder.content = (TextView) convertView.findViewById(R.id.item_mymessage_content);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTitle());
		holder.time.setText(dates.get(position).getTime());
		holder.content.setText(dates.get(position).getContent());
		/*Imageloader_homePager.displayImage(Constant.BASEURL
				+ dates.get(position).getPhoto(),
				holder.img,
				new Handler(), null);*/
		return convertView;
	}

	public static class ViewHolder{
		TextView title,time,content;
		ImageView img;
//		ImageView shop;
	}
}
