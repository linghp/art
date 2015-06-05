package com.shangxian.art.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangxian.art.R;
import com.shangxian.art.bean.DeliveryAddressModel;

public class DeliveryAddressAdapter extends EntityAdapter<DeliveryAddressModel>{

	public DeliveryAddressAdapter(Activity mAc, int layoutId,
			List<DeliveryAddressModel> dates) {
		super(mAc, layoutId, dates);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mAc).inflate(
					R.layout.item_deliveryaddress, null);
			holder.dingwei = (ImageView) convertView.findViewById(R.id.item_deliveryaddress_dingwei);
			holder.name = (TextView) convertView.findViewById(R.id.item_deliveryaddress_name);
			holder.num = (TextView) convertView.findViewById(R.id.item_deliveryaddress_num);
			holder.address = (TextView) convertView.findViewById(R.id.item_deliveryaddress_address);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText("收货人："+dates.get(position).getReceiverName());//收货人
		holder.num.setText(dates.get(position).getReceiverTel()+"");//电话
		holder.address.setText("收货地址："+dates.get(position).getDeliveryAddress());//收货地址
		
		holder.dingwei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println(">>>>>>>>>>>>>>>>>>定位");
				
			}
		});
		
		/*Imageloader_homePager.displayImage(Constant.BASEURL
				+ dates.get(position).getPhoto(),
				holder.img,
				new Handler(), null);*/
		return convertView;
	}

	public static class ViewHolder{
		TextView name,num,address;
		ImageView dingwei;
	}
}
