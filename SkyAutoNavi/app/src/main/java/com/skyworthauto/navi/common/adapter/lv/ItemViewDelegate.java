package com.skyworthauto.navi.common.adapter.lv;

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void bindData(ViewHolder holder, T data, int position);
}
