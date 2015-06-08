package com.shangxian.art.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.shangxian.art.R;

/**
 * @Description 刷新dialog
 * @author ling
 * @date 2015年6月8日
 */

public class RefreshDialog extends Dialog implements
		android.view.View.OnClickListener {

	public RefreshDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		init();
	}

	private void init() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_refresh);
		setCanceledOnTouchOutside(true);
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

}
