package com.shangxian.art.dialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.google.gson.Gson;
import com.shangxian.art.R;
import com.shangxian.art.bean.CommodityContentModel;
import com.shangxian.art.bean.ListCarGoodsBean;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;

public class GoodsDialog extends Dialog implements
		android.view.View.OnClickListener {
	private TextView tv_price;
	private TextView tv_option;
	private ImageView iv_icon;
	private LinearLayout ll_info;
	private LinearLayout ll_options_add;
	private TextView tv_sub;
	private Animation anim_in;
	private Animation anim_out;
	private ImageView iv_cha;

	private Button bt01, bt02;
	private EditText edt;
	private int num = 1;// 数量

	private Context context;

	private TextView tv_title;
	private AbImageLoader mAbImageLoader;

	// 商品详情模块
	private CommodityContentModel commodityContentModel;
	private GoodsDialogConfirmListener confirmListener;
	private GoodsDialogConfirmNowBuyListener confirmNowBuyListener;
	private String json;
	private boolean isallselected;// 商品详情时用于判断是否还有没有选择的规格属性
	private boolean isNowBuy;
	// 购物车模块
	private ListCarGoodsBean listCarGoodsBean;

	private Map<String, String> specSelectedStrs;// 已选的属性

	// private GoodsDialogConfirmListener confirmListener;
	// private String json;

	public interface GoodsDialogConfirmListener {
		void goodsDialogConfirm(String str);
	}

	public interface GoodsDialogConfirmNowBuyListener {
		void goodsDialogConfirmNowBuy(ListCarGoodsBean listCarGoodsBean);
	}
	
	// 商品详情加入购物车模块
	public GoodsDialog(Context context,
			GoodsDialogConfirmListener confirmListener) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.confirmListener = confirmListener;
		init();
	}

	// 商品详情立即购买模块
	public GoodsDialog(Context context,
			GoodsDialogConfirmNowBuyListener confirmListener, boolean isNowBuy) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.isNowBuy = isNowBuy;
		this.confirmNowBuyListener = confirmListener;
		init();
	}

	// 购物车模块
	public GoodsDialog(Context context,
			GoodsDialogConfirmListener confirmListener,
			ListCarGoodsBean listCarGoodsBean) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		this.listCarGoodsBean = listCarGoodsBean;
		this.confirmListener = confirmListener;
		init();
	}

	public GoodsDialog(Context context, int theme) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
		init();
	}

	private void init() {
		anim_in = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_bottom_in);
		anim_out = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_bottom_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shop_cart_updatagoods);
		setCanceledOnTouchOutside(true);
		mAbImageLoader = AbImageLoader.newInstance(getContext());
		mAbImageLoader.setLoadingImage(R.drawable.image_loading);
		mAbImageLoader.setErrorImage(R.drawable.image_error);
		mAbImageLoader.setEmptyImage(R.drawable.image_empty);
		initViews();
		initListener();
	}

	@Override
	public void show() {
		super.show();
		ll_info.startAnimation(anim_in);
	}

	@Override
	public void dismiss() {
		ll_info.startAnimation(anim_out);
		anim_out.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				GoodsDialog.super.dismiss();
			}
		});
	}

	private void initViews() {
		tv_title = (TextView) findViewById(R.id.goot_tv_title);
		tv_price = (TextView) findViewById(R.id.goot_tv_price);
		tv_option = (TextView) findViewById(R.id.goot_tv_option);
		tv_sub = (TextView) findViewById(R.id.goot_tv_sub);

		iv_icon = (ImageView) findViewById(R.id.gooi_iv_icon);
		iv_cha = (ImageView) findViewById(R.id.gooi_iv_cha);

		ll_info = (LinearLayout) findViewById(R.id.gool_ll_info);

		ll_options_add = (LinearLayout) findViewById(R.id.ll_options_add);

		bt01 = (Button) findViewById(R.id.addbt);
		bt02 = (Button) findViewById(R.id.subbt);
		edt = (EditText) findViewById(R.id.edt);
		bt01.setTag("-");
		bt02.setTag("+");

		initViews2();
	}

	private void initViews2() {
		// 购物车
		if (listCarGoodsBean != null) {
			tv_price.setText("¥"
					+ CommonUtil.priceConversion(listCarGoodsBean
							.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(listCarGoodsBean.getName());
			if (!TextUtils.isEmpty(listCarGoodsBean.getPhoto())) {
				String url = Constant.BASEURL + listCarGoodsBean.getPhoto();
				mAbImageLoader.display(iv_icon, url);
			}
			edt.setText((num = listCarGoodsBean.getQuantity()) + "");
			Map<String, List<String>> specMap = listCarGoodsBean.getSpecs();
			// 测试数据
			// Map<String, List<String>> specMap = new LinkedHashMap<String,
			// List<String>>();
			// specMap.putAll(listCarGoodsBean.getSpecs());
			// List<String> listtest = new ArrayList<String>();
			// listtest.add("三菜一汤");
			// listtest.add("四菜一汤");
			// listtest.add("五菜一汤");
			// specMap.put("套餐2", listtest);

			specSelectedStrs = new LinkedHashMap<String, String>();// 已选的属性
			if (listCarGoodsBean.getSelectedSpec() != null) {
				specSelectedStrs.putAll(listCarGoodsBean.getSelectedSpec());
			}
			for (Entry<String, List<String>> listCarGoodsBean2 : specMap
					.entrySet()) {
				String key = listCarGoodsBean2.getKey();
				View view = LayoutInflater.from(context).inflate(
						R.layout.commoditycontent_sepcs_item, null);
				((TextView) view.findViewById(R.id.textView1))
						.setText(listCarGoodsBean2.getKey());
				// 动态 添加布局
				ViewGroup vg_ad = (ViewGroup) view.findViewById(R.id.vg_add);
				List<String> specs = listCarGoodsBean2.getValue();
				for (String str : specs) {
					TextView textView = generateTextView(str);
					textView.setTag(key);
					if (str.equals(specSelectedStrs.get(key))) {
						textView.setSelected(true);
					}
					textView.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							ViewGroup vg = (ViewGroup) v.getParent();
							for (int i = 0; i < vg.getChildCount(); i++) {
								vg.getChildAt(i).setSelected(false);
							}
							v.setSelected(true);
							specSelectedStrs.put((String) v.getTag(),
									((TextView) v).getText().toString());
							tips();
						}
					});
					vg_ad.addView(textView);
				}
				ll_options_add.addView(view);
			}

			tips();
		} else if (commodityContentModel != null) {// 商品详情
			Map<String, List<String>> specMap = commodityContentModel
					.getSpecs();

			// 测试数据
			// Map<String, List<String>> specMap = new LinkedHashMap<String,
			// List<String>>();
			// if(commodityContentModel.getSpecs()!=null){
			// specMap.putAll(commodityContentModel.getSpecs());
			// }
			// List<String> listtest = new ArrayList<String>();
			// listtest.add("三菜一汤");
			// listtest.add("四菜一汤");
			// listtest.add("五菜一汤");
			// specMap.put("套餐2", listtest);

			tv_price.setText("¥  "
					+ CommonUtil.priceConversion(commodityContentModel
							.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(commodityContentModel.getName());
			if (commodityContentModel.getPhotos() != null
					&& commodityContentModel.getPhotos().size() > 0) {
				String url = Constant.BASEURL
						+ commodityContentModel.getPhotos().get(0);
				mAbImageLoader.display(iv_icon, url);
			}
			List<String> specStrs = new ArrayList<String>();// 属性的title集合
			specSelectedStrs = new LinkedHashMap<String, String>();// 已选的属性
			for (Entry<String, List<String>> listCarGoodsBean2 : specMap
					.entrySet()) {
				String key = listCarGoodsBean2.getKey();
				// 初始化已选的属性
				specSelectedStrs.put(key, "");
				View view = LayoutInflater.from(context).inflate(
						R.layout.commoditycontent_sepcs_item, null);
				((TextView) view.findViewById(R.id.textView1))
						.setText(listCarGoodsBean2.getKey());
				// 动态 添加布局
				ViewGroup vg_ad = (ViewGroup) view.findViewById(R.id.vg_add);
				List<String> specs = listCarGoodsBean2.getValue();
				if (specs.size() == 1) {
					specSelectedStrs.put(listCarGoodsBean2.getKey(),
							specs.get(0));
					TextView textView = generateTextView(specs.get(0));
					textView.setSelected(true);
					vg_ad.addView(textView);
				} else {
					specStrs.add(listCarGoodsBean2.getKey());
					for (String str : specs) {
						TextView textView = generateTextView(str);
						textView.setTag(key);
						textView.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								ViewGroup vg = (ViewGroup) v.getParent();
								for (int i = 0; i < vg.getChildCount(); i++) {
									vg.getChildAt(i).setSelected(false);
								}
								v.setSelected(true);
								specSelectedStrs.put((String) v.getTag(),
										((TextView) v).getText().toString());
								tips();
							}
						});
						vg_ad.addView(textView);
					}
				}
				ll_options_add.addView(view);
			}

			tips();
		}
	}

	private TextView generateTextView(String str) {
		TextView textView = new TextView(context);
		textView.setText(str);
		textView.setBackgroundResource(R.drawable.addshopcartdialog_shape_selector);
		textView.setTextColor(context.getResources().getColorStateList(
				R.color.graytoorange700));
		textView.setTextSize(14);
		return textView;
	}

	private void tips() {
		String speckey = "";
		String specStr = "";
		for (Entry<String, String> listCarGoodsBean2 : specSelectedStrs
				.entrySet()) {
			if (listCarGoodsBean2.getValue().equals("")) {
				speckey = speckey + listCarGoodsBean2.getKey() + "  ";
			}
			specStr = specStr + listCarGoodsBean2.getValue() + "  ";
		}
		if (speckey.equals("")) {
			tv_option.setText(specStr);
			isallselected = true;
		} else {
			tv_option.setText("请选择  " + speckey);
		}
	}

	private void initListener() {
		iv_cha.setOnClickListener(this);
		tv_sub.setOnClickListener(this);
		bt01.setOnClickListener(new OnButtonClickListener());
		bt02.setOnClickListener(new OnButtonClickListener());
		edt.addTextChangedListener(new OnTextChangeListener());
	}

	/**
	 * 加减按钮事件监听器
	 * 
	 *
	 */
	class OnButtonClickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			String numString = edt.getText().toString();
			if (numString == null || numString.equals("")) {
				num = 1;
				edt.setText("1");
			} else {
				if (v.getTag().equals("-")) {
					if ((num + 1) > 2) // 先加，再判断
					{
						num--;
						// Toast.makeText(context, "请输入一个大于0的数字",
						// Toast.LENGTH_SHORT).show();
						edt.setText(String.valueOf(num));
					}
				} else if (v.getTag().equals("+")) {
					num++;
					edt.setText(String.valueOf(num));
				}
			}
		}
	}

	/**
	 * EditText输入变化事件监听器
	 */
	class OnTextChangeListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			String numString = s.toString();
			if (numString == null || numString.equals("")) {
				num = 0;
			} else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 1) {
					Toast.makeText(context, "请输入一个大于0的数字", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 设置EditText光标位置 为文本末端
					edt.setSelection(edt.getText().toString().length());
					num = numInt;
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

	}

	@Override
	public void onClick(View v) {
		if (v == iv_cha) {
			dismiss();
		} else if (tv_sub == v) {
			if (num > 0) {
				// 购物车
				if (listCarGoodsBean != null) {
					listCarGoodsBean.setSelectedSpec(specSelectedStrs);
					listCarGoodsBean.setQuantity(num);
					confirmListener.goodsDialogConfirm("");
					dismiss();
				} else if(commodityContentModel!=null){// 商品详情
					if (isallselected) {
						if (isNowBuy) {//立即购买
							ListCarGoodsBean listCarGoodsBean=new ListCarGoodsBean();
							listCarGoodsBean.setQuantity(num);
							listCarGoodsBean.setSelectedSpec(specSelectedStrs);
							listCarGoodsBean.setProductId(commodityContentModel.getId());
							listCarGoodsBean.setPromotionPrice(commodityContentModel.getPromotionPrice());
							listCarGoodsBean.setOriginalPrice(commodityContentModel.getOriginalPrice());
							listCarGoodsBean.setName(commodityContentModel.getName());
							List<String> photos=commodityContentModel.getPhotos();
							if(photos!=null&&photos.size()>0){
							listCarGoodsBean.setPhoto(photos.get(0));
							}
							confirmNowBuyListener.goodsDialogConfirmNowBuy(listCarGoodsBean);
						} else {
							try {
								Gson gson = new Gson();
								// Map<String, String>
								// spec=commodityContentModel.getSpecs().
								String jsonsepcs = gson
										.toJson(specSelectedStrs);
								json = "{\"productId\":"
										+ commodityContentModel.getId()
										+ ",\"specs\":" + jsonsepcs
										+ ",\"buyCount\":" + num + "}";
								MyLogger.i(json);
								confirmListener.goodsDialogConfirm(json);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						dismiss();
					} else {
						CommonUtil.toast("请选择产品属性", context);
					}
				}
			} else {
				CommonUtil.toast("请输入产品数量", context);
			}
		}
	}

	public void setCommodityContent(CommodityContentModel commodityContentModel) {
		this.commodityContentModel = commodityContentModel;
	}
}
