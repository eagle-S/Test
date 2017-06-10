package com.skyworthauto.navi.base;

import android.content.Context;

public abstract class BaseSingleTypeAdapter<T> extends BaseMultiTypeAdapter<T> {

    public BaseSingleTypeAdapter(Context context, final int layoutId) {
        super(context);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void bindData(ViewHolder holder, T t, int position) {
                BaseSingleTypeAdapter.this.bindData(holder, t, position);
            }
        });
    }

    protected abstract void bindData(ViewHolder viewHolder, T item, int position);


}
