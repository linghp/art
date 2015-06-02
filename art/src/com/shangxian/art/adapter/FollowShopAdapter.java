package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.R;
import com.shangxian.art.ShopsActivity;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.Options;

/**
 * 
 * @author libo
 *
 */
public class FollowShopAdapter extends EntityAdapter<ListCarGoodsBean> {

	public FollowShopAdapter(Activity mAc, int layoutId,
			List<ListCarGoodsBean> dates) {
		super(mAc, layoutId, dates);
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater();
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ListCarGoodsBean bean = getItem(position);
		holder.title.setText(bean.getShopName());
		holder.price.setVisibility(View.INVISIBLE);
//		holder.title.setText("¥" + CommonUtil.priceConversion(bean.getPrice())
//				+ "/起");
		
		ImageLoader.getInstance().displayImage(
				Constant.BASEURL + bean.getShopLogo(), holder.icon,
				Options.getListOptions());
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShopsActivity.startThisActivity(bean.getShopId(), mAc);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		TextView title, price;
		ImageView icon;
	}
}
