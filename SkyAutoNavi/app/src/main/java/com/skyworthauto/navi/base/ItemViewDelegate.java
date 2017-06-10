package com.skyworthauto.navi.base;

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void bindData(ViewHolder holder, T t, int position);
}
