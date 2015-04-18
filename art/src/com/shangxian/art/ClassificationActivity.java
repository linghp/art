package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;
import com.shangxian.art.adapter.ClassificationAdp;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.utils.MyLogger;

/**
 * 分类
 * @author Administrator
 *
 */
public class ClassificationActivity extends BaseActivity implements OnClickListener{
	private ListView list;
	private List<ClassificationModel>model;
	private ClassificationAdp adapter;
	
	private AbHttpUtil httpUtil = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classification);
		initView();
		initData();
		listener();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		topView = MainActivity.getTopView();
		topView.setActivity(this);
		topView.showLeftBtn();
		topView.showRightBtn();
		topView.showCenterSearch();
		topView.hideTitle();
		MainActivity activity = (MainActivity) getParent();
		topView.setLeftBtnListener(activity);
		topView.setRightBtnListener(activity);
		topView.setCenterListener(activity);
	}
	//添加数据
	private void initData() {
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(Constant.timeOut);
		model = new ArrayList<ClassificationModel>();
		requestTask();
//		for (int i = 0; i < 10; i++) {
//			ClassificationModel m = new ClassificationModel();
//			m.setTitle("特价美食" + 1+i);
//			m.setSummary("特价美食，干果/仙贝" + 1+i);
//			model.add(m);
//		}
		
		adapter = new ClassificationAdp(this, R.layout.item_classifiymain, model);
		list.setAdapter(adapter);

	}
	
	private void requestTask() {
		AbDialogUtil.showLoadDialog(this,
				R.drawable.progress_circular, "数据加载中...");
		String url = Constant.BASEURL+Constant.CONTENT+Constant.CATEGORYS;
		AbRequestParams params = new AbRequestParams();
		params.put("level", "all");
//		params.put("code", "88881110344801123456");
//		params.put("phone", "15889936624");
		httpUtil.post(url,params,new AbStringHttpResponseListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onFinish() {
				 AbDialogUtil.removeDialog(ClassificationActivity.this);
				// mAbPullToRefreshView.onHeaderRefreshFinish();
			}

			@Override
			public void onFailure(int statusCode, String content,
					Throwable error) {
				list.setVisibility(View.GONE);
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
							list.setVisibility(View.VISIBLE);
							JSONArray resultObjectArray = jsonObject
									.getJSONArray("result");
							int length = resultObjectArray.length();
							for (int i = 0; i < length; i++) {
								JSONObject jo = resultObjectArray
										.getJSONObject(i);
								model.add(gson.fromJson(
										jo.toString(), ClassificationModel.class));
							}
							MyLogger.i(model.toString());
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

	//初始化控件
	private void initView() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.classify);

	}

	//事件监听
	private void listener(){
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//				System.out.println(">>>>>>>>分类：点击了" + model.get(position).getTitle());
				//CommonUtil.gotoActivity(ClassificationActivity.this, ClassifyCommodityActivity.class, false);
//				Intent intent=new Intent(ClassificationActivity.this, ClassifyCommodityActivity.class);
//				intent.putExtra("id", model.get(position).getId()+"");
//				startActivity(intent);
				ClassifyCommodityActivity.startThisActivity(model.get(position).getId()+"", ClassificationActivity.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.classification, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
}
