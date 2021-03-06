package com.shangxian.art.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.constant.Global;
import com.shangxian.art.utils.CommonUtil;

/**
 * 自定义标题栏
 * @author jewel
 *
 */
/**
 * 自定义标题栏
 * @author jewel
 *
 */
public class TopView extends RelativeLayout {
	
	private Activity mActivity; // 当前activity
	private View mRootView;
	private View ll_center;
	private EditText et_search;
	//private Button btn_back;
	private ImageView btn_left, btn_right;
	private TextView tv_title;
	private RelativeLayout rl_title;
	private TextView rt_name;

	public TopView(Context context) {
		super(context); 
		if(isInEditMode()){
			return ;
		}
		init();
	}
	
	public TopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()){
			return ;
		}
		init();
	}
	
	private void init(){
		if(Global.mInflater!=null){
		mRootView = Global.mInflater.inflate(R.layout.layout_top, null);
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		//btn_back = (ImageView) mRootView.findViewById(R.id.btn_back);
		btn_left = (ImageView) mRootView.findViewById(R.id.btn_left);
		ll_center = mRootView.findViewById(R.id.ll_center);
		et_search = (EditText) mRootView.findViewById(R.id.et_search);
		btn_right = (ImageView) mRootView.findViewById(R.id.btn_right);
		rl_title = (RelativeLayout) mRootView.findViewById(R.id.rl_title);
		rt_name = (TextView) mRootView.findViewById(R.id.topt_tv_right);
		
//		hideLefttBtn();
//		hideRightBtn();
//		hideBackBtn();
		//btn_back.setOnClickListener(mListener);
		
		addView(mRootView, Global.PARAM_MP_WC);
		
		mRootView.post(new Runnable() {
			@Override
			public void run() {
				LayoutParams params = (LayoutParams) mRootView.getLayoutParams();
				params.height = CommonUtil.dip2px(Global.mContext, 49);
				mRootView.setLayoutParams(params);
			}
		});
		}
	}
	
	public void setCneterHint(String name){
		if (!TextUtils.isEmpty(name)) {
			et_search.setHint(name);
		}
	}
	
	/**
	 * 设置标题背景
	 */
	public void setTopViewBackground(int resid){
		rl_title.setBackgroundResource(resid);
	}
	
	/**
	 * 返回按钮固定点击事件
	 */
	private OnClickListener mListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mActivity != null){
				mActivity.finish();
				//mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		}
	};
	
	/**
	 * 设置返回的一系列事件
	 * @param listener
	 */
	public void setBack(int resid){
		setLeftBtnDrawalbe(resid);
		btn_left.setOnClickListener(mListener);
	}
	
	//////////////////
	// 右按钮操作方法实现  //
	//////////////////
	
	/**
	 * 右按钮点击事件
	 * @param listener
	 */
	public void setRightBtnListener(OnClickListener listener){
		btn_right.setOnClickListener(listener);
	}
	
	/**
	 * 设置右按钮文字
	 * @param name
	 */
	public void setRightBtnText(String name){
//		btn_right.setText(name);
	}
	
	/**
	 * 设置右按钮宽高
	 * @param width
	 * @param height
	 */
	public void setRightBtnSize(int width, int height){
		LayoutParams params = (LayoutParams) btn_right.getLayoutParams();
		params.width = width;
		params.height = height;
		btn_right.setLayoutParams(params);
	}
	
	/**
	 * 设置右按钮背景图
	 * @param resid 背景图资源id
	 */
	public void setRightBtnDrawable(int resid){
		btn_right.setImageResource(resid);
	}
	
	/**
	 * 显示右按钮
	 */
	public void showRightBtn(){
		btn_right.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏右按钮
	 */
	public void hideRightBtn(){
		btn_right.setVisibility(View.GONE);
	}
	/**
	 * 隐藏右按钮
	 */
	public void hideRightBtn_invisible(){
		btn_right.setVisibility(View.INVISIBLE);
		btn_right.setOnClickListener(null);
	}
	
	/**
	 * 获取右按钮实例
	 * @return
	 */
//	public Button getRightBtn(){
//		return btn_right;
//	}
	
	
	//////////////////
	// 左按钮操作方法实现  //
	//////////////////
	
	/**
	 * 中间点击事件
	 * @param listener
	 */
	public void setCenterListener(OnClickListener listener){
		ll_center.setOnClickListener(listener);
	}

	public void showCenterSearch(){
		ll_center.setVisibility(View.VISIBLE);
	}
	
/**
 * 隐藏搜索框
 * @param hideCenterSearch 
 */
	public void hideCenterSearch(){
		ll_center.setVisibility(View.GONE);
	}
	public void showTitle(){
		tv_title.setVisibility(View.VISIBLE);
	}
	
	
	public void hideTitle(){
		tv_title.setVisibility(View.GONE);
	}
	
	/**
	 * 左按钮点击事件
	 * @param listener
	 */
	public void setLeftBtnListener(OnClickListener listener){
		btn_left.setOnClickListener(listener);
	}
	
	/**
	 * 设置左按钮文字
	 * @param name
	 */
	public void setLeftBtnText(String name){
		//btn_left.setText(name);
	}
	
	/**
	 * 设置左按钮背景图
	 * @param resid 背景图资源id
	 */
	public void setLeftBtnDrawalbe(int resid){
		btn_left.setImageResource(resid);
	}
	
	/**
	 * 显示左按钮
	 */
	public void showLeftBtn(){
		btn_left.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏左按钮
	 */
	public void hideLeftBtn(){
		btn_left.setVisibility(View.GONE);
	}
	/**
	 * 隐藏左按钮保留位置
	 */
	public void hideLeftBtnz(){
		btn_left.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 设置右边按钮文字
	 */
	public void setRightText(String name, OnClickListener listener){
		rt_name.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.GONE);
		rt_name.setText(name);
		rt_name.setOnClickListener(listener);
	}
	
	/**
	 * 获取左按钮实例
	 * @return
	 */
//	public Button getLeftBtn(){
//		return btn_left;
//	}
	
	//////////////////
	// 标题操作方法实现  //
	//////////////////
	
	public void setTitle(String title){
		tv_title.setText(title);
		tv_title.setVisibility(View.VISIBLE);
	}
	
	public void setActivity(Activity curActivity){
		this.mActivity = curActivity;
	}
	
	public ImageView getRightbtn(){
		return btn_right;
	}
}
