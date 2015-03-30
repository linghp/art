package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbSharedUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.shangxian.art.adapter.HomeGridAdp;
import com.shangxian.art.bean.GoodBean;
import com.shangxian.art.bean.HomeData;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Global;
import com.shangxian.art.view.TagViewPager;
import com.shangxian.art.view.TagViewPager.OnGetView;
import com.shangxian.art.view.TopView;

public class HomeActivity extends AbActivity implements OnHeaderRefreshListener{

	//private TopView topView;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private ListView mGridView = null;
	private TagViewPager viewPager = null;
	private View headView = null;
	//private MarqueeText tv_tips = null;
	//private TextView tv_more = null;
	private Button btn_check, btn_shop, btn_recorder, btn_sign;

	private HomeGridAdp adp = null;

	/** 首页轮播图地址 */
	private List<String> imgList = new ArrayList<String>();
	/** 首页广告数据 */
	private List<String> tipsList = new ArrayList<String>();
	/** 首页热门礼品 */
	private List<GoodBean> goods = new ArrayList<GoodBean>();
	/** 首页数据集合 */
	private HomeData mDatas = new HomeData();

	private AbHttpUtil httpUtil = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.layout_main_home);

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);

		// topView = (TopView) findViewById(R.id.top_title);
		// topView.setActivity(this);
		// topView.setTitle("首页");

		// 获取ListView对象
		mAbPullToRefreshView = (AbPullToRefreshView) this
				.findViewById(R.id.mPullRefreshView);
		mGridView = (ListView) this.findViewById(R.id.mListView);

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.setLoadMoreEnable(false);

		initHeadView();

		mGridView.addHeaderView(headView);

		adp = new HomeGridAdp();
		mGridView.setAdapter(adp);

		// 第一次加载数据
		refreshTask();
	}

	@Override
	protected void onResume() {
		super.onResume();

//		topView = MainActivity.getTopView();
//		topView.setTitle("首页");
//		topView.hideRightBtn();
	}

	private void refreshTask() {
		AbDialogUtil.showLoadDialog(HomeActivity.this,
				R.drawable.progress_circular, "数据加载中...");
		String url = "http://59.36.101.88:8013/app/shop/warehouse/custuihuosaomiao.asp?";
		AbRequestParams params = new AbRequestParams();
		params.put("shopid", "1019");
		params.put("code", "88881110344801123456");
		params.put("phone", "15889936624");
		httpUtil.get(url, params, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				AbDialogUtil.removeDialog(HomeActivity.this);
				mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				AbToastUtil.showToast(HomeActivity.this, error.getMessage());
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				AbToastUtil.showToast(HomeActivity.this, content);
				AbLogUtil.e(HomeActivity.this, content);
//				TestBean bean = (TestBean) AbJsonUtil.fromJson(content,
//						TestBean.class);
//				tv_tips.setText(bean.getPname());

				imgList.clear();
				tipsList.clear();
				goods.clear();

				ArrayList<String> imgs = new ArrayList<String>();
				imgs.add("http://b.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c54cdcbc85a38292df5e0fe7fae.jpg");
				imgs.add("http://h.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec1b0718942d2eb9389a506be1.jpg");
				imgs.add("http://f.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b50ba735b238435e5dde7116e37.jpg");
				imgs.add("http://f.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b50ba735b238435e5dde7116e37.jpg");
				ArrayList<String> tips = new ArrayList<String>();
				tips.add("中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一中国通告一");
				ArrayList<GoodBean> goodlist = new ArrayList<GoodBean>();
				for (int i = 0; i < 3; i++) {
					GoodBean good = new GoodBean();
					good.setContent("中国测试产品:" + i);
					good.setPrice(1000 + i + "");
					good.setImg("http://b.hiphotos.baidu.com/image/pic/item/ae51f3deb48f8c54cdcbc85a38292df5e0fe7fae.jpg");
					goodlist.add(good);
				}

				mDatas.setImgList(imgs);
				mDatas.setTipsList(tips);
				mDatas.setGoods(goodlist);

				if (mDatas != null) {
					if (mDatas.getImgList() != null
							&& mDatas.getImgList().size() > 0) {
						imgList.addAll(mDatas.getImgList());
						viewPager.setVisibility(View.VISIBLE);
						viewPager.setOnGetView(new OnGetView() {

							@Override
							public View getView(ViewGroup container,
									int position) {
								ImageView iv = new ImageView(HomeActivity.this);
								Imageloader_homePager.displayImage(
										imgList.get(position), iv,
										new Handler(), null);
								container.addView(iv);
								return iv;
							}
						});
						viewPager.setAdapter(imgList.size());
					} else {
						viewPager.setVisibility(View.GONE);
					}

					if (mDatas.getTipsList() != null
							&& mDatas.getTipsList().size() > 0) {
						tipsList.addAll(mDatas.getTipsList());
//						tv_tips.setVisibility(View.VISIBLE);
//						tv_tips.setText(tipsList.get(0));
					} else {
//						tv_tips.setVisibility(View.GONE);
					}

					if (mDatas.getGoods() != null
							&& mDatas.getGoods().size() > 0) {
						goods.addAll(mDatas.getGoods());
						adp.updateData(goods);
					}
				}
			}
		});
	}

	/** 初始化头部VIEW */
	private void initHeadView() {
		headView = LayoutInflater.from(this).inflate(
				R.layout.layout_main_home_gridview_head, null);
		viewPager = (TagViewPager) headView.findViewById(R.id.mTagViewPager);
		initTagViewPager();
		//tv_tips = (MarqueeText) headView.findViewById(R.id.tv_tips);
		//tv_more = (TextView) headView.findViewById(R.id.tv_more);
		btn_check = (Button) headView.findViewById(R.id.btn_check);
		btn_shop = (Button) headView.findViewById(R.id.btn_shop);
		btn_recorder = (Button) headView.findViewById(R.id.btn_recorder);
		btn_sign = (Button) headView.findViewById(R.id.btn_sign);

		//tv_more.setOnClickListener(new OnButtonClick(OnButtonClick.MORE));
		btn_check.setOnClickListener(new OnButtonClick(OnButtonClick.CHECK));
		btn_shop.setOnClickListener(new OnButtonClick(OnButtonClick.SHOP));
		btn_recorder.setOnClickListener(new OnButtonClick(
				OnButtonClick.RECORDER));
		btn_sign.setOnClickListener(new OnButtonClick(OnButtonClick.SIGN));
	}

	/** 初始化广告轮播VIEW */
	private void initTagViewPager() {
		viewPager.init(R.drawable.tagvewpager_point01,
				R.drawable.tagvewpager_point02, 14, 5, 2, 20);
		viewPager.setAutoNext(true, 5000);
		AbLogUtil.e(
				this,
				"轮播图宽度"
						+ AbSharedUtil.getInt(HomeActivity.this,
								Global.KEY_SCREEN_WIDTH));

		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewPager
				.getLayoutParams();
		params.width = AbSharedUtil.getInt(HomeActivity.this,
				Global.KEY_SCREEN_WIDTH);
		params.height = (int) (params.width / 2.65);
		viewPager.setLayoutParams(params);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView arg0) {
		refreshTask();
	}

	/** 首页按钮点击事件 */
	class OnButtonClick implements OnClickListener {
		public static final int CHECK = 1;
		public static final int SHOP = 2;
		public static final int RECORDER = 3;
		public static final int SIGN = 4;
		public static final int MORE = 5;

		int mSwitch;

		public OnButtonClick(int mSwitch) {
			this.mSwitch = mSwitch;
		}

		@Override
		public void onClick(View v) {
			switch (mSwitch) {
			case CHECK:
				if (AbSharedUtil.getString(Global.mContext, Global.KEY_ACCOUNT) != null) {
					AbToastUtil.showToast(HomeActivity.this, "积分查询");
				} else {
					AbToastUtil.showToast(HomeActivity.this, "绑定帐号");
				}
				break;

			case SHOP:
				AbToastUtil.showToast(HomeActivity.this, "积分商城");
				break;

			case RECORDER:
				AbToastUtil.showToast(HomeActivity.this, "兑换记录");
				break;

			case SIGN:
				AbToastUtil.showToast(HomeActivity.this, "赚恩宝");
				break;

			case MORE:
				AbToastUtil.showToast(HomeActivity.this, "热兑好礼");
				break;
			}
		}

	}
}
