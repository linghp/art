package com.shangxian.art.adapter;

import java.util.ArrayList;
import java.util.List;

import com.shangxian.art.base.MyApplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterBase<T> extends BaseAdapter {
	private final List<T> mList = new ArrayList<T>();
	public MyApplication mApplication = MyApplication.getInstance();

	public List<T> getListData() {
		return mList;
	}
	
	/**
	 * �������ݣ�������֮ǰ������
	 * @param newData
	 */
	public void updateData(List<T> newData){
		mList.clear();
		mList.addAll(newData);
		notifyDataSetChanged();
	}

	/**
	 * �����ݲ����б�β��������
	 * 
	 * @param list
	 */
	public void appendToList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}

	/**
	 * �����ݲ����б�ͷ��������
	 * 
	 * @param list
	 */
	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(0, list);
		notifyDataSetChanged();
	}

	/**
	 * ������ݲ�����
	 */
	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position > mList.size() - 1) {
			return null;
		}
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (position == getCount() - 1) {
			onReachBottom();
		}
		return getExView(position, convertView, parent);
	}

	protected abstract View getExView(int position, View convertView,
			ViewGroup parent);

	/**
	 * �������ײ����¼�
	 */
	protected abstract void onReachBottom();

}
