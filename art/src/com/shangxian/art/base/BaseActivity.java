package com.shangxian.art.base;

import android.os.Bundle;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

public class BaseActivity extends AbActivity {
	protected TopView topView;
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
