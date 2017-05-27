package com.skyworthauto.navi.base;

public abstract class ItemViewDelegate<T> {
    private int mLayoutId;
    private Class<? extends T> mClazz;

    public ItemViewDelegate(int layoutId, Class<? extends T> clazz) {
        mLayoutId = layoutId;
        mClazz = clazz;
    }

    public int getItemLayoutId() {
        return mLayoutId;
    }

    public Class<? extends T> getDataClass() {
        return mClazz;
    }

    public abstract void bindData(ViewHolder holder, T t, int position);
}
