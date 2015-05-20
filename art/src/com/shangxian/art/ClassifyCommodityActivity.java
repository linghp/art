package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ClassityCommodiyAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.ClassityCommdityResultModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;

/**
 * 分类---> 商品展示
 * 
 * @author Administrator
 *
 */
public class ClassifyCommodityActivity extends BaseActivity {
	// 列表
	private ListView list;
	private List<ClassityCommdityModel> model;
	private ClassityCommodiyAdp adapter;
	private AbHttpUtil httpUtil = null;

	// 底部选项
	TextView shaixuan, xiaoliang, jiage, xinpin;

	/*
	 * //筛选popupwindow private PopupWindow popupWindow; private View view;
	 * private TextView shopslist,classifylist;//商铺列表、分类列表
	 */
	// DialogScreen dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classitycommodity);
		initView();
		initData();
		listener();
		// initScreenPopupWindow();
		// initScreenDialog();
	}

	public static void startThisActivity(String id, Context context) {
		Intent intent = new Intent(context, ClassifyCommodityActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}

	public static void startThisActivity_url(String url, Context context) {
		Intent intent = new Intent(context, ClassifyCommodityActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}

	private void initView() {
		// TODO Auto-generated method stub
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.shopcart);// 右侧按钮（购物车 白色）
		topView.setBack(R.drawable.back);// 返回

		list = (ListView) findViewById(R.id.classitycommodity);
		// 底部选项
		shaixuan = (TextView) findViewById(R.id.classitycommodity_saixuan);// 筛选
		xiaoliang = (TextView) findViewById(R.id.classitycommodity_xiaoliang);// 销量
		jiage = (TextView) findViewById(R.id.classitycommodity_jiage);// 价格
		xinpin = (TextView) findViewById(R.id.classitycommodity_xinpin);// 新品

	}

	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id = getIntent().getStringExtra("id");
		String geturl = getIntent().getStringExtra("url");
		String url = "";
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/" + id + "/products";
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
		}
		model = new ArrayList<ClassityCommdityModel>();
		refreshTask(url);
		
	}

	private void refreshTask(String url) {
		// AbRequestParams params = new AbRequestParams();
		// params.put("shopid", "1019");
		// params.put("code", "88881110344801123456");
		// params.put("phone", "15889936624");
		httpUtil.get(url, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				// AbDialogUtil.removeDialog(HomeActivity.this);
				// mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				// AbToastUtil.showToast(HomeActivity.this, error.getMessage());
				// imgList.clear();
				// ArrayList<String> imgs = new ArrayList<String>();
				// imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
				// mDatas.setImgList(imgs);
				// if (mDatas != null) {
				// if (mDatas.getImgList() != null
				// && mDatas.getImgList().size() > 0) {
				// imgList.addAll(mDatas.getImgList());
				// // viewPager.setVisibility(View.VISIBLE);
				// viewPager.setOnGetView(new OnGetView() {
				//
				// @Override
				// public View getView(ViewGroup container,
				// int position) {
				// ImageView iv = new ImageView(HomeActivity.this);
				// Imageloader_homePager.displayImage(
				// imgList.get(position), iv,
				// new Handler(), null);
				// container.addView(iv);
				// return iv;
				// }
				// });
				// viewPager.setAdapter(imgList.size());
				// }
				//
				// }

			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// AbToastUtil.showToast(HomeActivity.this, content);
				//请求
				AbLogUtil.i(ClassifyCommodityActivity.this, content);
				model.clear();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							String str=jsonObject.getString("result");
							ClassityCommdityResultModel classityCommdityResultModel=gson.fromJson(str, ClassityCommdityResultModel.class);
							model = classityCommdityResultModel.getData();
							adapter = new ClassityCommodiyAdp(ClassifyCommodityActivity.this,
									R.layout.item_classitycommodity, model);
							list.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	private void listener() {
		// TODO Auto-generated method stub
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// CommonUtil.gotoActivity(ClassifyCommodityActivity.this,
				// CommodityContentActivity.class, false);
				// Intent intent=new Intent(ClassifyCommodityActivity.this,
				// CommodityContentActivity.class);
				// intent.putExtra("id", model.get(position).getId()+"");
				// startActivity(intent);

				CommodityContentActivity.startThisActivity(model.get(position)
						.getId() + "", ClassifyCommodityActivity.this);
			}
		});
		shaixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>筛选");
				// popupWindow.showAtLocation(v, MODE_APPEND, 0, 0);
				// dialog.show();
			}
		});
		xiaoliang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>销量");
			}
		});
		jiage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>价格");
			}
		});
		xinpin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>新品");
			}
		});

		// title购物车跳转
		topView.setRightBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("isother", true);
				CommonUtil.gotoActivityWithDataForResult(ClassifyCommodityActivity.this, ShoppingcartActivity.class, bundle, 10086, false);
			}
		});
	}

	// 筛选dialog
	private void initScreenDialog() {
		// TODO Auto-generated method stub
		// dialog = new Dialog(this, theme);//创建Dialog并设置样式主题
		// Window win = dialog.getWindow();
		// LayoutParams params = new LayoutParams();
		// params.x = -80;//设置x坐标
		// params.y = -60;//设置y坐标
		// win.setAttributes(params);
		// dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
		// dialog.show();
	}

	/*
	 * //筛选popupwindow private void initScreenPopupWindow() { // TODO
	 * Auto-generated method stub LayoutInflater inflater =
	 * LayoutInflater.from(this); //引入窗口配置文件 view =
	 * inflater.inflate(R.layout.screen_popupwindow, null); //创建popupwindow对象
	 * popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
	 * LayoutParams.WRAP_CONTENT,false); //
	 * popupWindow.setBackgroundDrawable(R.drawable.background);
	 * 
	 * // popupWindow.setHeight(500); //点击外边可消失
	 * popupWindow.setBackgroundDrawable(new BitmapDrawable()); //设置点击外边窗口消失
	 * popupWindow.setOutsideTouchable(true); //设置可获得焦点
	 * popupWindow.setFocusable(true); //popupwindow是否消失监听
	 * popupWindow.setOnDismissListener(new OnDismissListener() {
	 * 
	 * @Override public void onDismiss() { // TODO Auto-generated method stub
	 * 
	 * } }); //得到popupwindow里面的控件 shopslist = (TextView)
	 * view.findViewById(R.id.screen_shopslist); classifylist = (TextView)
	 * view.findViewById(R.id.screen_classifylist);
	 * shopslist.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub popupWindow.dismiss(); } }); classifylist.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub popupWindow.dismiss();
	 * 
	 * } }); }
	 */
}
