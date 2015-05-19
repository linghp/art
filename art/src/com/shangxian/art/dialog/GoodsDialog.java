package com.shangxian.art.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.google.gson.Gson;
import com.shangxian.art.R;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class GoodsDialog extends Dialog implements
		android.view.View.OnClickListener {
	private TextView tv_price;
	private TextView tv_option;
	private ImageView iv_icon;
	private LinearLayout ll_info;
	private LinearLayout ll_options_add;
	private TextView tv_sub;
	private Animation anim_in;
	private Animation anim_out;
	private ImageView iv_cha;
	private Context context;

	private CarItem item;
	private TextView tv_title;
	private AbImageLoader mAbImageLoader;

	//商品详情模块
	private CommodityContentModel commodityContentModel;
	private GoodsDialogConfirmListener confirmListener;
	private String json;

	public interface GoodsDialogConfirmListener {
		void goodsDialogConfirm(String str);
	}

	public GoodsDialog(Context context,GoodsDialogConfirmListener confirmListener) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.confirmListener=confirmListener;
		init();
	}

	public GoodsDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		init();
	}

	private void init() {
		anim_in = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_bottom_in);
		anim_out = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_bottom_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shop_cart_updatagoods);
		setCanceledOnTouchOutside(false);
		mAbImageLoader = AbImageLoader.newInstance(getContext());
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		initViews();
		initListener();
	}

	@Override
	public void show() {
		super.show();
		ll_info.startAnimation(anim_in);
	}

	@Override
	public void dismiss() {
		ll_info.startAnimation(anim_out);
		anim_out.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				GoodsDialog.super.dismiss();
			}
		});
	}

	private void initViews() {
		tv_title = (TextView) findViewById(R.id.goot_tv_title);
		tv_price = (TextView) findViewById(R.id.goot_tv_price);
		tv_option = (TextView) findViewById(R.id.goot_tv_option);
		tv_sub = (TextView) findViewById(R.id.goot_tv_sub);

		iv_icon = (ImageView) findViewById(R.id.gooi_iv_icon);
		iv_cha = (ImageView) findViewById(R.id.gooi_iv_cha);

		ll_info = (LinearLayout) findViewById(R.id.gool_ll_info);

		ll_options_add = (LinearLayout) findViewById(R.id.ll_options_add);

		upDataView();
	}

	private void upDataView() {
		if (item != null) {
			tv_price.setText("¥"+ CommonUtil.priceConversion(
							item.listCarGoodsBean.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(item.listCarGoodsBean.getName());
			String url = Constant.BASEURL + item.listCarGoodsBean.getPhoto();
			mAbImageLoader.display(iv_icon, url);
		} else if (commodityContentModel != null) {
			Map<String, List<String>> specMap = commodityContentModel
					.getSpecs();

			tv_price.setText("¥  "
					+ CommonUtil.priceConversion(
							commodityContentModel.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(commodityContentModel.getName());
			String url = Constant.BASEURL
					+ commodityContentModel.getPhotos().get(0);
			mAbImageLoader.display(iv_icon, url);
			 List<String> specStrs=new ArrayList<String>();
			for (Entry<String, List<String>> listCarGoodsBean2 : specMap
					.entrySet()) {
				 specStrs.add(listCarGoodsBean2.getKey());
				// selectedSpec=listCarGoodsBean2.getValue();
				View view = LayoutInflater.from(context).inflate(
						R.layout.commoditycontent_sepcs_item, null);
				((TextView) view.findViewById(R.id.textView1))
						.setText(listCarGoodsBean2.getKey());
				//添加属性值
				ViewGroup vg_ad=(ViewGroup) view.findViewById(R.id.vg_add);
				List<String> listvalues=listCarGoodsBean2.getValue();
				for (String str : listvalues) {
					TextView textView =new TextView(context);
					textView.setText(str);
					//textView.setBackgroundResource(R.drawable.reslib_item_bg);
					//textView.setTag("");
					textView.setTextSize(14);
					vg_ad.addView(textView);
				}
				
				ll_options_add.addView(view);
			}
			String specStr="";
			for (String str : specStrs) {
				specStr=specStr+str+"  ";
			}
			tv_option.setText("请选择  " + specStr);
		}
	}

	private void initListener() {
		iv_cha.setOnClickListener(this);
		tv_sub.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == iv_cha) {
			dismiss();
		} else if (tv_sub == v) {
			Gson gson=new Gson();
			String jsonsepcs=gson.toJson(commodityContentModel.getSpecs());
			//服务器有问题 暂时改成这种格式
			jsonsepcs=jsonsepcs.replace("[", "");
			jsonsepcs=jsonsepcs.replace("]", "");
			json = "{\"productId\":" + commodityContentModel.getId()
					+ ",\"sepcs\":"+jsonsepcs+",\"buyCount\":"+1+"}";
			MyLogger.i(json);
           confirmListener.goodsDialogConfirm(json);
           dismiss();
		}
	}

	public void setCarGoodsItem(CarItem item) {
		this.item = item;
	}

	public void setCommodityContent(CommodityContentModel commodityContentModel) {
		this.commodityContentModel = commodityContentModel;
	}
}
