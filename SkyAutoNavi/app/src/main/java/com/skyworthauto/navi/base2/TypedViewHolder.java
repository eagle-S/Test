package com.skyworthauto.navi.base2;

import android.view.View;
import android.view.ViewGroup;

import com.skyworthauto.navi.base2.IType;
import com.skyworthauto.navi.base2.IViewHolder;

public class TypedViewHolder implements IType, IViewHolder {

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public View createView(ViewGroup parent) {
        return null;
    }

    @Override
    public void bindData(Object data) {

    }
}
