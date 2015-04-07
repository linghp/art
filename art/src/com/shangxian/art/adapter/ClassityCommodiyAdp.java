package com.shangxian.art.adapter;
/**
 * 分类adapter
 * 
 */
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.constant.Global;

public class ClassityCommodiyAdp extends EntityAdapter<ClassityCommdityModel>{

	public ClassityCommodiyAdp(Activity mAc, int layoutId,
			List<ClassityCommdityModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_classitycommodity, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_commodity_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_commodity_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_commodity_img);
			holder.price = (TextView) convertView.findViewById(R.id.item_commodity_price);
			holder.shop = (ImageView) convertView.findViewById(R.id.item_commodity_shop);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getTitle());
		holder.summary.setText(dates.get(position).getSummary());
		holder.price.setText(dates.get(position).getPrice());
		
		return convertView;
	}
	public static class ViewHolder{
		TextView title,summary,price;
		ImageView img,shop;
	}
}