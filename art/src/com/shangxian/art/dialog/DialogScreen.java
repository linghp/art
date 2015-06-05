package com.shangxian.art.dialog;

import com.shangxian.art.R;
import com.shangxian.art.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
/**
 * 筛选dialog
 * @author Administrator
 *
 */
public class DialogScreen extends AlertDialog{
	private TextView shopslist,classifylist;

	protected DialogScreen(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		// TODO Auto-generated constructor stub
		
	}

	protected DialogScreen(Context context) {
		super(context,android.R.style.Theme_Translucent);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_popupwindow);
		shopslist = (TextView) findViewById(R.id.screen_shopslist);
		classifylist = (TextView) findViewById(R.id.screen_classifylist);
	}

}
