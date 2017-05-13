package com.skyworthauto.navi.base;

public abstract class ItemViewDelegate<T> {

    public abstract int getItemLayoutId();

    public abstract Class<? extends T> getDataClass();

    public abstract void bindData(ViewHolder holder, T t, int position);
}
