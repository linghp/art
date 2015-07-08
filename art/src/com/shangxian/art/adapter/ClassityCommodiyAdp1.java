package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
/**
 * 分类adapter
 * 
 */
public class ClassityCommodiyAdp1 extends EntityAdapter<ClassityCommdityModel>{
	private AbImageLoader mAbImageLoader = null;
	public ClassityCommodiyAdp1(Activity mAc, int layoutId,
			List<ClassityCommdityModel> dates) {
		super(mAc, layoutId, dates);
		mAbImageLoader = AbImageLoader.newInstance(mAc);
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
					R.layout.item_classitycommodity, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_commodity_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_commodity_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_commodity_img);
			holder.price = (TextView) convertView.findViewById(R.id.item_commodity_price);
			holder.priceold = (TextView) convertView.findViewById(R.id.item_commodity_priceold);
			holder.priceold.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			//			holder.shop = (ImageView) convertView.findViewById(R.id.item_commodity_shop);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getName());
		holder.summary.setText(dates.get(position).getReDetails());
//		holder.price.setText(dates.get(position).getPromotionPrice()+"");
		holder.price.setText("¥"+CommonUtil.priceConversion((float)dates.get(position).getPromotionPrice()));

		if (dates.get(position).getOriginalPrice() > dates.get(position).getPromotionPrice()) {
			holder.priceold.setText("¥"+CommonUtil.priceConversion((float)dates.get(position).getOriginalPrice()));
		}
		
        mAbImageLoader.display(holder.img,Constant.BASEURL
				+ dates.get(position).getPhoto());
		return convertView;
	}
	public static class ViewHolder{
		TextView title,summary,price,priceold;
		ImageView img;
		//		ImageView shop;
	}
}
