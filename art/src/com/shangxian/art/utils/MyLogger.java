package com.shangxian.art.utils;

/**   
 * @Title: MyLogger.java 
 * @Package com.coney.logger
 * Copyright (C) 2014 coney geng <coneylife@gmail.com> 
 * @Description: my logger for my development
 * @author coney Geng
 * @date 2014年9月22日 下午5:15:36 
 * @version V1.0   
 */


import android.util.Log;

import com.shangxian.art.BuildConfig;


/**
 * User: kulik Date: 2/8/13 Time: 5:56 PM
 */
public class MyLogger {

	private static String mTag = "";

	private final static boolean DEBUG = BuildConfig.DEBUG;

	public MyLogger(Class clazz, String mAppTag) {
		// TODO Add Proguard config a.a.b.A != A
		mTag = mAppTag + clazz.getSimpleName();
	}

	static String className;
	static String methodName;
	static int lineNumber;

	public static boolean isDebuggable() {
		return DEBUG;
	}

	public static void setMyTags(String tag) {
		mTag = tag;
	}

	private static String createLog(String log) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(methodName);
		buffer.append(":");
		buffer.append(lineNumber);
		buffer.append("]");
		buffer.append(log);

		return buffer.toString();
	}

	private static String createTag(String tag) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(className);
		buffer.append("]");
		buffer.append(tag);

		return buffer.toString();
	}

	private static void getMethodNames(StackTraceElement[] sElements) {
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}

	public static void e(String message) {
		if (!isDebuggable())
			return;

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.e(className, createLog(message));
		else
			Log.e(createTag(mTag), createLog(message));
	}

	public static void e(String tag, String message) {
		if (!isDebuggable())
			return;

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		Log.e(createTag(tag), createLog(message));
	}

	public static void i(String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.i(className, createLog(message));
		else
			Log.i(createTag(mTag), createLog(message));
	}

	public static void i(String tag, String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.i(createTag(tag), createLog(message));
	}

	public static void d(String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.d(className, createLog(message));
		else
			Log.d(createTag(mTag), createLog(message));
	}

	public static void d(String tag, String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.d(createTag(tag), createLog(message));
	}

	public static void v(String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.v(className, createLog(message));
		else
			Log.v(createTag(mTag), createLog(message));
	}

	public static void v(String tag, String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.v(createTag(tag), createLog(message));
	}

	public static void w(String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.w(className, createLog(message));
		else
			Log.w(createTag(mTag), createLog(message));
	}

	public static void w(String tag, String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.w(createTag(tag), createLog(message));
	}

	public static void wtf(String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		if (mTag == "" || mTag == null)
			Log.wtf(className, createLog(message));
		else
			Log.wtf(createTag(mTag), createLog(message));
	}

	public static void wtf(String tag, String message) {
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.wtf(createTag(tag), createLog(message));
	}

}
