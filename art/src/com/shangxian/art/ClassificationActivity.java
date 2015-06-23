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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ClassificationAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 分类
 * @author Administrator
 *
 */
public class ClassificationActivity extends BaseActivity implements OnClickListener,OnHeaderRefreshListener{
	private ListView listView;
	private List<ClassificationModel>model;
	private ClassificationAdp adapter;
	private View ll_nonetwork,loading_big;

	private AbHttpUtil httpUtil = null;
	private LinearLayout ll_noData;

	boolean isother = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classification);
		initView();
		initData();
		listener();
	}

	//初始化控件
	private void initView() {
		Intent intent = getIntent();
		isother = intent.getBooleanExtra("isother", false);
		if (isother == true) {
			topView = (TopView) findViewById(R.id.top_title);
			topView.setActivity(this);
			topView.setVisibility(View.VISIBLE);
			topView.hideLeftBtnz();
			topView.hideTitle();
			topView.showRightBtn();
			topView.setCenterListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CommonUtil.gotoActivity(mAc, SearchsActivity.class, false);
				}
			});
			topView.setRightText("取消",new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}

		listView = (ListView) findViewById(R.id.classify);
		ll_nonetwork=findViewById(R.id.ll_nonetwork);
		loading_big=findViewById(R.id.loading_big);
		ll_noData = (LinearLayout) findViewById(R.id.ll_nodata);


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	//添加数据
	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		model = new ArrayList<ClassificationModel>();
		loading_big.setVisibility(View.VISIBLE);
		requestTask();
		//		for (int i = 0; i < 10; i++) {
		//			ClassificationModel m = new ClassificationModel();
		//			m.setTitle("特价美食" + 1+i);
		//			m.setSummary("特价美食，干果/仙贝" + 1+i);
		//			model.add(m);
		//		}

	}

	private void requestTask() {
		//		AbDialogUtil.showLoadDialog(this,
		//				R.drawable.progress_circular, "数据加载中...");
		//mAbPullToRefreshView.setVisibility(View.GONE);
		String url = Constant.BASEURL+Constant.CONTENT+Constant.CATEGORYS;
		AbRequestParams params = new AbRequestParams();
		params.put("level", "all");
		//		params.put("code", "88881110344801123456");
		//		params.put("phone", "15889936624");
		httpUtil.post(url,params,new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
				//loading_big.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish() {
				// AbDialogUtil.removeDialog(ClassificationActivity.this);
				mAbPullToRefreshView.onHeaderRefreshFinish();
				loading_big.setVisibility(View.GONE);
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				mAbPullToRefreshView.setVisibility(View.GONE);
				AbToastUtil.showToast(ClassificationActivity.this, error.getMessage());
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
				AbLogUtil.i(ClassificationActivity.this, content);
				model.clear();
				if (!TextUtils.isEmpty(content)) {
					Gson gson = new Gson();
					try {
						JSONObject jsonObject = new JSONObject(content);
						String result_code = jsonObject
								.getString("result_code");
						if (result_code.equals("200")) {
							mAbPullToRefreshView.setVisibility(View.VISIBLE);
							JSONArray resultObjectArray = jsonObject
									.getJSONArray("result");
							int length = resultObjectArray.length();
							for (int i = 0; i < length; i++) {
								JSONObject jo = resultObjectArray
										.getJSONObject(i);
								ClassificationModel classificationModel=gson.fromJson(
										jo.toString(), ClassificationModel.class);
								List<ClassificationModel> classificationModels=classificationModel.getProductCategoryDtos();
								for (ClassificationModel classificationModel2 : classificationModels) {//服务器返回level有误都为1，故在此修改为2
									classificationModel2.setLevel(2);
								}
								model.add(classificationModel);
							}
							MyLogger.i(model.toString());

							if (model.size() != 0) {
								ll_noData.setVisibility(View.GONE);
								adapter = new ClassificationAdp(ClassificationActivity.this, R.layout.item_classifiymain, model);
								listView.setAdapter(adapter);
							}else {
								ll_noData.setVisibility(View.VISIBLE);
							}
							//							adapter.notifyDataSetChanged();
						}else {
							ll_noData.setVisibility(View.VISIBLE);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	//事件监听
	private void listener(){
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setLoadMoreEnable(false);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ClassifyCommodityActivity.startThisActivity(model.get(position).getId()+"", ClassificationActivity.this);
			}
		});

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_reload:
			requestTask();
			break;
		default:
			break;
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		requestTask();
	}
}
