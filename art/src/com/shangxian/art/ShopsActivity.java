package com.shangxian.art;

import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import com.ab.util.dct.IFDCT;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 商家情况
 * @author Administrator
 *
 */
public class ShopsActivity extends BaseActivity{
	ImageView img,shopsimg,collectionimg,img1,img2;
	TextView shopsname,guanzu,all,up,youhui,summary1,price1,summary2,price2;
	
	//判断是否收藏
	boolean iscollection = false;
	//popupwindow
	private PopupWindow popupWindow;
	private View view;
	private TextView fenlei,jianjie;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shops);
		initView();
		initData();
		initListener();
		initPopupWindow();
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.more1);
		topView.showCenterSearch();
		topView.setBack(R.drawable.back);//返回
		
		img = (ImageView) findViewById(R.id.shops_img);
		shopsimg = (ImageView) findViewById(R.id.shops_shopsimg);
		collectionimg = (ImageView) findViewById(R.id.shops_collectionimg);
		img1 = (ImageView) findViewById(R.id.shops_img1);
		img2 = (ImageView) findViewById(R.id.shops_img2);
		
		shopsname= (TextView) findViewById(R.id.shops_shopsname);
		guanzu= (TextView) findViewById(R.id.shops_guanzu);
		all= (TextView) findViewById(R.id.shops_all);
		up= (TextView) findViewById(R.id.shops_up);
		youhui= (TextView) findViewById(R.id.shops_youhui);
		summary1= (TextView) findViewById(R.id.shops_summary1);
		price1= (TextView) findViewById(R.id.shops_price1);
		summary2= (TextView) findViewById(R.id.shops_summary2);
		price2= (TextView) findViewById(R.id.shops_price2);
	}

	private void initData() {
		// TODO Auto-generated method stub
		
	}

	private void initListener() {
		// TODO Auto-generated method stub
		topView.setRightBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.showAsDropDown(topView, 460, -30);
			}
		});
		//收藏
		collectionimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isLoginAndToLogin()){
				if (!iscollection) {
					collectionimg.setImageResource(R.drawable.collection_on);
					myToast("已收藏");
				}else {
					collectionimg.setImageResource(R.drawable.collection_off);
					myToast("取消收藏");
				}
				iscollection = !iscollection;
				}
			}
		});
	}
	private void initPopupWindow() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		//引入窗口配置文件
		view = inflater.inflate(R.layout.shops_popupwindow, null);
		//创建popupwindow对象
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,false);
		//		popupWindow.setBackgroundDrawable(R.drawable.background);
				
		//		popupWindow.setHeight(500);
		//点击外边可消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//设置点击外边窗口消失
		popupWindow.setOutsideTouchable(true);
		//设置可获得焦点
		popupWindow.setFocusable(true);
		//popupwindow是否消失监听
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				
			}
		});	
		//得到popupwindow里面的控件
		fenlei = (TextView) view.findViewById(R.id.shops_popup_fenlei);
		jianjie = (TextView) view.findViewById(R.id.shops_popup_jianjie);
		fenlei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
//				CommonUtil.gotoActivity(ShopsActivity.this, ClassificationActivity.class, true);
			}
		});
		jianjie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				CommonUtil.gotoActivity(ShopsActivity.this, ShopsSummaryActivity.class, false);
			}
		});

	}
}
