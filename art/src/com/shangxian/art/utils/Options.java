package com.shangxian.art.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.shangxian.art.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class Options {
	/** 新闻列表中用到的图片加载配置 */
	public static DisplayImageOptions getListOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				.showImageOnLoading(R.drawable.image_loading)
				// // 设置图片Uri为空或是错误的时候显示的图片
				.showImageForEmptyUri(R.drawable.image_empty)
				// // 设置图片加载/解码过程中错误时候显示的图片
				.showImageOnFail(R.drawable.image_error).cacheInMemory(true)
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
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		return options;
	}
}
