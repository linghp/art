package com.shangxian.art.adapter;

import java.util.List;
import java.util.Map;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.shangxian.art.R;
import com.shangxian.art.bean.ShopsListModel;
import com.shangxian.art.constant.Constant;

public class ShopsListAdapter extends BaseAdapter {

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
	 */
	public ShopsListAdapter(Context context, List data, int resource) {
		this.mContext = context;
		this.mData = data;
		this.mResource = resource;
		// this.mFrom = from;
		// this.mTo = to;
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
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ShopsListModel shopsListModel = (ShopsListModel) getItem(position);
		if (convertView == null) {
			// 使用自定义的list_items作为Layout
			convertView = mInflater.inflate(mResource, parent, false);
		}

		ImageView itemsIcon = (ImageView) convertView
				.findViewById(R.id.itemsIcon);
		TextView itemsTitle = (TextView) convertView
				.findViewById(R.id.itemsTitle);
		TextView itemsText = (TextView) convertView
				.findViewById(R.id.itemsText);
		TextView items_bottom = (TextView) convertView
				.findViewById(R.id.items_bottom);
		TextView items_bottom1 = (TextView) convertView
				.findViewById(R.id.items_bottom1);
		// 获取该行的数据
		// final Map<String, Object> obj = (Map<String,
		// Object>)mData.get(position);
		// String imageUrl = (String)obj.get("itemsIcon");
		itemsTitle.setText(shopsListModel.getShopName());
		itemsText.setText(shopsListModel.getSubTitle());
		items_bottom.setText("¥" + shopsListModel.getPrice() + "/起");
		items_bottom.setVisibility(View.GONE);
		items_bottom1.setVisibility(View.VISIBLE);
		// 设置加载中的View
		mAbImageLoader.display(itemsIcon,
				Constant.BASEURL + shopsListModel.getLogo());
		// 图片的下载
		// mAbImageLoader.display(itemsIcon,imageUrl);

		return convertView;
	}

}
