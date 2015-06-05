package com.shangxian.art.view;

import com.shangxian.art.utils.MyLogger;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * TODO: document your custom view class.
 */
public class MyRelativeLayout extends RelativeLayout {

	public MyRelativeLayout(Context context) {
		super(context);
		init(null, 0);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
	
	

	private void init(AttributeSet attrs, int defStyle) {

	}

}
