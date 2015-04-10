package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.bean.ClassityCommdityModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.view.TopView;
/**
 * 分类--->  商品展示
 * @author Administrator
 *
 */
public class ClassifyCommodityActivity extends BaseActivity{
	//列表
	private ListView list;
	private List<ClassityCommdityModel> model;
	private ClassityCommodiyAdp adapter;
	private AbHttpUtil httpUtil = null;

	//底部选项
	TextView shaixuan,xiaoliang,jiage,xinpin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classitycommodity);
		initView();
		initData();
		listener();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		topView=(TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.setRightBtnDrawable(R.drawable.shopcart);//右侧按钮（购物车 白色）
		topView.setBack(R.drawable.back);//返回
		
		list = (ListView) findViewById(R.id.classitycommodity);
		//底部选项
		shaixuan = (TextView) findViewById(R.id.classitycommodity_saixuan);//筛选
		xiaoliang = (TextView) findViewById(R.id.classitycommodity_xiaoliang);//销量
		jiage = (TextView) findViewById(R.id.classitycommodity_jiage);//价格
		xinpin = (TextView) findViewById(R.id.classitycommodity_xinpin);//新品

	}
	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		String id=getIntent().getStringExtra("id");
		String url=Constant.BASEURL+Constant.CONTENT+"/"+id+"/products";
		model = new ArrayList<ClassityCommdityModel>();
        refreshTask(url);
		adapter = new ClassityCommodiyAdp(this, R.layout.item_classitycommodity, model);
		list.setAdapter(adapter);
	}
	
	private void refreshTask(String url) {
	//	AbRequestParams params = new AbRequestParams();
//		params.put("shopid", "1019");
//		params.put("code", "88881110344801123456");
//		params.put("phone", "15889936624");
		httpUtil.get(url,new AbStringHttpResponseListener() {

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
//				imgList.clear();
//				ArrayList<String> imgs = new ArrayList<String>();
//				imgs.add("http://img1.imgtn.bdimg.com/it/u=3784117098,1253514089&fm=21&gp=0.jpg");
//				mDatas.setImgList(imgs);
//				if (mDatas != null) {
//					if (mDatas.getImgList() != null
//							&& mDatas.getImgList().size() > 0) {
//						imgList.addAll(mDatas.getImgList());
//						// viewPager.setVisibility(View.VISIBLE);
//						viewPager.setOnGetView(new OnGetView() {
//
//							@Override
//							public View getView(ViewGroup container,
//									int position) {
//								ImageView iv = new ImageView(HomeActivity.this);
//								Imageloader_homePager.displayImage(
//										imgList.get(position), iv,
//										new Handler(), null);
//								container.addView(iv);
//								return iv;
//							}
//						});
//						viewPager.setAdapter(imgList.size());
//					}
//
//				}

			}

			@Override
			public void onSuccess(int statusCode, String content) {
				// AbToastUtil.showToast(HomeActivity.this, content);
				AbLogUtil.i(ClassifyCommodityActivity.this, content);
				model.clear();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							JSONArray resultObjectArray = jsonObject
									.getJSONArray("result");
							int length = resultObjectArray.length();
							for (int i = 0; i < length; i++) {
								JSONObject jo = resultObjectArray
										.getJSONObject(i);
								model.add(gson.fromJson(
										jo.toString(), ClassityCommdityModel.class));
							}
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				//CommonUtil.gotoActivity(ClassifyCommodityActivity.this, CommodityContentActivity.class, false);
				Intent intent=new Intent(ClassifyCommodityActivity.this, CommodityContentActivity.class);
				intent.putExtra("id", model.get(position).getId()+"");
				startActivity(intent);
			}
		});
		shaixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>筛选");
				
				
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
		
//		//购物车
//		shop.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				System.out.println(">>>>>>>加入购物车");
//			}
//		});
		//title购物车跳转
		topView.setRightBtnListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				CommonUtil.gotoActivityForResult(ClassifyCommodityActivity.this, ShoppingcartActivity.class, 10010, false);
			}
		});
	}
}
