package com.skyworthauto.navi.base2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseAutoAdapter<T> extends BaseAdapter {
    protected ArrayList<T> mDataList = new ArrayList<T>();

    public void clearData() {
        synchronized (mDataList) {
            mDataList.clear();
        }
    }

    public void setAllData(List<T> data) {
        synchronized (mDataList) {
            mDataList.clear();
            addPageData(data);
        }
    }

    public void addPageData(List<T> data) {
        synchronized (mDataList) {
            mDataList.addAll(data);
        }
    }

    @Override
    public int getCount() {
        synchronized (mDataList) {
            return mDataList.size();
        }
    }

    @Override
    public T getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        synchronized (mDataList) {
            return mDataList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            holder = createHolder(position);
            convertView = holder.createView(parent);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        bindData(holder, getItem(position), position);
        return convertView;
    }

    protected void bindData(BaseViewHolder holder, T item, int position) {
        holder.bindData(item);
    }


    public abstract BaseViewHolder createHolder(int position);
}
