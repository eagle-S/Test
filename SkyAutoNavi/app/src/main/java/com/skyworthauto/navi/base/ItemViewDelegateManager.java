package com.skyworthauto.navi.base;

import android.support.v4.util.SparseArrayCompat;

public class ItemViewDelegateManager<T> {
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }

        int layout = delegate.getItemLayoutId();
        if (delegates.get(layout) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = " + layout
                            + ". Already registered ItemViewDelegate is " + delegates.get(layout));
        }

        delegates.put(layout, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.getDataClass().isInstance(item)) {
                return i;
            }
        }

        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position
                        + " in data source");
    }

    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.getDataClass().isInstance(item)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemLayoutId();
    }

    public void bindData(ViewHolder viewHolder, T item, int position) {
        ItemViewDelegate delegate = getItemViewDelegate(item, position);
        delegate.bindData(viewHolder, item, position);
    }
}
