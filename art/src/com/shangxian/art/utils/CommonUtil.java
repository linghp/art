package com.shangxian.art.utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.shangxian.art.R;
import com.shangxian.art.constant.Global;

public class CommonUtil {

	/**
	 * 跳转页面，动画效果
	 * 
	 * @param curActivity
	 *            当前活动
	 * @param targetActivity
	 *            目标活动
	 * @param finish
	 *            是否结束当前活动
	 */
	public static void gotoActivity(Activity curActivity,
			Class<?> targetActivity, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(curActivity, targetActivity);
		curActivity.startActivity(intent);
		curActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if (finish) {
			curActivity.finish();
		}
	}

	/**
	 * 跳转页面--带数据传递
	 * 
	 * @param curActivity
	 *            当前活动
	 * @param targetActivity
	 *            目标活动
	 * @param bundle
	 *            需要传递的数据
	 * @param finish
	 *            是否结束当前活动
	 */
	public static void gotoActivityWithData(Activity curActivity,
			Class<?> targetActivity, Bundle bundle, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(curActivity, targetActivity);
		intent.putExtras(bundle);
		curActivity.startActivity(intent);
		curActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if (finish) {
			curActivity.finish();
		}
	}

	/**
	 * 跳转页面--带数据传递
	 * 
	 * @param curActivity
	 *            当前活动
	 * @param targetActivity
	 *            目标活动
	 * @param bundle
	 *            需要传递的数据
	 * @param finish
	 *            是否结束当前活动
	 */
	public static void gotoActivityWithDataForResult(Activity curActivity,
			Class<?> targetActivity, Bundle bundle, int RequestCode,
			boolean finish) {
		Intent intent = new Intent();
		intent.setClass(curActivity, targetActivity);
		intent.putExtras(bundle);
		curActivity.startActivityForResult(intent, RequestCode);
		curActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if (finish) {
			curActivity.finish();
		}
	}

	/**
	 * 跳转页面，动画效果
	 * 
	 * @param curActivity
	 *            当前活动
	 * @param targetActivity
	 *            目标活动
	 * @param Code 
	 *        int 类型数据
	 * @param finish
	 *            是否结束当前活动
	 */
	public static void gotoActivityForResult(Activity curActivity,
			Class<?> targetActivity, int Code, boolean finish) {
		Intent intent = new Intent();
		intent.setClass(curActivity, targetActivity);
		curActivity.startActivityForResult(intent, Code);
		curActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);

		if (finish) {
			curActivity.finish();
		}
	}

	/**
	 * 跳转页面--结束中间活动
	 * 
	 * @param curActivity
	 *            当前活动
	 * @param targetActivity
	 *            目标活动
	 * @param refresh
	 *            是否刷新要跳转界面
	 */
	public static void gotoActivityWithFinishOtherAll(Activity curActivity,
			Class<?> targetActivity, boolean refresh) {
		Intent intent = new Intent();
		intent.setClass(curActivity, targetActivity);
		if (!refresh) {
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);// 设置不要刷新将要跳到的界面
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// 它可以关掉所要到的界面中间的activity
		curActivity.startActivity(intent);
		curActivity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
	}

	/**
	 * 显示网络相关提示界面
	 * 
	 * @param tipsView
	 * @param response
	 *            网络请求响应值
	 * @param refreshlistener
	 *            数据刷新监听接口
	 */
	//	public static void showNetTipsView(final TipsView tipsView,
	//			WebResponse response, OnClickListener refreshlistener) {
	//		tipsView.setVisibility(View.VISIBLE);
	//
	//		switch (response.state) {
	//		case WebResponse.FAIL:
	//			tipsView.setTitle("亲，连接超时了哦！\n请检查您的网络并重新刷新数据！");
	//			tipsView.setBtnText("刷新数据");
	//			tipsView.setBtnListener(refreshlistener);
	//			tipsView.setBtnVisibility(View.VISIBLE);
	//			break;
	//
	//		case WebResponse.XmlPullParserException:
	//			tipsView.setTitle("数据解析失败！\n如果您能将错误信息反馈给我们的工程师，\n我们将为您提供更好的体验！");
	//			tipsView.setBtnText("我要反馈！");
	//			tipsView.setBtnListener(new OnClickListener() {
	//
	//				@Override
	//				public void onClick(View v) {
	//					CommonUtil.gotoActivity(tipsView.getActivity(),
	//							FeedBackActivity.class, false);
	//				}
	//			});
	//			tipsView.setBtnVisibility(View.VISIBLE);
	//			break;
	//		case WebResponse.NullPointerException:
	//			tipsView.setTitle("未知的错误！错误代码：" + (String) response.responseObject);
	//			tipsView.setBtnText("刷新数据");
	//			tipsView.setBtnListener(refreshlistener);
	//			tipsView.setBtnVisibility(View.VISIBLE);
	//			break;
	//		}
	//
	//		tipsView.showNetworkErro();
	//	}

	/**
	 * 显示数据相关提示界面
	 * 
	 * @param tipsView
	 * @param tips
	 *            提示语
	 * @param btnText
	 *            按钮文字
	 * @param refreshlistener
	 *            数据刷新监听接口
	 * @param showBtn
	 *            是否显示按钮
	 */
	//	public static void showDataTipsView(TipsView tipsView, String tips,
	//			String btnText, OnClickListener refreshlistener, boolean showBtn) {
	//		tipsView.setVisibility(View.VISIBLE);
	//
	//		tipsView.setTitle(tips);
	//		if (TextUtils.isEmpty(btnText)) {
	//			tipsView.setBtnText("刷    新");
	//		} else {
	//			tipsView.setBtnText(btnText);
	//		}
	//
	//		if (showBtn) {
	//			tipsView.setBtnListener(refreshlistener);
	//			tipsView.setBtnVisibility(View.VISIBLE);
	//		} else {
	//			tipsView.setBtnVisibility(View.GONE);
	//		}
	//		tipsView.showDataNull();
	//	}

	/**
	 * dp转换为px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px装换成dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * sp转换为px
	 */
	public static int sp2px(Context context, float spValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * scale + 0.5f);
	}

	/**
	 * px转换为sp
	 */
	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 验证字符串是否为网址
	 */
	public static boolean isUrl(String url) {
		String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(url);
		return matcher.matches();
	}

	/**
	 * 验证字符串是否为数字
	 */
	public static boolean isNumeric(String url) {
		Pattern patt = Pattern.compile("[0-9]*");
		Matcher matcher = patt.matcher(url);
		return matcher.matches();
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public static int getVersionCode() {
		PackageManager manager = Global.mContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(
					Global.mContext.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}

	/**
	 * 获取版本名
	 * 
	 * @return
	 */
	public static String getVersionName() {
		PackageManager manager = Global.mContext.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(
					Global.mContext.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			return "1.0";
		}
	}

	public static boolean isTablet(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
				+ Math.pow(dm.heightPixels, 2));
		double physicalSize = diagonalPixels / (160 * dm.density);
		return physicalSize > 7;
	}

	/**
	 * 设置margins
	 * 
	 * @return
	 */
	public static void setMargins (View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}
}
