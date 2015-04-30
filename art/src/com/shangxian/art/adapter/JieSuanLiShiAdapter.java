package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.JieSuanLiShiModel;

public class JieSuanLiShiAdapter extends EntityAdapter<JieSuanLiShiModel>{

	public JieSuanLiShiAdapter(Activity mAc, int layoutId,
			List<JieSuanLiShiModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_jinjijiesuan, null);
			holder.type = (TextView) convertView.findViewById(R.id.jinjijiesuan_type);
			holder.time = (TextView) convertView.findViewById(R.id.jinjijiesuan_time);
			holder.parice = (TextView) convertView.findViewById(R.id.jinjijiesuan_money);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.type.setText(dates.get(position).getType());
		holder.time.setText(dates.get(position).getTime());
		holder.parice.setText(dates.get(position).getSprice());
		return convertView;
	}

	public static class ViewHolder{
		TextView time,type,parice;
	}
}
