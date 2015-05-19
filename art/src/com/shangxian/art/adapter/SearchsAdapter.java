package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.CircleImageView1;

public class SearchsAdapter extends EntityAdapter<ListCarGoodsBean> {

	public SearchsAdapter(Activity mAc, int layoutId,
			List<ListCarGoodsBean> dates) {
		super(mAc, layoutId, dates);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater();
			holder = new ViewHolder();
			holder.icon = (CircleImageView1) convertView.findViewById(R.id.searchs_img);
			holder.title = (TextView) convertView.findViewById(R.id.searchs_title);
			holder.time = (TextView) convertView.findViewById(R.id.searchs_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ListCarGoodsBean bean = getItem(position);
		holder.title.setText(bean.getName());
		holder.time.setVisibility(View.INVISIBLE);
		ImageLoader.getInstance().displayImage(Constant.BASEURL + bean.getPhoto(), holder.icon, Options.getListOptions());
		return convertView;
	}
	
	private class ViewHolder{
		TextView title, time;
		CircleImageView1 icon;
	}
}
