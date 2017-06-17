package com.skyworthauto.navi.base2;

import android.view.View;
import android.view.ViewGroup;

public interface IViewHolder<T> {

    public View createView(ViewGroup parent);

    public void bindData(T data);
}
