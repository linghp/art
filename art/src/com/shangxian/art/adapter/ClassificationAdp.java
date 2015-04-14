package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.ClassificationModel;
import com.shangxian.art.bean.SubClassificationModel;
import com.shangxian.art.cache.Imageloader_homePager;
import com.shangxian.art.constant.Constant;
/**
 * 分类adapter
 * @author Administrator
 *
 */
public class ClassificationAdp extends EntityAdapter<ClassificationModel>{

	private boolean isshow = false;

	public ClassificationAdp(Activity mAc, int layoutId,
			List<ClassificationModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_classifiymain, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_classifiymain_title);
			holder.summary = (TextView) convertView.findViewById(R.id.item_classifiymain_summary);
			holder.img = (ImageView) convertView.findViewById(R.id.item_classifiymain_img);
			holder.spread = (ImageView) convertView.findViewById(R.id.item_classifiymain_spread);
			holder.sublist = (ListView) convertView.findViewById(R.id.item_classifiymain_list);

			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(dates.get(position).getName());
		holder.summary.setText(dates.get(position).getSubTitle());
		Imageloader_homePager.displayImage(Constant.BASEURL
				+ dates.get(position).getPhoto(),
				holder.img,
				new Handler(), null);
		holder.spread.setOnClickListener(new MyClickListener(holder, position) {
			
			@Override
			void click(com.shangxian.art.adapter.ClassificationAdp.ViewHolder h,
					View v, int p) {
				// TODO Auto-generated method stub
				if (!isshow) {
					holder.spread.setImageResource(R.drawable.next_level);
					holder.sublist.setVisibility(View.VISIBLE);
					System.out.println(">>>>>>已展开"+ dates.get(position).getName());
				}else {
					holder.spread.setImageResource(R.drawable.arrow);
					holder.sublist.setVisibility(View.GONE);
					System.out.println(">>>>>>关闭"+ dates.get(position).getName());
				}
				isshow = !isshow;
				
				
				/*holder.model = new ArrayList<SubClassificationModel>();
				for (int i = 0; i < 10; i++) {
					SubClassificationModel m = new SubClassificationModel();
					m.setSubtitle("二级分类"+(i+1));
					holder.model.add(m);
				}
				holder.adapter = new SubClassificationAdp(mAc, R.layout.subitem_classification, holder.model);
				holder.sublist.setAdapter(holder.adapter);*/
				
				notifyDataSetChanged();
			}
		}); 

		return convertView;
	}
	public static class ViewHolder{
		private ImageView spread;
		private TextView title,summary;
		private ImageView img;
		private ListView sublist;
		
		private List<SubClassificationModel> model;
		private SubClassificationAdp adapter;

	}
	abstract class MyClickListener implements View.OnClickListener {
		int position;
		ViewHolder holder;

		public MyClickListener(ViewHolder h, int p) {
			position = p;
			holder = h;
		}

		@Override
		public void onClick(View v) {
			click(holder, v, position);
		}

		abstract void click(ViewHolder h, View v, int p);

	}

}
