package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.adapter.JieSuanLiShiAdapter.ViewHolder;
import com.shangxian.art.bean.IandEDetailsModel;

public class IandEDetailsAdapter extends EntityAdapter<IandEDetailsModel>{

	public IandEDetailsAdapter(Activity mAc, int layoutId,
			List<IandEDetailsModel> dates) {
		super(mAc, layoutId, dates);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_iandedetails, null);
			holder.month = (TextView) convertView.findViewById(R.id.item_iandedetails_month);
			holder.img = (ImageView) convertView.findViewById(R.id.item_iandedetails_img);
			holder.title = (TextView) convertView.findViewById(R.id.item_iandedetails_title);
			holder.time = (TextView) convertView.findViewById(R.id.item_iandedetails_time);
			holder.parice = (TextView) convertView.findViewById(R.id.item_iandedetails_money);
			holder.type = (TextView) convertView.findViewById(R.id.item_iandedetails_type);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTradeTitle());
		holder.time.setText(dates.get(position).getTransDate());
		holder.parice.setText(dates.get(position).getTotalPrice());
		holder.type.setText(dates.get(position).getPayType());
		return convertView;
	}
	public static class ViewHolder{
		TextView title,time,parice,type,month;
		ImageView img;
	}
}
