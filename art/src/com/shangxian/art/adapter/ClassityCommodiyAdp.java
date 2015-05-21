package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;
/**
 * 分类adapter
 * 
 */
public class ClassityCommodiyAdp extends EntityAdapter<ListCarGoodsBean>{

	public ClassityCommodiyAdp(Activity mAc, int layoutId,
			List<ListCarGoodsBean> dates) {
		super(mAc, layoutId, dates);
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
			//holder.shop = (ImageView) convertView.findViewById(R.id.item_commodity_shop);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getProductName());
		//holder.summary.setText(dates.get(position).getReDetails());
		holder.price.setText(CommonUtil.priceConversion((float)dates.get(position).getPrice())+"");
		ImageLoader.getInstance().displayImage(Constant.BASEURL + getItem(position).getProductPhoto(), holder.img, Options.getListOptions());
		return convertView;
	}
	public static class ViewHolder{
		TextView title,summary,price;
		ImageView img;
	}
}
