package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.SubClassificationModel;

public class SubClassificationAdp extends EntityAdapter<SubClassificationModel>{

	public SubClassificationAdp(Activity mAc, int layoutId,
			List<SubClassificationModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		SubViewHolder holder = null;
		if (convertView == null) {
			holder = new SubViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(R.layout.subitem_classification, null);
			holder.subtitle = (TextView) convertView.findViewById(R.id.subitem_classification_txt);
			holder.subimg = (ImageView) convertView.findViewById(R.id.subitem_classification_img);
			convertView.setTag(holder);
		}else {
			holder = (SubViewHolder) convertView.getTag();
			holder.subtitle.setText(dates.get(position).getSubtitle());
		}
		return convertView;
	}
	public static class SubViewHolder{
		private TextView subtitle;
		private ImageView subimg;
	}

}
