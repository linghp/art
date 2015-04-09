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
import com.shangxian.art.utils.TimeUtil;
import com.shangxian.art.view.LinearSlidingLayout;
import com.shangxian.art.view.SlidingListView;

public class SearchAdapter extends EntityAdapter<SearchModel>{

	public SearchAdapter(Activity mAc, int layoutId, List<SearchModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
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
		holder.title.setText(dates.get(position).getTitle());
		holder.time.setText(TimeUtil.getCurrentData());
		
		ImageView delete = (ImageView) convertView.findViewById(R.id.item_delete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				LinearSlidingLayout slidingLayout = (LinearSlidingLayout) mAc.get;
				LinearSlidingLayout selectView = (LinearSlidingLayout) parent.getChildAt(position);
				if(selectView != null){
					selectView.scrollWithoutDelay();
				}
				dates.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	public static class ViewHolder{
//		private SlidingListView list;
		TextView title,time;
		ImageView img;
	}

}
