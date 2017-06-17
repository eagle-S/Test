package com.skyworthauto.navi.base2;

import android.view.View;
import android.view.ViewGroup;

import com.skyworthauto.navi.util.ResourceUtils;

public abstract class BaseViewHolder<T> implements IViewHolder<T> {
    protected int mLayoutId;

    public BaseViewHolder(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public View createView(ViewGroup parent) {
        return ResourceUtils.getInflater().inflate(mLayoutId, parent, false);
    }

    public abstract void initView(View v, View.OnClickListener clickListener);

}
