package com.skyworthauto.navi.fragment;


import android.content.Context;
import android.view.View;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseSingleTypeAdapter;
import com.skyworthauto.navi.base.ViewHolder;
import com.skyworthauto.navi.views.NoScrollGridView;

public class SearchMoreAdapter extends BaseSingleTypeAdapter<SearchMoreItemData> {

    protected View.OnClickListener mHolderClickListener;

    public SearchMoreAdapter(Context context, View.OnClickListener listener) {
        super(context, R.layout.search_more_item);
        mHolderClickListener = listener;
    }

    @Override
    protected void bindData(ViewHolder viewHolder, SearchMoreItemData item, int position) {
        viewHolder.setText(R.id.auto_search_more_group_name, item.getType());
        SearchMoreChildAdapter mAdapter =
                new SearchMoreChildAdapter(mContext, mHolderClickListener);
        mAdapter.setAllData(item.getChildList());
        ((NoScrollGridView) viewHolder.getView(R.id.auto_search_more_child_gridview))
                .setAdapter(mAdapter);
    }

    //    private static class SearchMoreViewHolder extends BaseViewHolder<SearchMoreItemData> {
    //
    //        private TextView mTypeName;
    //        private NoScrollGridView mGridView;
    //        private SearchMoreChildAdapter mAdapter;
    //
    //        public SearchMoreViewHolder(int layoutId, View.OnClickListener listener) {
    //            super(layoutId);
    //            mAdapter = new SearchMoreChildAdapter(listener);
    //        }
    //
    //        @Override
    //        public void bindData(SearchMoreItemData data) {
    //            mTypeName.setText(data.getType());
    //            mAdapter.setAllData(data.getChildList());
    //            mGridView.setAdapter(mAdapter);
    //        }
    //
    //        @Override
    //        public void initView(View v, View.OnClickListener listener) {
    //            mTypeName = (TextView) v.findViewById(R.id.auto_search_more_group_name);
    //            mGridView = (NoScrollGridView) v.findViewById(R.id.auto_search_more_child_gridview);
    //        }
    //    }
}
