package com.shangxian.art.constant;



public class Constant {
	//public static final String BASEURL = "http://192.168.1.125:8888/art/";
	public static final String BASEURL = "http://www.ainonggu666.com";
	public static final String CONTENT = "/api";
	public static final String HOME = "/ads";//动态布局的数据
	public static final String CATEGORYS = "/categorys";//分类
	public static final String CART = "/cart";//购物车
	public static final String ORDER = "/order/from/cart";//下订单
	public static final String ORDERS = "/orders";//我的订单
//	public static final String SHOPSLIST = "/1/shops";//
	
	
	
    
    // 连接超时
 	public static final int timeOut = 10000;
 	// 建立连接
 	public static final int connectOut = 12000;
 	// 获取数据
 	public static final int getOut = 60000;
 	
 	//1表示已下载完成
 	public static final int downloadComplete = 1;
 	//1表示未开始下载
 	public static final int undownLoad = 0;
 	//2表示已开始下载
 	public static final int downInProgress = 2;
 	//3表示下载暂停
 	public static final int downLoadPause = 3;
 	
 	public static final String BASEURL1 = "http://www.amsoft.cn/";
 	
 	
    /**
     * 
     * ------------------------------ 偏好设置相关(PRE开头) -----------------------------------
     * 
     */
 	
 	public static final String PRE_USER_ID = "user_id";
 	public static final String PRE_USER_LOGINTYPE = "user_loginType";
 	public static final String PRE_USER_NICKNAME = "user_nickName";
 	public static final String PRE_USER_PHONENUMBER = "user_phoneNumber";
 	public static final String PRE_USER_SCALEPHOTO = "user_scalePhoto";
 	
 	public static final String PRE_LOGIN_STATE = "pre_isLogin";
 	public static final String PRE_LOGIN_USERNAME = "pre_username";//上一次登录用户名
 	public static final String PRE_LOGIN_PASSWORD = "pre_password";//上一次登录密码
 	public static final String PRE_LOGIN_LASTTIME = "pre_lasttime";//上一次登录时间
 	
 	public static final String INT_SHOPS_2_LOC = "int_shops_2_loc";
 	public static final String INT_LOC_TOTYPE = "int_loc_totype";
 	
 	/***
 	 * 
 	 * ------------------------------------ 地图相关 （MAP开头） ------------------------------------------
 	 * 
 	 */
 	public static final int MAP_SHOPS_2_LOC = 0x00001000;
 	public static final int MAP_NEARLY_LOC = 0x00001001;
}
