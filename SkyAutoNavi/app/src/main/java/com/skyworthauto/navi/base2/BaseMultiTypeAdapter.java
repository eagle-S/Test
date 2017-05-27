package com.skyworthauto.navi.base2;


public class BaseMultiTypeAdapter extends BaseAutoAdapter<TypedData> {

    private int mTypeCount;
    private TypeFactory mTypeFactory;

    public BaseMultiTypeAdapter(int typeCount) {
        mTypeCount = typeCount;
    }

    @Override
    public int getViewTypeCount() {
        return mTypeFactory.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType(mTypeFactory);
    }

    @Override
    public BaseViewHolder createHolder(int position) {
        mTypeFactory.createViewHolder(getItemViewType(position));
        return null;
    }




}
