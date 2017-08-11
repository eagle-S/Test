package com.skyworthauto.navi.common.adapter.rv;


public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void bindData(ViewHolder holder, T data, int position);
}
