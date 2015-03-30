package com.shangxian.art.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
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
	private ImageView btn_back;
	private Button btn_left, btn_right;
	private TextView tv_title;
	private RelativeLayout rl_title;

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
		btn_back = (ImageView) mRootView.findViewById(R.id.btn_back);
		btn_left = (Button) mRootView.findViewById(R.id.btn_left);
		btn_right = (Button) mRootView.findViewById(R.id.btn_right);
		rl_title = (RelativeLayout) mRootView.findViewById(R.id.rl_title);
		
		hideLefttBtn();
		hideRightBtn();
		hideBackBtn();
		btn_back.setOnClickListener(mListener);
		
		addView(mRootView, Global.PARAM_MP_WC);
		
		mRootView.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LayoutParams params = (LayoutParams) mRootView.getLayoutParams();
				params.height = CommonUtil.dip2px(Global.mContext, 49);
				mRootView.setLayoutParams(params);
			}
		});
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
				mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		}
	};
	
	/**
	 * 重写返回按钮事件
	 */
	public void setBackBtnListener(OnClickListener listener){
		btn_back.setOnClickListener(listener);
	}
	
	/**
	 * 隐藏返回按钮
	 */
	public void hideBackBtn(){
		btn_back.setVisibility(View.GONE);
	}
	
	/**
	 * 显示返回按钮
	 */
	public void showBackBtn(){
		btn_back.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 设置返回按钮背景图
	 * @param resid 背景图资源id
	 */
	public void setBackBtnBackground(int resid){
		btn_back.setBackgroundResource(resid);
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
		btn_right.setText(name);
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
		btn_right.setBackgroundResource(resid);
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
	 * 获取右按钮实例
	 * @return
	 */
	public Button getRightBtn(){
		return btn_right;
	}
	
	
	//////////////////
	// 左按钮操作方法实现  //
	//////////////////
	
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
		btn_left.setText(name);
	}
	
	/**
	 * 设置左按钮背景图
	 * @param resid 背景图资源id
	 */
	public void setLeftBtnDrawalbe(int resid){
		btn_left.setBackgroundResource(resid);
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
	public void hideLefttBtn(){
		btn_left.setVisibility(View.GONE);
	}
	
	/**
	 * 获取左按钮实例
	 * @return
	 */
	public Button getLeftBtn(){
		return btn_left;
	}
	
	//////////////////
	// 标题操作方法实现  //
	//////////////////
	
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	public void setActivity(Activity curActivity){
		this.mActivity = curActivity;
	}
}
