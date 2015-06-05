package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.CommodityContentActivity;
import com.shangxian.art.R;
import com.shangxian.art.ShopsActivity;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.BaseServer;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.CircleImageView1;

public class SearchsAdapter extends EntityAdapter<ListCarGoodsBean> {
	private boolean isShop = true;
	
	public SearchsAdapter(Activity mAc, int layoutId,
			List<ListCarGoodsBean> dates) {
		super(mAc, layoutId, dates);
	}

	public void setToSearch(boolean isShop){
		this.isShop = isShop;
	}
	
	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater();
			holder = new ViewHolder();
			holder.icon = (CircleImageView1) convertView.findViewById(R.id.searchs_img);
			holder.title = (TextView) convertView.findViewById(R.id.searchs_title);
			holder.time = (TextView) convertView.findViewById(R.id.searchs_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ListCarGoodsBean bean = getItem(position);
		holder.title.setText(isShop ? bean.getShopName() : bean.getName());
		//bean.get
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShop) {
					ShopsActivity.startThisActivity(bean.getShopId(), mAc);
				} else {
					CommodityContentActivity.startThisActivity(bean.getCategoryId() + "", mAc);
				}
			}
		});
		holder.time.setVisibility(View.INVISIBLE);
		ImageLoader.getInstance().displayImage(Constant.BASEURL + (isShop ? bean.getLogo() : bean.getPhoto()), holder.icon, Options.getListOptions());
		return convertView;
	}
	
	private class ViewHolder{
		TextView title, time;
		CircleImageView1 icon;
	}
}
