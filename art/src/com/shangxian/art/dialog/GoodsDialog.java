package com.shangxian.art.dialog;

import com.shangxian.art.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GoodsDialog extends Dialog {
	private TextView tv_price;
	private TextView tv_option;
	private ImageView iv_icon;
	private LinearLayout ll_info;
	private ListView lv_option;
	private TextView tv_sub;
	private Animation anim_in;
	private Animation anim_out;

	public GoodsDialog(Context context) {
		super(context, android.R.style.Theme_Translucent);
		init();
	}
	
	public GoodsDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);	
		init();
	}

	private void init() {
		anim_in = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_in);
		anim_out = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shop_cart_updatagoods);
		setCanceledOnTouchOutside(false);
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
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				GoodsDialog.super.dismiss();
			}
		});
	}

	private void initViews() {
		tv_price = (TextView) findViewById(R.id.goot_tv_price);
		tv_option = (TextView) findViewById(R.id.goot_tv_option);
		tv_sub = (TextView) findViewById(R.id.goot_tv_option);
		
		iv_icon = (ImageView) findViewById(R.id.gooi_iv_icon);
		
		ll_info = (LinearLayout) findViewById(R.id.gool_ll_info);
		
		lv_option = (ListView) findViewById(R.id.gool_lv_option);
	}
	
	private void initListener() {
		
	}
	
	public void subClick(View v){
		
	}
	
	public void chaClick(View v){
		dismiss();
	}
}
