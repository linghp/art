package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import com.shangxian.art.adapter.FragmentViewPagerAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.fragment.BuyerReturnOrderFragment;
import com.shangxian.art.fragment.MyOrder_All_Fragment;
import com.shangxian.art.fragment.SellerOrder_All_Fragment;
import com.shangxian.art.view.TopView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 退款订单
 * @author Administrator
 *                          _ooOoo_
                           o8888888o
                           88" . "88
                           (| -_- |)
                            O\ = /O
                        ____/`---'\____
                      .   ' \\| |// `.
                       / \\||| : |||// \
                     / _||||| -:- |||||- \
                       | | \\\ - /// | |
                     | \_| ''\---/'' | |
                      \ .-\__ `-` ___/-. /
                   ___`. .' /--.--\ `. . __
                ."" '< `.___\_<|>_/___.' >'"".
               | | : `- \`.;`\ _ /`;.`/ - ` : | |
                 \ \ `-. \_ __\ /__ _/ .-` / /
         ======`-.____`-.___\_____/___.-`____.-'======
                            `=---='

         .............................................
                  佛祖保佑             永无BUG
                    佛曰:
                  写字楼里写字间，写字间里程序员；
                  程序人员写程序，又拿程序换酒钱。
                  酒醒只在网上坐，酒醉还来网下眠；
                  酒醉酒醒日复日，网上网下年复年。
                  但愿老死电脑间，不愿鞠躬老板前；
                  奔驰宝马贵者趣，公交自行程序员。
                  别人笑我忒疯癫，我笑自己命太贱；
                  不见满街漂亮妹，哪个归得程序员？
 *
 */
public class BuyerReturnOrderActivity extends BaseActivity implements
		OnClickListener {

	private LinearLayout ll_tab1;
	private LinearLayout ll_tab2;
	private LinearLayout ll_tab3;
	private LinearLayout ll_tab4;
	private TextView tv_tab2;
	private TextView tv_tab3;
	private TextView tv_tab4;
	private ViewPager vp_content;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private FragmentViewPagerAdp adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_seller_order);
		initViews();
		initDatas();
		Listener();
	}
	
	private void Listener() {
		ll_tab1.setOnClickListener(this);
		ll_tab2.setOnClickListener(this);
		ll_tab3.setOnClickListener(this);
		ll_tab4.setOnClickListener(this);
		changeUi(0);

		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			private int curIndex;

			@Override
			public void onPageSelected(int position) {
				changeUi(position);
				curIndex = position;
				((BuyerReturnOrderFragment)fragments.get(position)).getData();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initDatas() {
		tv_tab2.setText("待审核");
		tv_tab3.setText("待退货");
		tv_tab4.setText("待退款");
		topView.setTitle("退款订单管理");
		fragments.add(0, new BuyerReturnOrderFragment(this,""));
		fragments.add(1, new BuyerReturnOrderFragment(this,SellerOrderActivity.orderReturnStatus[2]));
		fragments.add(2, new BuyerReturnOrderFragment(this,SellerOrderActivity.orderReturnStatus[3]));
		fragments.add(3, new BuyerReturnOrderFragment(this,SellerOrderActivity.orderReturnStatus[4]));
		
		adapter = new FragmentViewPagerAdp(getSupportFragmentManager(),
				fragments);
		vp_content.setAdapter(adapter); 
	}

	private void initViews() {
		// 改变topbar
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);

		ll_tab1 = (LinearLayout) findViewById(R.id.ll_tab1);
		ll_tab2 = (LinearLayout) findViewById(R.id.ll_tab2);
		ll_tab3 = (LinearLayout) findViewById(R.id.ll_tab3);
		ll_tab4 = (LinearLayout) findViewById(R.id.ll_tab4);

		tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
		tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
		tv_tab4 = (TextView) findViewById(R.id.tv_tab4);

		vp_content = (ViewPager) findViewById(R.id.vp_content);
//		vp_content.setOffscreenPageLimit(0);
	}

	public void initDateFirstFragment() {
		((BuyerReturnOrderFragment) fragments.get(0)).getData();
	}
	
	private void changeUi(int i) {
		ll_tab1.setSelected(false);
		ll_tab2.setSelected(false);
		ll_tab3.setSelected(false);
		ll_tab4.setSelected(false);
		switch (i) {
		case R.id.ll_tab1:
		case 0:
			vp_content.setCurrentItem(0);
			ll_tab1.setSelected(true);
			break;
		case R.id.ll_tab2:
		case 1:
			vp_content.setCurrentItem(1);
			ll_tab2.setSelected(true);
			break;
		case R.id.ll_tab3:
		case 2:
			vp_content.setCurrentItem(2);
			ll_tab3.setSelected(true);
			break;
		case R.id.ll_tab4:
		case 3:
			vp_content.setCurrentItem(3);
			ll_tab4.setSelected(true);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		changeUi(v.getId());
	}
	
	
}
