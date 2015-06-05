package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

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
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ClassityCommodiyAdp1;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.bean.ClassityCommdityResultModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.dialog.FilterDialog;
import com.shangxian.art.dialog.FilterDialog.Filter_I;
import com.shangxian.art.net.FilterServer;
import com.shangxian.art.net.FilterServer.OnHttpResultFilterListener;
import com.shangxian.art.net.FilterServer.OnHttpResultMoreListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 分类/首页---> 商品展示list
 * 
 * @author Administrator
 *
 */
public class ClassifyCommodityActivity extends BaseActivity implements
		OnHttpResultFilterListener,Filter_I,OnHeaderRefreshListener, OnFooterLoadListener,OnHttpResultMoreListener {
	// 列表
	private ListView list;
	private List<ClassityCommdityModel> model;
	private ClassityCommodiyAdp1 adapter;
	private AbHttpUtil httpUtil = null;
	// 底部选项
	TextView shaixuan, xiaoliang, jiage, xinpin;
	private String priceSort,dateSort, categoryId,lowprice,upprice;

	private String url="";
	private int skip = 0; // 从第skip+1条开始查询
	private final int pageSize = 10;
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
		topView.setCenterListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.gotoActivity(mAc, SearchsActivity.class, false);
			}
		});

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
		categoryId = getIntent().getStringExtra("id");
		MyLogger.i(categoryId);
		String geturl = getIntent().getStringExtra("url");
		if (TextUtils.isEmpty(geturl)) {
			url = Constant.BASEURL + Constant.CONTENT + "/" + categoryId + "/productlist";
		} else {
			url = Constant.BASEURL + Constant.CONTENT + geturl;
		}
		model = new ArrayList<ClassityCommdityModel>();
		refreshTask();

	}

	private void refreshTask() {
		// AbRequestParams params = new AbRequestParams();
		// params.put("shopid", "1019");
		// params.put("code", "88881110344801123456");
		// params.put("phone", "15889936624");
		httpUtil.post(url, new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				// AbDialogUtil.removeDialog(HomeActivity.this);
				 mAbPullToRefreshView.onHeaderRefreshFinish();
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
				// 请求
				AbLogUtil.i(ClassifyCommodityActivity.this, content);
				skip=0;
				model.clear();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							String str = jsonObject.getString("result");
							ClassityCommdityResultModel classityCommdityResultModel = gson
									.fromJson(str,
											ClassityCommdityResultModel.class);
							model = classityCommdityResultModel.getData();
							categoryId=model.get(0).getCategoryId()+"";
							if (model != null) {
								adapter = new ClassityCommodiyAdp1(
										ClassifyCommodityActivity.this,
										R.layout.item_classitycommodity, model);
								list.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	private void listener() {
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
		
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
				resetFooterButton();
				new FilterDialog(ClassifyCommodityActivity.this, ClassifyCommodityActivity.this).show();
			}
		});
		xiaoliang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!v.isFocused()) {
				resetFooterButton();
				v.setFocusable(true);
				v.setFocusableInTouchMode(true);
				v.requestFocus();
				}
			}
		});
		jiage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xiaoliang.setFocusable(false);
				xinpin.setFocusable(false);
				if (v.isFocused()) {
					priceSort = "desc";
					v.setFocusable(false);
					v.setSelected(true);
				} else if (v.isSelected()) {
					priceSort = "";
					v.setFocusable(false);
					v.setSelected(false);
				} else {
					priceSort = "asc";
					v.setFocusable(true);
					v.setFocusableInTouchMode(true);
					v.requestFocus();
				}
				AbRequestParams params = new AbRequestParams();
				params.put("priceSort", priceSort);
				initdata2(params);
			}
		});
		xinpin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!v.isFocused()) {
				    resetFooterButton();
					v.setFocusable(true);
					v.setFocusableInTouchMode(true);
					v.requestFocus();
					dateSort="desc";
					AbRequestParams params = new AbRequestParams();
					params.put("dateSort", dateSort);
					initdata2(params);
				}
			}
		});

		// title购物车跳转
		topView.setRightBtnListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putBoolean("isother", true);
				CommonUtil.gotoActivityWithDataForResult(
						ClassifyCommodityActivity.this,
						ShoppingcartActivity.class, bundle, 10086, false);
			}
		});
	}

	private void initdata2(AbRequestParams params) {
		skip=0;
		params.put("categoryId", categoryId);
		url=Constant.NET_FILTER;
		FilterServer.toPostFilter(Constant.NET_FILTER,params,
				ClassifyCommodityActivity.this);
	}
	
	private void resetFooterButton(){
		jiage.setSelected(false);
		jiage.setFocusable(false);
		xiaoliang.setFocusable(false);
		xinpin.setFocusable(false);
		priceSort="";
		dateSort="";
		lowprice="";
		upprice="";
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

	@Override
	public void onHttpResultFilter(
			ClassityCommdityResultModel classityCommdityResultModel) {
		 mAbPullToRefreshView.onHeaderRefreshFinish();
		if (classityCommdityResultModel != null&&adapter!=null) {
			skip=0;
			model.clear();
			model.addAll(classityCommdityResultModel.getData());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void getData(String lowprice, String upprice) {
		AbRequestParams params = new AbRequestParams();
		this.lowprice=lowprice+"00";
		this.upprice=upprice+"00";
		params.put("minPrice", this.lowprice);
		params.put("maxPrice", this.upprice);
		initdata2(params);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMore();
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		if(url.endsWith("find")){
			AbRequestParams params = new AbRequestParams();
			if(!TextUtils.isEmpty(priceSort)){
				params.put("priceSort", priceSort);	
			}else if(!TextUtils.isEmpty(dateSort)){
				params.put("dateSort", dateSort);
			}else if(!TextUtils.isEmpty(lowprice)){
				params.put("minPrice", this.lowprice);
				params.put("maxPrice", this.upprice);	
			}
			params.put("categoryId", categoryId);
			FilterServer.toPostFilter(Constant.NET_FILTER,params,
					ClassifyCommodityActivity.this);
		}else{
			refreshTask();
		}
	}

	private void loadMore() {
		skip += pageSize;
		if(url.endsWith("find")){
			AbRequestParams params = new AbRequestParams();
			if(!TextUtils.isEmpty(priceSort)){
				params.put("priceSort", priceSort);	
			}else if(!TextUtils.isEmpty(dateSort)){
				params.put("dateSort", dateSort);
			}else if(!TextUtils.isEmpty(lowprice)){
				params.put("minPrice", this.lowprice);
				params.put("maxPrice", this.upprice);	
			}
			params.put("categoryId", categoryId);
			params.put("pageSize", pageSize+"");
			params.put("skip", skip+"");
			FilterServer.toGetMore(Constant.NET_FILTER,params,
					ClassifyCommodityActivity.this);
		}else{
			FilterServer.toGetMore(url+"?pageSize="+pageSize+"&skip="+skip, null, this);
		}
		MyLogger.i(priceSort+"--"+dateSort+"--"+categoryId+"--"+pageSize+"--"+skip);
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

	@Override
	public void onHttpResultMore(
			ClassityCommdityResultModel classityCommdityResultModel) {
		MyLogger.i("onHttpResultMore");
		mAbPullToRefreshView.onFooterLoadFinish();
		if(classityCommdityResultModel!=null){
			List<ClassityCommdityModel> classityCommdityModels=classityCommdityResultModel.getData();
			if(classityCommdityModels!=null&&classityCommdityModels.size()>0){
			model.addAll(classityCommdityResultModel.getData());
			adapter.notifyDataSetChanged();
			}else{
				myToast("已到最后一页");
			}
		}else{
			skip -= pageSize;
		}
	}
}
