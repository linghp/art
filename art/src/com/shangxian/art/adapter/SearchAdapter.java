package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.SearchModel;
import com.shangxian.art.bean.SearchProductInfo;
import com.shangxian.art.utils.TimeUtil;
import com.shangxian.art.view.LinearSlidingLayout;
import com.shangxian.art.view.SlidingListView;

public class SearchAdapter extends EntityAdapter<SearchProductInfo>{

	public SearchAdapter(Activity mAc, int layoutId, List<SearchProductInfo> dates) {
		super(mAc, layoutId, dates);
	}

	@Override
	public View initView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_search, null);
			holder.title = (TextView) convertView.findViewById(R.id.search_title);
			holder.time = (TextView) convertView.findViewById(R.id.search_time);
			holder.img = (ImageView) convertView.findViewById(R.id.search_img);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	public static class ViewHolder{
//		private SlidingListView list;
		TextView title,time;
		ImageView img;
	}

}
