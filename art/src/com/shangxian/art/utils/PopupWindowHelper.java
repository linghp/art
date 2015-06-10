package com.shangxian.art.utils;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowHelper {
	private PopupWindow mWindow;
	
	public PopupWindowHelper() {
		mWindow = new PopupWindow();
	}
	
	public PopupWindowHelper(Context context){
		mWindow = new PopupWindow(context);
	}
	
	public PopupWindowHelper(View contentView){
		mWindow = new PopupWindow(contentView);
	}
	
	public PopupWindowHelper(Context context, AttributeSet attrs){
		mWindow = new PopupWindow(context, attrs);
	}
	
	public PopupWindowHelper(int width, int height){
		mWindow = new PopupWindow(width, height);		
	}
	
	public PopupWindowHelper(View contentView, int width, int height){
		mWindow = new PopupWindow(contentView, width, height);
	}
	
	public PopupWindowHelper(View contentView, int width, int height, boolean focusable){
		mWindow = new PopupWindow(contentView, width, height, focusable);
	}
	
	public void showAsDropDown(View anchor, int xoff, int yoff){
		mWindow.setTouchable(true);
		mWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		mWindow.setBackgroundDrawable(new BitmapDrawable());
		mWindow.getContentView().setFocusableInTouchMode(true);
		mWindow.getContentView().setFocusable(true);
		mWindow.showAsDropDown(anchor, xoff, yoff);
	}
	
	
	public void showAsDropDown(View anchor, int xoff, int yoff, int gravity){
		mWindow.setTouchable(true);
		mWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		mWindow.setBackgroundDrawable(new BitmapDrawable());
		mWindow.getContentView().setFocusableInTouchMode(true);
		mWindow.getContentView().setFocusable(true);
		mWindow.showAsDropDown(anchor, xoff, yoff, gravity);
	}
	
	public void dismiss() {
		if (mWindow.isShowing()) {
			mWindow.dismiss();
		}
	}
	
	public PopupWindow getWindow(){
		return mWindow;
	}
}
