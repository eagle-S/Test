package com.skyworthauto.navi.base;

public abstract class BaseSingleTypeAdapter<T> extends BaseMultiTypeAdapter<T> {

    public BaseSingleTypeAdapter(final int layoutId, final Class clazz) {
        super();

        addItemViewDelegate(new ItemViewDelegate<T>(layoutId, clazz) {

            @Override
            public void bindData(ViewHolder holder, T item, int position) {
                BaseSingleTypeAdapter.this.bindData(holder, item, position);
            }
        });
    }

    protected abstract void bindData(ViewHolder viewHolder, T item, int position);


}