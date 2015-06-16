package com.shangxian.art;

import java.util.Timer;
import java.util.TimerTask;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbSharedUtil;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.constant.Global;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.LocalUserInfo;
import com.shangxian.art.view.TopView;
import com.shangxian.art.zxing.CaptureActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnClickListener{
	
	private static TopView topView;

	private TabHost tabhost;
	private TabHost.TabSpec first;
	private TabHost.TabSpec second;
	private TabHost.TabSpec third;
	private TabHost.TabSpec fourth;
	private TabHost.TabSpec fifth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		initScreenData();
		init();
	}

	private void init() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		
		tabhost = this.getTabHost();

		first = tabhost.newTabSpec("1");
		second = tabhost.newTabSpec("2");
		third = tabhost.newTabSpec("3");
		fourth = tabhost.newTabSpec("4");
		fifth = tabhost.newTabSpec("5");

		first.setIndicator(createContent("首页", R.drawable.first_tab));
		second.setIndicator(createContent("分类", R.drawable.second_tab));
		third.setIndicator(createContent("购物篮", R.drawable.third_tab));
		fourth.setIndicator(createContent("附近", R.drawable.fourth_tab));
		fifth.setIndicator(createContent("我的", R.drawable.fifth_tab));

		// 绑定显示的页面
		// first.setContent(R.id.ll_first);
		first.setContent(new Intent(this, HomeActivity.class));
		second.setContent(new Intent(this, ClassificationActivity.class));
		third.setContent(new Intent(this,ShoppingcartActivity.class));
		fourth.setContent(new Intent(this, NearlyActivity.class));
		fifth.setContent(new Intent(this, MineActivity.class));
		// 将选项卡加进TabHost
		tabhost.addTab(first);
		tabhost.addTab(second);
		tabhost.addTab(third);
		tabhost.addTab(fourth);
		tabhost.addTab(fifth);
		tabhost.setCurrentTab(0);
		// 设置tabHost切换时动态更改图标
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				tabChanged(tabId);
			}

		});
	}	
	
	public static TopView getTopView(){
		return topView;
	}
	


	// 捕获tab变化事件
	private void tabChanged(String tabId) {
		// 当前选中项
		if (tabId.equals("1")) {
			tabhost.setCurrentTabByTag("首页");
		} else if (tabId.equals("2")) {
			tabhost.setCurrentTabByTag("分类");
		} else if (tabId.equals("3")&&isLoginAndToLogin()) {
			tabhost.setCurrentTabByTag("购物篮");
		} else if (tabId.equals("4")) {
			tabhost.setCurrentTabByTag("附近");
			NearlyActivity.isMainClick=true;
		}else if (tabId.equals("5")) {
			tabhost.setCurrentTabByTag("我的");
		}
	}
	
	protected boolean isLoginAndToLogin(){
		if (LocalUserInfo.getInstance(this).getBoolean(Constant.PRE_LOGIN_STATE, false)) {
			return true;
		} else {
			CommonUtil.gotoActivity(this, LoginActivity.class, false);
			return false;
		}
	}

	// 返回单个选项
	private View createContent(String text, int resid) {
		View view = LayoutInflater.from(this).inflate(R.layout.layout_tabwidget, null,
				false);
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		ImageView iv_icon = (ImageView) view.findViewById(R.id.img_name);
		tv_name.setText(text);
		iv_icon.setBackgroundResource(resid);
		return view;
	}

	private void initScreenData() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		Rect rect = new Rect();
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

		if (dm.widthPixels > 0 && dm.heightPixels > 0) {
			AbSharedUtil.putInt(this, Global.KEY_SCREEN_WIDTH, dm.widthPixels);
			AbSharedUtil
					.putInt(this, Global.KEY_SCREEN_HEIGHT, dm.heightPixels);
			AbSharedUtil.putInt(this, Global.KEY_SCREEN_TOPBAR, rect.top);
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			exitBy2Click();
			return false;
		} else {
			return super.dispatchKeyEvent(event);
		}
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			//myToast("扫描");
			CommonUtil.gotoActivity(this, CaptureActivity.class, false);
			break;
		case R.id.btn_right:
			//myToast("定位");
			Bundle bundle = new Bundle();
			bundle.putInt(Constant.INT_LOC_TOTYPE, Constant.MAP_NEARLY_LOC);
			CommonUtil.gotoActivityWithData(this, LocationActivity.class, bundle, false);
			break;
		case R.id.ll_center:
			//myToast("搜索");
			CommonUtil.gotoActivity(this, SearchsActivity.class, false);
			break;
		default:
			break;
		}
	}
	
	protected void myToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}