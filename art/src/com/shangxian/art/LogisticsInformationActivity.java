package com.shangxian.art;

import java.util.List;
import java.util.regex.Matcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shangxian.art.adapter.ExpressPopuAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ExpressInfo;
import com.shangxian.art.net.BuyerOrderServer;
import com.shangxian.art.net.CallBack;
import com.shangxian.art.net.SellerOrderServer;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.PopupWindowHelper;
import com.shangxian.art.view.TopView;

/**
 * 填写物流信息
 * 
 * @author libo
 */
public class LogisticsInformationActivity extends BaseActivity {
	private EditText et_expressCompany;
	private EditText et_expressNum;
	private TextView tv_quxiao,tv_tijiao;
	private View popuView;
	private ListView lv_express;
	private PopupWindowHelper popuWindowHelper;
	private ExpressPopuAdapter expresAdapter;
	private List<ExpressInfo> expressInfos;
	private String expressCompany;
	private String expressNum;
	private String shippingId;
	private String productId;
	private String orderNum;

	private boolean ismaijia;

	private int itemIndex;
	private LinearLayout ll_view;

	private static final String INT_productId = "int_productId";
	private static final String INT_orderNum = "int_orderNum";
	private static final String INT_itemIndex = "int_itemIndex";

	public static void startThisActivity(Fragment fragment, String productId,
			int index, String orderNum, boolean ismaijia) {
		Intent intent = new Intent(fragment.getActivity(),
				LogisticsInformationActivity.class);
		intent.putExtra(INT_productId, productId);
		intent.putExtra(INT_orderNum, orderNum);
		intent.putExtra(INT_itemIndex, index);
		intent.putExtra("ismaijia", ismaijia);
		fragment.startActivityForResult(intent, 1);
	}

	public static void startThisActivity(Activity mAc, String productId,
			int index, String orderNum,boolean ismaijia) {
		Intent intent = new Intent(mAc,
				LogisticsInformationActivity.class);
		intent.putExtra(INT_productId, productId);
		intent.putExtra(INT_orderNum, orderNum);
		intent.putExtra(INT_itemIndex, index);
		intent.putExtra("ismaijia", ismaijia);
		mAc.startActivityForResult(intent, 1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logisticsinformation);
		initData();
		initView();
		initlistener();
	}
	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_logisticsinformation));

		et_expressCompany = (EditText) findViewById(R.id.logiste_et_expressCompany);
		et_expressNum = (EditText) findViewById(R.id.logiste_et_expressNum);

		tv_quxiao = (TextView) findViewById(R.id.logisticsinfomation_tv2);
		tv_tijiao = (TextView) findViewById(R.id.logisticsinfomation_tv3);

		ll_view = (LinearLayout) findViewById(R.id.logisticsinfomation_linear);

		loadPopupWindowData();
		initPupowindow();
	}

	private void initData() {
		productId = getIntent().getStringExtra(INT_productId);
		orderNum = getIntent().getStringExtra(INT_orderNum);
		itemIndex = getIntent().getIntExtra(INT_itemIndex, Integer.MIN_VALUE);
		ismaijia = getIntent().getBooleanExtra("ismaijia", false);
		MyLogger.i(">>>>>>>>>退款订单传入数据productId："+productId+"orderNum:"+orderNum+"itemIndex:"+itemIndex);
		if (TextUtils.isEmpty(productId) || TextUtils.isEmpty(INT_orderNum)) {
			myToast("请求参数异常");
			finish();
		}
	}
	private void initlistener() {
		
		tv_tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				expressCompany = et_expressCompany.getText().toString();
				expressNum = et_expressNum.getText().toString();
				// 提交
				if (ismaijia == true) {
//					myToast("卖家发货");
					SellerOrderServer.toSellerSendOrder(productId + "", expressCompany, expressNum, shippingId, new CallBack() {
						@Override
						public void onSimpleSuccess(Object res) {
							MyLogger.i("提交物流信息数据："+res);
							myToast("发货成功");
							setResult(RESULT_OK,
									new Intent().putExtra("res", itemIndex));
							finish();
						}
						
						@Override
						public void onSimpleFailure(int code) {
							myToast("发货失败");
						}
					});
				}else {
					if (matchData()) {
						BuyerOrderServer.toBuyerReturnOrderExpress(productId,
								orderNum, expressCompany, expressNum, shippingId,new CallBack() {
							@Override
							public void onSimpleSuccess(Object res) {
								MyLogger.i("提交物流信息数据："+res);
								myToast("提交物流信息成功");
								setResult(RESULT_OK,
										new Intent().putExtra("res", itemIndex));
								finish();
							}
							@Override
							public void onSimpleFailure(int code) {
								myToast("提交物流信息失败");
							}
						});
					}

				}
			}
		});
		tv_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//取消
				finish();
			}
		});
	}

	/**
	 * 加载popupWindow显示信息
	 * 
	 */
	private void loadPopupWindowData() {
		new BuyerOrderServer().toBuyerGetExpress(new CallBack() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSimpleSuccess(Object res) {
				if (res != null) {
					expressInfos = (List<ExpressInfo>) res;
					if (expressInfos != null && expressInfos.size() > 0) {
						expresAdapter.upDateList(expressInfos);
					}
				}
			}

			@Override
			public void onSimpleFailure(int code) {
				myToast("获取快递信息失败");
			}
		});
	}

	/**
	 * 初始化pupowindow
	 * 
	 */
	private void initPupowindow() {
		popuView = getLayoutInflater().inflate(
				R.layout.activity_logisticsinformation_popu, null);

		lv_express = (ListView) popuView.findViewById(R.id.logistl_lv_info);
		expresAdapter = new ExpressPopuAdapter(mAc,
				R.layout.activity_logisticsinformation_popu_list, expressInfos);
		lv_express.setAdapter(expresAdapter);
		lv_express.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (expresAdapter != null) {
					expresAdapter.changeIndex(position);
					et_expressCompany.setText(expressInfos.get(position)
							.getName());
					shippingId = expressInfos.get(position)
							.getId()+"";
					MyLogger.i("shippingId："+shippingId);
					popuWindowHelper.dismiss();
				}
			}
		});
	}

	/**
	 * 弹出pupoWindow
	 * 
	 * @param v
	 */
	public void pullClick(View v) {
		if (expressInfos != null && expressInfos.size() > 0) {
			popuWindowHelper = new PopupWindowHelper(popuView,
					ll_view.getWidth() + CommonUtil.dip2px(mAc, 16), LayoutParams.WRAP_CONTENT, true);

			popuWindowHelper.showAsDropDown(et_expressCompany,
					CommonUtil.dip2px(mAc, 8), 0);
		} else {
			myToast("当前没有可选的快递信息");
		}
	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void backClick(View v) {
		finish();
	}

	/**
	 * 提交物流信息
	 * 
	 * @param v
	 */
	public void submitClick(View v) {

	}

	private boolean matchData() {
		
		if (TextUtils.isEmpty(expressCompany)) {
			myToast("请填写快递公司名称");
			return false;
		}
		if (TextUtils.isEmpty(expressNum)) {
			myToast("请填写快递运单号");
			return false;
		}
		return true;
	}
}
