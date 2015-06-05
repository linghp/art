package com.shangxian.art.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.shangxian.art.R;
import com.shangxian.art.animation.BaseEffects;
import com.shangxian.art.utils.CommonUtil;

/**
 * 删除dialog
 * 
 * @author Administrator
 *
 */
public class FilterDialog extends Dialog implements
		android.view.View.OnClickListener {
	private EditText et_lowprice,et_upprice;
	private View ll_filter,tv_confirm;
    private Context context;
	private Filter_I filter_I;

	public interface Filter_I {
		void getData(String lowprice,String upprice);
	}

	public FilterDialog(Context context, Filter_I filter_I) {
		super(context, android.R.style.Theme_Translucent);
		this.filter_I = filter_I;
		this.context=context;
		init();
	}

	public FilterDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		init();
	}

	private void init() {
//		anim_in = AnimationUtils.loadAnimation(getContext(),
//				R.anim.anim_bottom_in);
//		anim_out = AnimationUtils.loadAnimation(getContext(),
//				R.anim.anim_bottom_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.shop_filter_dialog);
		setCanceledOnTouchOutside(true);
		initViews();
		initListener();
	}

	@Override
	public void show() {
		super.show();
		//ll_info.startAnimation(anim_in);
		SlideDown slideDown=new SlideDown();
		slideDown.setDuration(300);
		slideDown.start(ll_filter);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		//ll_info.startAnimation(anim_out);
//		anim_out.setAnimationListener(new AnimationListener() {
//			@Override
//			public void onAnimationStart(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				DeleteDialog.super.dismiss();
//			}
//		});
	}

	private void initViews() {
		et_lowprice = (EditText) findViewById(R.id.fite_et_left);
		et_upprice = (EditText) findViewById(R.id.fite_et_right);
		et_lowprice.setFocusable(true);
		et_lowprice.setFocusableInTouchMode(true);
		et_lowprice.requestFocus();
		ll_filter = findViewById(R.id.ll_filter);
		tv_confirm = findViewById(R.id.tv_confirm);
	}


	private void initListener() {
		tv_confirm.setOnClickListener(this);
		ll_filter.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv_confirm) {
			String lowprice=et_lowprice.getText().toString().trim();
			String upprice=et_upprice.getText().toString().trim();
			if(match(lowprice,upprice)){
				filter_I.getData(lowprice, upprice);
			}
			dismiss();
		}else if(v==ll_filter){
			dismiss();
		}
	}

	private boolean match(String lowprice, String upprice) {
		// TODO Auto-generated method stub
		return true;
	}

	public class SlideDown extends BaseEffects{

	    @Override
	    protected void setupAnimation(View view) {
	        getAnimatorSet().playTogether(
	                ObjectAnimator.ofFloat(view, "translationY", 300, CommonUtil.dip2px(context, 0)).setDuration(mDuration),
	                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)

	        );
	    }
	}
}
