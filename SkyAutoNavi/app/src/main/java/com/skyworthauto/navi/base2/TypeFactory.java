package com.skyworthauto.navi.base2;


public interface TypeFactory {

    int type(TypedData typedData);

    int getViewTypeCount();

    BaseViewHolder createViewHolder(int type);
}
