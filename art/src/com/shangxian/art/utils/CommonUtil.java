package com.shangxian.art.utils;

import java.text.DecimalFormat;
import java.util.UUID;
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
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

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
	 *            int 类型数据
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
	// public static void showNetTipsView(final TipsView tipsView,
	// WebResponse response, OnClickListener refreshlistener) {
	// tipsView.setVisibility(View.VISIBLE);
	//
	// switch (response.state) {
	// case WebResponse.FAIL:
	// tipsView.setTitle("亲，连接超时了哦！\n请检查您的网络并重新刷新数据！");
	// tipsView.setBtnText("刷新数据");
	// tipsView.setBtnListener(refreshlistener);
	// tipsView.setBtnVisibility(View.VISIBLE);
	// break;
	//
	// case WebResponse.XmlPullParserException:
	// tipsView.setTitle("数据解析失败！\n如果您能将错误信息反馈给我们的工程师，\n我们将为您提供更好的体验！");
	// tipsView.setBtnText("我要反馈！");
	// tipsView.setBtnListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// CommonUtil.gotoActivity(tipsView.getActivity(),
	// FeedBackActivity.class, false);
	// }
	// });
	// tipsView.setBtnVisibility(View.VISIBLE);
	// break;
	// case WebResponse.NullPointerException:
	// tipsView.setTitle("未知的错误！错误代码：" + (String) response.responseObject);
	// tipsView.setBtnText("刷新数据");
	// tipsView.setBtnListener(refreshlistener);
	// tipsView.setBtnVisibility(View.VISIBLE);
	// break;
	// }
	//
	// tipsView.showNetworkErro();
	// }

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
	// public static void showDataTipsView(TipsView tipsView, String tips,
	// String btnText, OnClickListener refreshlistener, boolean showBtn) {
	// tipsView.setVisibility(View.VISIBLE);
	//
	// tipsView.setTitle(tips);
	// if (TextUtils.isEmpty(btnText)) {
	// tipsView.setBtnText("刷    新");
	// } else {
	// tipsView.setBtnText(btnText);
	// }
	//
	// if (showBtn) {
	// tipsView.setBtnListener(refreshlistener);
	// tipsView.setBtnVisibility(View.VISIBLE);
	// } else {
	// tipsView.setBtnVisibility(View.GONE);
	// }
	// tipsView.showDataNull();
	// }

	/**
	 * dp转换为px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		//MyLogger.i(scale+"");
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
	public static void setMargins(View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
					.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}

	public static void toast(String str, Context context) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
	
	public static void toast(String str) {
		Toast.makeText(Global.mContext, str, Toast.LENGTH_SHORT).show();
	}

	// 获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	// 获取屏幕的高度
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}
	
	//分享
	private static String picUrl;
	private static String content;
	private static String[] images;
//	public static void showShare(Context context,String content,String titleUrl,String picUrl1) {
//		// picUrl=picUrl1;
//		 ShareSDK.initSDK(context);
//		 OnekeyShare oks = new OnekeyShare();
//		 //关闭sso授权
//		 oks.disableSSOWhenAuthorize(); 
//
//		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		 oks.setTitle(context.getString(R.string.share));
//		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		 oks.setTitleUrl("http://sharesdk.cn");
//		 // text是分享文本，所有平台都需要这个字段
//		 oks.setText("我是分享文本");
//		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//		 // url仅在微信（包括好友和朋友圈）中使用
//		 oks.setUrl("http://sharesdk.cn");
//		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		 oks.setComment("我是测试评论文本");
//		 // site是分享此内容的网站名称，仅在QQ空间使用
//		 oks.setSite(context.getString(R.string.app_name));
//		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		 oks.setSiteUrl("http://sharesdk.cn");
//
//		// 启动分享GUI
//		 oks.show(context);
//		 }
	
	public static void showShare(Context context,String content1,String titleUrl,String picUrl1,String[] images1) {
		content=content1;
		images=images1;
		if(images!=null&&images.length>0){
			picUrl1=images[0];
		}
		picUrl=picUrl1;
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(context.getString(R.string.app_name));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(titleUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(content1);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(titleUrl);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(titleUrl);
		if(titleUrl!=null)
		oks.setImageUrl(picUrl1);
//		if(images!=null&&images.length>0)
//		oks.setImageUrl(images[0]);
		// 启动分享GUI
		oks.show(context);
	}
	

	/**
	 * 快捷分享项目现在添加为不同的平台添加不同分享内容的方法。
	 *本类用于演示如何区别Twitter的分享内容和其他平台分享内容。
	 */
	public static class ShareContentCustomizeDemo implements ShareContentCustomizeCallback {
	 
	        public void onShare(Platform platform, ShareParams paramsToShare) {
	                if (QZone.NAME.equals(platform.getName())) {
	                        //String text = platform.getContext().getString(R.string.share_content_short);
	                        paramsToShare.setImageUrl(picUrl);
	                }else if (WechatMoments.NAME.equals(platform.getName())) {
	                	paramsToShare.setTitle(content);
	                	paramsToShare.setText("");
	                }else if (SinaWeibo.NAME.equals(platform.getName())) {
	                	//paramsToShare.setImageArray(images);
	                }
	        }
	 
	}
	
	//价格除以100
	public static String priceConversion(double price) {
		MyLogger.i(">>>>>>>>>>>>>>>>>>>>>>>"+price);
		DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String str=decimalFormat.format(price/100.0);
		return str;
	}
	
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
}
