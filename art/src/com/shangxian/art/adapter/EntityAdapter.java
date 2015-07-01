package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 实体适配器
 * @create libo 2015/1/22
 * @finish ...
 */
public abstract class EntityAdapter<T> extends BaseAdapter{
    private LayoutInflater inflater;
    protected List<T> dates;
    protected int layoutId;
    protected Activity mAc;
	protected Fragment mFragment;
	protected ImageLoader imageLoader = ImageLoader.getInstance();;

    public EntityAdapter(Activity mAc, int layoutId, List<T> dates){
        this.mAc = mAc;
        this.layoutId = layoutId;
        this.dates = dates;
        inflater = LayoutInflater.from(mAc);
        upDateList(dates);
    }
    
    public EntityAdapter(Fragment fragment, int layoutId, List<T> dates){
    	this.mAc = fragment.getActivity();
    	this.mFragment = fragment;
    	this.layoutId = layoutId;
    	this.dates = dates;
    	inflater = LayoutInflater.from(mAc);
    	upDateList(dates);
    }

    protected View inflater(){
        if (inflater == null){
            return null;
        }
        return inflater.inflate(layoutId, null);
    }

    public void upDateList(List<T> dates) {
        if (dates!= null){
        	this.dates.clear();
        	this.dates.addAll(dates);
        }
        notifyDataSetChanged();
    }
    
//    public void addHeadDataList(List<T> datas){
//        if (datas == null || datas.size() == 0){
//            return;
//        }
//        if (dates == null){
//            dates = new ArrayList<T>();
//        }
//        if (dates.size() == 0){
//            dates.addAll(datas);
//        } else {
//            for (int i = datas.size() - 1; i >= 0; i++) {
//                if (!dates.contains(datas.get(i))){
//                    dates.add(0, datas.get(i));
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

    public void addFootDataList(List<T> datas){
        if (datas == null || datas.size() == 0){
            return;
        }
         this.dates.addAll(datas);
        notifyDataSetChanged();
    }
    
    /**
     * 移除datas里的单个数据 by data
     * 
     * @param data
     */
    public void removeDataItem(T data){
    	if (dates.contains(data)) {
			dates.remove(data);
			notifyDataSetChanged();
		}
    }
    
    /**
     * 移除datas里的单个数据，根据data下标
     * 
     * @param position
     */
    public void removeDataItem(int position){
    	if (position >= 0 && dates.size() > 0 && dates.size() > position) {
    		dates.remove(position);
			notifyDataSetChanged();
		}
    }

    protected void changData(int postition, T t){
        dates.remove(postition);
        dates.add(postition,t);
        notifyDataSetChanged();
    }

    public List<T> getDates() {
        return dates;
    }

    public void setDates(List<T> dates) {
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public T getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public abstract View initView(int position, View convertView, ViewGroup parent);
}
