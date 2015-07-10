package com.shangxian.art.adapter;

import java.math.BigDecimal;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.ab.util.AbToastUtil;
import com.shangxian.art.CommodityContentActivity;
import com.shangxian.art.R;
import com.shangxian.art.ShopsActivity;
import com.shangxian.art.bean.ProductDto;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class ShopsAdapter extends AdapterBase<ProductDto>{
	Context mContext;
	//图片下载器
	private AbImageLoader mAbImageLoader = null;

	public ShopsAdapter(Context context) {
		//this.mList = mList;
		this.mContext = context;
		//图片下载器
		mAbImageLoader = AbImageLoader.newInstance(context);
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
	}
	@Override
	protected View getExView(final int position, View convertView, ViewGroup parent) {
		SubViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shops, null);
			holder = new SubViewHolder();

			holder.ll_first = (LinearLayout) convertView.findViewById(R.id.item_shops_linear1);
			holder.title = (TextView) convertView.findViewById(R.id.item_shops_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_shops_img);
			// 设置图片的比例大小
			//holder.img.setScaleType(ImageView.ScaleType.FIT_XY);
//			LayoutParams layoutParams = ((ViewGroup)holder.img.getParent())
//					.getLayoutParams();
			LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			layoutParams.width = (CommonUtil
					.getScreenWidth(mContext) - CommonUtil
					.dip2px(mContext, 40)) / 2;
			layoutParams.height = layoutParams.width;
			holder.img.setLayoutParams(layoutParams);
			
			holder.price = (TextView) convertView.findViewById(R.id.item_shops_price);

			holder.ll_second = (LinearLayout) convertView.findViewById(R.id.item_shops_linear2);
			holder.title1 = (TextView) convertView.findViewById(R.id.item_shops_summary1);
			holder.img1 = (ImageView) convertView.findViewById(R.id.item_shops_img1);
			holder.price1 = (TextView) convertView.findViewById(R.id.item_shops_price1);
			holder.img1.setLayoutParams(layoutParams);
			convertView.setTag(holder);
		}else {
			holder = (SubViewHolder) convertView.getTag();

		}
		//		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.img
		//				.getLayoutParams();
		//		params.width = (AbSharedUtil.getInt(mContext,
		//				Global.KEY_SCREEN_WIDTH) - 9 * 4 - 5 * 4) / 2;

		mApplication.getLoader().display(holder.img, Constant.BASEURL+getListData().get(position * 2).getPhoto());
		if (!TextUtils.isEmpty(getListData().get(position * 2).getPrice()+""))
			holder.price.setText("¥"+CommonUtil.priceConversion(getListData().get(position * 2).getPrice()));//金额
		if (!TextUtils.isEmpty(getListData().get(position * 2).getName()))
			holder.title.setText(getListData().get(position * 2).getName());//name
		//		holder.ll_first.setOnClickListener(new OnClick(getListData().get(position * 2)
		//				.getId()));//左边的点击
		holder.ll_first.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String productId = getListData().get(position * 2).getProductId();
				CommodityContentActivity.startThisActivity(productId, mContext);
			}
		});
		if (getListData().size() > (position * 2 + 1)) {
			holder.ll_second.setVisibility(View.VISIBLE);

			mApplication.getLoader().display(holder.img1, Constant.BASEURL+getListData().get(position * 2 + 1)
					.getPhoto());
			if (!TextUtils.isEmpty(getListData().get(position * 2 + 1).getPrice()+""))
				holder.price1.setText("¥"+CommonUtil.priceConversion(getListData().get(position * 2 + 1)
						.getPrice()));
			if (!TextUtils.isEmpty(getListData().get(position * 2 + 1).getName()))
				holder.title1.setText(getListData().get(position * 2 + 1)
						.getName());
			holder.ll_second.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String productId = getListData().get(position * 2+1).getProductId();
					CommodityContentActivity.startThisActivity(productId, mContext);
				}
			});
		} else {
			holder.ll_second.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public int getCount() {
		BigDecimal bigDecimal = new BigDecimal(getListData().size() / 2).setScale(0,
				BigDecimal.ROUND_HALF_UP);
		if (getListData().size() % 2 == 0) {
			return bigDecimal.intValue();
		} else {
			return bigDecimal.intValue() + 1;
		}
	}


	/*//图片下载器
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
	}*/
	public static class SubViewHolder{
		LinearLayout ll_first, ll_second;
		private TextView title,price;
		private ImageView img;
		private TextView title1,price1;
		private ImageView img1;
	}


}
