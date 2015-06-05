package com.shangxian.art.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class SearchDialog extends Dialog {
	
	public SearchDialog(Context context){
		super(context, android.R.style.Theme_Translucent);
	}

	public SearchDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
