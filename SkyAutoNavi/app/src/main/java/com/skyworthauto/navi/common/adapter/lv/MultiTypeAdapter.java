package com.skyworthauto.navi.common.adapter.lv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected final List<T> mDataList = new ArrayList<T>();

    public MultiTypeAdapter(Context context) {
        mContext = context;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mItemViewDelegateManager = new ItemViewDelegateManager();

        if (datas != null) {
            mDataList.clear();
            mDataList.addAll(datas);
        }

    }

    public MultiTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public void clearData() {
        synchronized (mDataList) {
            mDataList.clear();
        }
    }

    public void setAllData(List<T> data) {
        synchronized (mDataList) {
            mDataList.clear();
            addData(data);
        }
    }

    public void addData(List<T> data) {
        synchronized (mDataList) {
            mDataList.addAll(data);
        }
    }

    public List<T> getData() {
        return mDataList;
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
    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(getItem(position), position);
            return viewType;
        }

        return super.getItemViewType(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            int layoutId =
                    mItemViewDelegateManager.getItemViewLayoutId(getItem(position), position);
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }

        bindData(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    protected void bindData(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.bindData(viewHolder, item, position);
    }

    private void onViewHolderCreated(ViewHolder viewHolder, View convertView) {

    }
}
