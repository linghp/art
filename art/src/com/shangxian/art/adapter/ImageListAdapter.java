package com.shangxian.art.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.shangxian.art.LocationActivity;
import com.shangxian.art.NearlyActivity;
import com.shangxian.art.R;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;

public class ImageListAdapter extends BaseAdapter {

	private static String TAG = "ImageListAdapter";

	private Context mContext;
	// xml转View对象
	private LayoutInflater mInflater;
	// 单行的布局
	private int mResource;
	// 列表展现的数据
	private List mData;
	// Map中的key
	private String[] mFrom;
	// view的id
	private int[] mTo;
	// 图片下载器
	private AbImageLoader mAbImageLoader = null;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param data
	 *            列表展现的数据
	 * @param resource
	 *            单行的布局
	 * @param from
	 *            Map中的key
	 * @param to
	 *            view的id
	 */
	public ImageListAdapter(Context context, List data, int resource,
			String[] from, int[] to) {
		this.mContext = context;
		this.mData = data;
		this.mResource = resource;
		this.mFrom = from;
		this.mTo = to;
		// 用于将xml转为View
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 图片下载器
		mAbImageLoader = AbImageLoader.newInstance(mContext);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 使用自定义的list_items作为Layout
			convertView = mInflater.inflate(mResource, parent, false);
		}

		ImageView itemsIcon = AbViewHolder.get(convertView, mTo[0]);
		TextView itemsTitle = AbViewHolder.get(convertView, mTo[1]);
		TextView itemsText = AbViewHolder.get(convertView, mTo[2]);
		TextView items_bottom = AbViewHolder.get(convertView, mTo[3]);
		items_bottom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(mContext, "location", 0).show();
				// TODO:========================= 传递参数
				Bundle bundle = new Bundle();
				bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_NEARLY_LOC);
				CommonUtil.gotoActivityWithData((Activity) mContext,
						LocationActivity.class, bundle, false);
			}
		});
		// 获取该行的数据
		@SuppressWarnings("unchecked")
		final Map<String, Object> obj = (Map<String, Object>) mData
				.get(position);
		String imageUrl = (String) obj.get("itemsIcon");
		// itemsTitle.setText((String)obj.get("itemsTitle"));
		// itemsText.setText((String)obj.get("itemsText"));
		// 设置加载中的View
		mAbImageLoader.setLoadingView(convertView
				.findViewById(R.id.progressBar));
		// 图片的下载
		mAbImageLoader.display(itemsIcon, imageUrl);

		return convertView;
	}

}
