
package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.shangxian.art.adapter.DeliveryAddressAdapter;
import com.shangxian.art.adapter.IandEDetailsAdapter;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.bean.DeliveryAddressModel;
import com.shangxian.art.bean.IandEDetailsModel;
import com.shangxian.art.bean.IandEDetailsResultModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpClients;
import com.shangxian.art.net.HttpClients.HttpCilentListener;
import com.shangxian.art.utils.CommonUtil;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.view.TopView;

/**
 * 
 * 爱农谷--收支明细（Income and expenditure details）
 * @author zyz
 *
 */
public class IandEDetailsActivity extends BaseActivity{

	private ListView listview;

	private List<IandEDetailsModel>list;
	private IandEDetailsAdapter adapter;
	
	private IandEDetailsResultModel model;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iandedetails);
		initView();
		initData();
		initListener();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn();
		topView.hideCenterSearch();
		topView.showTitle();
		topView.setBack(R.drawable.back);
		topView.setTitle(getString(R.string.title_activity_iandedetails));

		listview = (ListView) findViewById(R.id.iandedetails_list);
	}

	private void initData() {
		list = new ArrayList<IandEDetailsModel>();
		String url = "";
		url = Constant.BASEURL + Constant.CONTENT + "/trade/history";
		refreshTask(url);
	}

	private void refreshTask(String url) {
		HttpClients.getDo(url, new HttpCilentListener() {
			
			@Override
			public void onResponse(String res) {
				
				list.clear();
				if (!TextUtils.isEmpty(res)) {
					Gson gson = new Gson();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(res);
						String result_code = jsonObject
								.getString("result_code");
						System.out.println(":::::::::::::::::result_code"+result_code);
						if (result_code.equals("200")) {
							String str=jsonObject.getString("result");
							System.out.println(":::::::::::::::::str"+str);
							
							IandEDetailsResultModel resultModel = gson.fromJson(str, IandEDetailsResultModel.class);
							
							list = resultModel.getData();
							String month = "";
							for (IandEDetailsModel iandEDetailsModel : list) {
								String str1 = iandEDetailsModel.getTransDate();
								String spStr[] = str1.split("-");
								iandEDetailsModel.setMonth(spStr[1]);
								if(!month.equals(spStr[1])){
									iandEDetailsModel.setIstitle(true);
								}
								month = spStr[1];
							}
							MyLogger.i("收支明细列表数据："+list);
							if (list != null) {
								adapter = new IandEDetailsAdapter(IandEDetailsActivity.this, R.layout.item_iandedetails, list);
								listview.setAdapter(adapter);
								adapter.notifyDataSetChanged();
							}
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});

	}

	private void initListener() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				IandEDetailsContentActivity.startThisActivity(list.get(position).getTradeTitle(),
						CommonUtil.priceConversion(list.get(position).getTotalPrice())+"",
						list.get(position).getTradeNumber(),
						list.get(position).getDirection(),
						list.get(position).getPayType(),
						list.get(position).getTransDate(),
						list.get(position).getMonth(),
						IandEDetailsActivity.this);
			}
		});
		

	}
}
