package com.shangxian.art.base;

import android.os.Bundle;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.shangxian.art.utils.MyLogger;

public class BaseActivity extends AbActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyLogger.i(getClass().getSimpleName());
	}

	protected void myToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
