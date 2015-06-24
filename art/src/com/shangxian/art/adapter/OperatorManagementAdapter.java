package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.OperatorManagementModel;

public class OperatorManagementAdapter extends EntityAdapter<OperatorManagementModel>{

	public OperatorManagementAdapter(Activity mAc, int layoutId,
			List<OperatorManagementModel> dates) {
		super(mAc, layoutId, dates);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_operatormanagement, null);
			holder.name = (TextView) convertView.findViewById(R.id.operatormanagement_tv1);
			holder.num = (TextView) convertView.findViewById(R.id.operatormanagement_tv2);
			holder.operator = (TextView) convertView.findViewById(R.id.operatormanagement_tv3);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(dates.get(position).getName());
		holder.num.setText(dates.get(position).getNum());
		holder.operator.setText(dates.get(position).getOperator());
		return convertView;
	}
	public static class ViewHolder{
		TextView name,num,operator;
	}
}
