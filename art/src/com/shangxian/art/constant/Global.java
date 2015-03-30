package com.shangxian.art.constant;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class Global {
	public static Context mContext;

	public static LayoutInflater mInflater;

	/** 项目文件根路径 */
	public static String fileSavePath = "";
	/** 程序异常相关根路径 */
	public static String exceptionSavePath = "";
	/** 个人相关根路径 */
	public static String userSavePath = "";
	/** 下载包根路径 */
	public static String apkSavePath = "";
	/** 下载包完整路径 */
	public static String apkSaveFileName = "";
	/** 临时文件路径 */
	public static String tempFilePath = "";

	public static final String KEY_SCREEN_WIDTH = "screen_width";
	public static final String KEY_SCREEN_HEIGHT = "screen_height";
	public static final String KEY_SCREEN_TOPBAR = "screen_topbar";

	public static final String KEY_USERINFO = "userinfo";
	public static final String KEY_ACCOUNT = "user_account";
	public static final String KEY_PSW = "user_psw";

	public static LinearLayout.LayoutParams PARAM_MP_WC = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	public static LinearLayout.LayoutParams PARAM_WC_WC = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	public static LinearLayout.LayoutParams PARAM_MP_MP = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
}