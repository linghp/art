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
import com.shangxian.art.utils.CommonUtil;

public class ClassificationAdp extends EntityAdapter<ClassificationModel> {
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
	public View initView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final ClassificationModel classificationModel=getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_classifiymain, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_classifiymain_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_classifiymain_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_classifiymain_img);
			holder.item_classifiymain_spread = (ImageView) convertView.findViewById(R.id.item_classifiymain_spread);
			
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
        if(classificationModel.getLevel()==1){
        	holder.title.setVisibility(View.VISIBLE);
        	holder.item_classifiymain_spread.setVisibility(View.VISIBLE);
        }else{
        	holder.title.setVisibility(View.GONE);
        	holder.item_classifiymain_spread.setVisibility(View.GONE);
        	holder.summary.setText(dates.get(position).getName());
        }
        if(classificationModel.isexpand){
        	holder.item_classifiymain_spread.setImageResource(R.drawable.pull_down1);
        }else{
        	holder.item_classifiymain_spread.setImageResource(R.drawable.next_level);
        }
        // 设置内边距
     	convertView.setPadding((classificationModel.getLevel()-1) * 30+10, (2-classificationModel.getLevel())*10+6, (2-classificationModel.getLevel())*10+6, (2-classificationModel.getLevel())*10+6);
        holder.item_classifiymain_spread.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//CommonUtil.toast("click", mAc);
				if(classificationModel.getLevel()==1){
					classificationModel.isexpand=!classificationModel.isexpand;
				if(classificationModel.isexpand){
					getDates().addAll(position+1, getDates().get(position).getProductCategoryDtos());
				}else{
					getDates().removeAll(getDates().get(position).getProductCategoryDtos());
				}
				notifyDataSetChanged();
				}
			}
		});
        
		return convertView;
	}

	public static class ViewHolder {
		TextView title, summary;
		ImageView img, item_classifiymain_spread;
	}
}
