package com.shangxian.art.adapter;

import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.adapter.JieSuanLiShiAdapter.ViewHolder;
import com.shangxian.art.bean.IandEDetailsModel;
import com.shangxian.art.utils.CommonUtil;

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
		holder.title.setText(dates.get(position).getTradeTitle());//购买商品
		holder.time.setText(dates.get(position).getTransDate());//时间
		holder.parice.setText("¥"+CommonUtil.priceConversion(dates.get(position).getTotalPrice()));//金额
		holder.type.setText(dates.get(position).getDirection());//支出还是收入
		if ("收入".equals(dates.get(position).getDirection().toString().trim())) {
			holder.img.setImageResource(R.drawable.balance_green);
		}else if ("支出".equals(dates.get(position).getDirection().toString().trim())){
			holder.img.setImageResource(R.drawable.balance);
		}
		//月份
		if(dates.get(position).isIstitle()){
			holder.month.setVisibility(View.VISIBLE);
		    holder.month.setText(dates.get(position).getMonth()+"月");
		}else{
			holder.month.setVisibility(View.GONE);
		}
		return convertView;
	}
	public static class ViewHolder{
		TextView title,time,parice,type,month;
		ImageView img;
	}
}
