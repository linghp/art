package com.shangxian.art.utils;


import com.shangxian.art.bean.UserInfo;
import com.shangxian.art.constant.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalUserInfo {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "local_userinfo";
    public static final String USERPHOTO_FILENAME = "userphotoname";//头像图片文件名
    private static SharedPreferences mSharedPreferences;
    private static LocalUserInfo mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    private LocalUserInfo(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取instance实例
     * 
     * @param cxt
     * @return
     */
    public static LocalUserInfo getInstance(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new LocalUserInfo(cxt);
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    //
    public void setUserInfo(String str_name, String str_value) {
        editor.putString(str_name, str_value);
        editor.commit();
    }
    
    public void deleteUserInfo() {
    	editor.clear();
    	editor.commit();
    }

    public String getUserInfo(String str_name) {
        return mSharedPreferences.getString(str_name, "");
    }
    
    public boolean contains(String key) {
		return mSharedPreferences.contains(key);
	}

	/**
	 * 保存键为key的值为vlaue
	 * 
	 * @param key
	 * @param vlaue
	 */
	public void put(String key, int vlaue) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(key, vlaue);
		editor.commit();
	}

	/**
	 * 保存键为key的值为vlaue
	 * 
	 * @param key
	 * @param vlaue
	 */
	public void put(String key, String vlaue) {
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, vlaue);
		editor.commit();
	}

	/**
	 * 保存键为key的值为vlaue
	 * 
	 * @param key
	 * @param vlaue
	 */
	public void put(String key, boolean vlaue) {
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, vlaue);
		editor.commit();
	}

	/**
	 * 保存键为key的值为vlaue
	 * 
	 * @param key
	 * @param vlaue
	 */
	public void put(String key, long vlaue) {
		Editor editor = mSharedPreferences.edit();
		editor.putLong(key, vlaue);
		editor.commit();
	}

	/**
	 * 保存键为key的值为vlaue
	 * 
	 * @param key
	 * @param vlaue
	 */
	public void put(String key, float vlaue) {
		Editor editor = mSharedPreferences.edit();
		editor.putFloat(key, vlaue);
		editor.commit();
	}

	/**
	 * key对应的整型值叠加1
	 * 
	 * @param key
	 */
	public void superposition(String key) {
		int vlaue = mSharedPreferences.getInt(key, 0);
		Editor editor = mSharedPreferences.edit();
		vlaue++;
		editor.putFloat(key, vlaue);
		editor.commit();
	}

	public int getInt(String key, int defult) {
		return mSharedPreferences.getInt(key, defult);
	}

	public int getInt(String key) {
		return mSharedPreferences.getInt(key, 0);
	}

	public boolean getBoolean(String key) {
		return mSharedPreferences.getBoolean(key, true);
	}

	public boolean getBoolean(String key, boolean isTrue) {
		return mSharedPreferences.getBoolean(key, isTrue);
	}

	public String getString(String key) {
		return mSharedPreferences.getString(key, null);
	}

	public String getString(String key, String defult) {
		return mSharedPreferences.getString(key, defult);
	}

	public long getLong(String key, long defult) {
		return mSharedPreferences.getLong(key, defult);
	}

	public long getLong(String key) {
		return mSharedPreferences.getLong(key, 0);
	}

	public void remove(String... keys) {
		Editor editor = mSharedPreferences.edit();
		for (String key : keys) {
			editor.remove(key);
		}
		editor.commit();
	}
	
	/**
	 * 保存用户信息
	 * @param user
	 */
	public void putUser(UserInfo user) {
		put(Constant.PRE_USER_ID, user.getId());
		put(Constant.PRE_USER_LOGINTYPE, user.getLoginType());
		put(Constant.PRE_USER_NICKNAME, user.getNickName());
		put(Constant.PRE_USER_PHONENUMBER, user.getPhoneNumber());
		put(USERPHOTO_FILENAME, user.getScalePhoto());
		put(Constant.PRE_LOGIN_USERNAME,
				user.getPhoneNumber());
		put(Constant.PRE_LOGIN_LASTTIME,
				System.currentTimeMillis());
		put(Constant.PRE_LOGIN_STATE, true);
		put("payed", user.isPayed());
	}
	
	/**
	 * 获取当前用户
	 * @return 
	 */
	public UserInfo getUser(){
		UserInfo info = new UserInfo(getInt(Constant.PRE_USER_ID, Integer.MIN_VALUE),
				getInt(Constant.PRE_USER_LOGINTYPE, Integer.MIN_VALUE),
				getString(Constant.PRE_USER_NICKNAME),
				getString(Constant.PRE_USER_PHONENUMBER),
				getString(USERPHOTO_FILENAME));
		info.setPayed(getBoolean("payed", false));
		return info;
	}
}
