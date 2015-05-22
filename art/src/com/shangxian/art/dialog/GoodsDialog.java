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
import com.shangxian.art.MainActivity;
import com.shangxian.art.R;
import com.shangxian.art.bean.CarItem;
import com.shangxian.art.bean.CommodityContentModel;
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

	private CarItem item;
	private TextView tv_title;
	private AbImageLoader mAbImageLoader;

	// 商品详情模块
	private CommodityContentModel commodityContentModel;
	private GoodsDialogConfirmListener confirmListener;
	private String json;

	public interface GoodsDialogConfirmListener {
		void goodsDialogConfirm(String str);
	}

	public GoodsDialog(Context context,
			GoodsDialogConfirmListener confirmListener) {
		super(context, android.R.style.Theme_Translucent);
		this.context = context;
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
		bt01.setTag("+");
		bt02.setTag("-");

		upDataView();
	}

	private void upDataView() {
		if (item != null) {
			tv_price.setText("¥"
					+ CommonUtil.priceConversion(item.listCarGoodsBean
							.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(item.listCarGoodsBean.getName());
			String url = Constant.BASEURL + item.listCarGoodsBean.getPhoto();
			mAbImageLoader.display(iv_icon, url);
		} else if (commodityContentModel != null) {
			Map<String, List<String>> specMap = new LinkedHashMap<String, List<String>>();
			specMap.putAll(commodityContentModel.getSpecs());
			// Map<String, List<String>> specMap = commodityContentModel
			// .getSpecs();

			// 测试数据
			List<String> listtest = new ArrayList<String>();
			listtest.add("三菜一汤");
			listtest.add("四菜一汤");
			listtest.add("五菜一汤");
			specMap.put("套餐2", listtest);

			tv_price.setText("¥  "
					+ CommonUtil.priceConversion(commodityContentModel
							.getPromotionPrice()));
			// tv_option.setText(item.listCarGoodsBean.getSpecs());
			tv_title.setText(commodityContentModel.getName());
			String url = Constant.BASEURL
					+ commodityContentModel.getPhotos().get(0);
			mAbImageLoader.display(iv_icon, url);
			List<String> specStrs = new ArrayList<String>();
			for (Entry<String, List<String>> listCarGoodsBean2 : specMap
					.entrySet()) {
				specStrs.add(listCarGoodsBean2.getKey());
				// selectedSpec=listCarGoodsBean2.getValue();
				View view = LayoutInflater.from(context).inflate(
						R.layout.commoditycontent_sepcs_item, null);
				((TextView) view.findViewById(R.id.textView1))
						.setText(listCarGoodsBean2.getKey());
				// 添加属性值
				ViewGroup vg_ad = (ViewGroup) view.findViewById(R.id.vg_add);
				List<String> listvalues = listCarGoodsBean2.getValue();
				if (listvalues.size() == 1) {
					TextView textView = new TextView(context);
					textView.setText(listvalues.get(0));
					textView.setBackgroundResource(R.drawable.addshopcartdialog_shape_selector);
					textView.setSelected(true);
					textView.setTextSize(14);
					vg_ad.addView(textView);
				} else {
					for (String str : listvalues) {
						TextView textView = new TextView(context);
						textView.setText(str);
						textView.setBackgroundResource(R.drawable.addshopcartdialog_shape_selector);
						// textView.setTag("");
						textView.setTextSize(14);
						vg_ad.addView(textView);
					}
				}

				ll_options_add.addView(view);
			}
			String specStr = "";
			for (String str : specStrs) {
				specStr = specStr + str + "  ";
			}
			tv_option.setText("请选择  " + specStr);
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
					if (++num < 1) // 先加，再判断
					{
						num--;
//						Toast.makeText(context, "请输入一个大于0的数字",
//								Toast.LENGTH_SHORT).show();
					} else {
						edt.setText(String.valueOf(num));
					}
				} else if (v.getTag().equals("+")) {
					if (--num < 1) // 先减，再判断
					{
						num++;
//						Toast.makeText(context, "请输入一个大于0的数字",
//								Toast.LENGTH_SHORT).show();
					} else {
						edt.setText(String.valueOf(num));
					}
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
				num = 1;
			} else {
				int numInt = Integer.parseInt(numString);
				if (numInt < 1) {
					 Toast.makeText(context, "请输入一个大于0的数字",
					 Toast.LENGTH_SHORT)
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
			try {
				Gson gson = new Gson();
				// Map<String, String> spec=commodityContentModel.getSpecs().
				String jsonsepcs = gson
						.toJson(commodityContentModel.getSpecs());
				// 服务器有问题 暂时改成这种格式
				jsonsepcs = jsonsepcs.replace("[", "");
				jsonsepcs = jsonsepcs.replace("]", "");
				json = "{\"productId\":" + commodityContentModel.getId()
						+ ",\"sepcs\":" + jsonsepcs + ",\"buyCount\":" + num
						+ "}";
				MyLogger.i(json);
				confirmListener.goodsDialogConfirm(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dismiss();
		}
	}

	public void setCarGoodsItem(CarItem item) {
		this.item = item;
	}

	public void setCommodityContent(CommodityContentModel commodityContentModel) {
		this.commodityContentModel = commodityContentModel;
	}
}
