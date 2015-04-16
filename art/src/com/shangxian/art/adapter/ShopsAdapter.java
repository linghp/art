package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ProductDto;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.constant.Constant;

public class ShopsAdapter extends EntityAdapter<ProductDto>{

	//图片下载器
    private AbImageLoader mAbImageLoader = null;
	public ShopsAdapter(Activity mAc, int layoutId, List<ProductDto> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
		//图片下载器
        mAbImageLoader = AbImageLoader.newInstance(mAc);
//        mAbImageLoader.setMaxWidth(150);
//        mAbImageLoader.setMaxHeight(150);
        mAbImageLoader.setLoadingImage(R.drawable.image_loading);
        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		SubViewHolder holder = null;
		if (convertView == null) {
			holder = new SubViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(R.layout.item_shops, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_shops_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_shops_img);
			holder.price = (TextView) convertView.findViewById(R.id.item_shops_price);
			convertView.setTag(holder);
		}else {
			holder = (SubViewHolder) convertView.getTag();
			holder.title.setText(dates.get(position).getName());
			holder.price.setText("￥"+dates.get(position).getPrice());
			mAbImageLoader.display(holder.img, Constant.BASEURL+dates.get(position).getPhoto());
		}
		
		return convertView;
	}
	public static class SubViewHolder{
		private TextView title,price;
		private ImageView img;
	}

}
