package com.shangxian.art.dialog;

import com.shangxian.art.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

public class GoodsDialog extends Dialog {
	public GoodsDialog(Context context) {
		super(context, android.R.style.Theme_Translucent);
	}
	
	public GoodsDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);	
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

	private void initViews() {
		
	}
	
	private void initListener() {
		
	}
}
