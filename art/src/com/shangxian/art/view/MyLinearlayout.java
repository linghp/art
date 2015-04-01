package com.shangxian.art.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class MyLinearlayout extends LinearLayout {

	public MyLinearlayout(Context context) {
		super(context);
		init(null, 0);
	}

	public MyLinearlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
	

	private void init(AttributeSet attrs, int defStyle) {

	}

}
