package com.shangxian.art.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Description 刷新dialog
 * @author ling
 * @date 2015年6月8日
 */

public class CustomOnlyDisplayDialog extends Dialog implements
		android.view.View.OnClickListener {
	private int layoutid;
	public CustomOnlyDisplayDialog(Context context, int theme,int layoutid) {
		super(context, android.R.style.Theme_Translucent);
		this.layoutid=layoutid;
		init();
	}

	private void init() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(layoutid);
		initViews();
		initListener();
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	private void initViews() {
	}


	private void initListener() {
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return true;
	}
}
