package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.BenQiJieSuanModel;
import com.shangxian.art.bean.JieSuanLiShiModel;
import com.shangxian.art.utils.CommonUtil;

public class JieSuanLiShiAdapter extends EntityAdapter<BenQiJieSuanModel>{

	public JieSuanLiShiAdapter(Activity mAc, int layoutId,
			List<BenQiJieSuanModel> dates) {
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
		holder.type.setText(dates.get(position).getSettlementType());
		if (dates.get(position).getSettlementType().equals("NORMAL")) {
			holder.type.setText("定时结算");
		}else {
			holder.type.setText("紧急结算");
		}
		holder.time.setText(dates.get(position).getSettlementTime());
		holder.parice.setText(CommonUtil.priceConversion(dates.get(position).getAmount()));
		return convertView;
	}

	public static class ViewHolder{
		TextView time,type,parice;
	}
}
