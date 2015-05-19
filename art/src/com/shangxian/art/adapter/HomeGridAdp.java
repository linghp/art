package com.shangxian.art.adapter;

import java.math.BigDecimal;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ab.util.AbLogUtil;
import com.ab.util.AbSharedUtil;
import com.ab.util.AbToastUtil;
import com.shangxian.art.R;
import com.shangxian.art.bean.GoodBean;
import com.shangxian.art.constant.Global;
import com.shangxian.art.utils.MyLogger;

public class HomeGridAdp extends AdapterBase<GoodBean> {

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = Global.mInflater.inflate(R.layout.item_main_home_grid,
					null);
			holder = new ViewHolder();

			holder.ll_first = (LinearLayout) convertView
					.findViewById(R.id.ll_first);
			holder.img_logo_left = (ImageView) convertView
					.findViewById(R.id.img_logo_left);
			holder.tv_title_left = (TextView) convertView
					.findViewById(R.id.tv_title_left);
			holder.tv_price_left = (TextView) convertView
					.findViewById(R.id.tv_price_left);

			holder.ll_second = (LinearLayout) convertView
					.findViewById(R.id.ll_second);
			holder.img_logo_right = (ImageView) convertView
					.findViewById(R.id.img_logo_right);
			holder.tv_title_right = (TextView) convertView
					.findViewById(R.id.tv_title_right);
			holder.tv_price_right = (TextView) convertView
					.findViewById(R.id.tv_price_right);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.img_logo_left
				.getLayoutParams();
		params.width = (AbSharedUtil.getInt(Global.mContext,
				Global.KEY_SCREEN_WIDTH) - 9 * 4 - 5 * 4) / 2;
		params.height = params.width;
		holder.img_logo_left.setLayoutParams(params);
		AbLogUtil.e(Global.mContext, params.width + "");
		
		mApplication.getLoader().setMaxHeight(params.width);
		mApplication.getLoader().setMaxWidth(params.width);
		mApplication.getLoader().display(holder.img_logo_left, getListData().get(position * 2).getImg());

		if (!TextUtils.isEmpty(getListData().get(position * 2).getPrice()))
			holder.tv_price_left.setText(getListData().get(position * 2).getPrice());
		//MyLogger.i(getListData().toString());
		if (!TextUtils.isEmpty(getListData().get(position * 2).getContent()))
			holder.tv_title_left.setText(getListData().get(position * 2).getContent());

		holder.ll_first.setOnClickListener(new OnClick(getListData().get(position * 2)
				.getId()));

		if (getListData().size() > (position * 2 + 1)) {
			holder.ll_second.setVisibility(View.VISIBLE);

			LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) holder.img_logo_right
					.getLayoutParams();
			params2.width = (AbSharedUtil.getInt(Global.mContext,
					Global.KEY_SCREEN_WIDTH) - 9 * 4 - 5 * 4) / 2;
			params2.height = params2.width;
			holder.img_logo_left.setLayoutParams(params2);
			AbLogUtil.e(Global.mContext, params2.width + "");

			mApplication.getLoader().setMaxHeight(params.width);
			mApplication.getLoader().setMaxWidth(params.width);
			mApplication.getLoader().display(holder.img_logo_right, getListData().get(position * 2 + 1)
					.getImg());

			if (!TextUtils.isEmpty(getListData().get(position * 2 + 1).getPrice()))
				holder.tv_price_right.setText(getListData().get(position * 2 + 1)
						.getPrice());
			if (!TextUtils.isEmpty(getListData().get(position * 2 + 1).getContent()))
				holder.tv_title_right.setText(getListData().get(position * 2 + 1)
						.getContent());
			holder.ll_second.setOnClickListener(new OnClick(getListData().get(
					position * 2 + 1).getId()));
		} else {
			holder.ll_second.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	protected void onReachBottom() {

	}

	private class OnClick implements OnClickListener {

		int id;

		public OnClick(int id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			// Bundle bundle = new Bundle();
			// bundle.putInt("id", id);
			// CommonUtil.gotoActivityWithData(Global.mContext, ??.class,
			// bundle, false);
			AbToastUtil.showToast(Global.mContext, "再点我一下");
		}

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

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		LinearLayout ll_first, ll_second;
		ImageView img_logo_left, img_logo_right;
		TextView tv_title_left, tv_price_left, tv_title_right, tv_price_right;
	}

}
