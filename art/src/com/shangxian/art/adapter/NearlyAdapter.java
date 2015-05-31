package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.shangxian.art.LocationActivity;
import com.shangxian.art.R;
import com.shangxian.art.bean.NearlyShopInfo;
import com.shangxian.art.bean.ShopLocInfo;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;

public class NearlyAdapter extends EntityAdapter<NearlyShopInfo> {

	private DisplayImageOptions options;

	public NearlyAdapter(Activity mAc, int layoutId, List<NearlyShopInfo> dates) {
		super(mAc, layoutId, dates);
		options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				// .showImageOnLoading(R.drawable.image_error)
				// // 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.image_empty)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.image_loading).
				cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)
				.considerExifParams(true)
				// 设置图片下载前的延
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater();
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.itemsIcon);
			holder.title = (TextView) convertView.findViewById(R.id.itemsTitle);
			holder.context = (TextView) convertView.findViewById(R.id.itemsText);
			holder.distance = (TextView) convertView.findViewById(R.id.items_bottom);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final NearlyShopInfo info = getItem(position);
		holder.title.setText(info.getTitle());
		holder.context.setText(info.getServiceDesc());
		holder.distance.setText("距离我:" + info.getDistance() + "米");
		ImageLoader.getInstance().displayImage(Constant.BASEURL  + info.getIndexLogo(), holder.icon, options);		
		holder.distance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_SHOPS_2_LOC);
				ShopLocInfo info1 = new ShopLocInfo();
				info1.setId(info.getShopId());
				info1.setTitle(info.getTitle());
				info1.setPhoto(info.getIndexLogo());
				info1.setAddress(info.getAddress());
				info1.setLng(info.getLatLng());
				bundle.putSerializable(Constant.INT_LOC_NEARLY_SHOPINFO, info1);
				CommonUtil.gotoActivityWithData(mAc,
						LocationActivity.class, bundle, false);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView title, context, distance;
		ImageView icon;
	}
}
