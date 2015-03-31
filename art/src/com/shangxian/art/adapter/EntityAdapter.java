package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

    public EntityAdapter(Activity mAc, int layoutId, List<T> dates){
        this.mAc = mAc;
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
        if (dates == null){
            dates = new ArrayList<T>();
        }
        this.dates = dates;
        notifyDataSetChanged();
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
