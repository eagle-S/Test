package com.skyworthauto.navi.base2;


import android.content.Context;


public class BaseMultiTypeAdapter<T> extends BaseAutoAdapter<T> {

    private ItemViewDelegateManager mItemViewDelegateManager;

    public BaseMultiTypeAdapter(Context context) {
        super(context);
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public BaseMultiTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewType(mDataList.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder createHolder(int position) {
        ItemViewDelegate itemViewDelegate =
                mItemViewDelegateManager.getItemViewDelegate(mDataList.get(position), position);

        return itemViewDelegate.createHolder();
    }

}
