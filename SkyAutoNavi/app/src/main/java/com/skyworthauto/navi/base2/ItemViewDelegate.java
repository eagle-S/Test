package com.skyworthauto.navi.base2;

import com.skyworthauto.navi.base.ViewHolder;

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void bindData(ViewHolder holder, T t, int position);

    BaseViewHolder createHolder();
}
