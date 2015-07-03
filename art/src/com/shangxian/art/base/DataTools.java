package com.shangxian.art.base;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;


public class DataTools {
	private Map<String, Serializable> datas;
	private Map<String, List<? extends Serializable>> datass;
	
	private Application app;
	private static DataTools dao;
	
	//屏幕相关
	private DisplayMetrics metrics;
	public static int w_screeen;
	public static int h_screeen;
	
	private DataTools(){
		datas = new HashMap<String, Serializable>();
		datass = new HashMap<String, List<? extends Serializable>>();
	}
	
	public static DataTools newInstance(){
		if (dao == null) {
			dao = new DataTools();
		}
		return dao;
	}
	
	public void initApplication(Application app){
		this.app = app;
		this.metrics = getApplicationContext().getResources().getDisplayMetrics();
		this.w_screeen = metrics.widthPixels;
		this.h_screeen = metrics.heightPixels;
	}
	
	public Context getApplicationContext(){
		return app.getApplicationContext();
	}
	
	public void put(String key, Serializable data){
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (data == null){
			throw new NullPointerException("data is null");
		}
		this.datas.put(key, data);
	}
	
	public void put(String key, List<? extends Serializable> data){
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (data == null || data.size() == 0){
			throw new NullPointerException("data is null");
		}
		this.datass.put(key, data);
	}
	
	public Serializable getDatas(String key) {
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (this.datas.containsKey(key)) {
			return this.datas.get(key);
		} else {
			return null;
		}
	}
	
	public List<? extends Serializable> getDatass(String key) {
		if (TextUtils.isEmpty(key)) {
			throw new NullPointerException("key is null");
		}
		if (this.datas.containsKey(key)) {
			return this.datass.get(key);
		} else {
			return null;
		}
	}
	
	
	
	
	 private static final String attach = "dqm";
	    // 十六进制下数字到字符的映射数组
	    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    /**
     * 加密
     *
     * @param inputString
     * @return
     */
    public static String generatePassword(String inputString) {
        inputString += attach;
        return encodeByMD5(inputString);
    }

//    /**
//     * 密码校验
//     *
//     * @param password
//     * @param inputString
//     * @return
//     */
//    public static boolean validatePassword(String password, String inputString) {
//        inputString += attach;
//        if (password.equals(encodeByMD5(inputString))) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
