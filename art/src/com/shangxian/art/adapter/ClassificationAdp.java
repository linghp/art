package com.shangxian.art.adapter;
/**
 * 分类adapter
 * 
 */
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;

public class ClassificationAdp extends EntityAdapter<ClassificationModel>{
	private AbImageLoader mAbImageLoader = null;
	public ClassificationAdp(Activity mAc, int layoutId,
			List<ClassificationModel> dates) {
		super(mAc, layoutId, dates);
        mAbImageLoader = AbImageLoader.newInstance(mAc);
        mAbImageLoader.setMaxWidth(100);
        mAbImageLoader.setMaxHeight(100);
        mAbImageLoader.setLoadingImage(R.drawable.image_loading);
        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}
	
	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_classifiymain, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_classifiymain_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_classifiymain_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_classifiymain_img);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getName());
		holder.summary.setText(dates.get(position).getSubTitle());
//		Imageloader_homePager.displayImage(Constant.BASEURL
//				+ dates.get(position).getPhoto(),
//				holder.img,
//				new Handler(), null);
        //图片的下载
        mAbImageLoader.display(holder.img,Constant.BASEURL
				+ dates.get(position).getPhoto());
		
		return convertView;
	}
	public static class ViewHolder{
		TextView title,summary;
		ImageView img;
	}
}
