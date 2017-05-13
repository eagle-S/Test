package com.skyworthauto.navi.base2;

import android.view.View;
import android.view.ViewGroup;

import com.skyworthauto.navi.base2.IViewHolder;
import com.skyworthauto.navi.util.Utils;

public abstract class BaseViewHolder<T> implements IViewHolder<T> {
    protected int mLayoutId;

    public BaseViewHolder(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public View createView(ViewGroup parent) {
        View v = Utils.getInflater().inflate(mLayoutId, parent, false);
        initView(v);
        return v;
    }

    public abstract void initView(View v);

}
