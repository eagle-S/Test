package com.skyworthauto.navi.fragment;


import android.view.View;
import android.widget.TextView;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base2.BaseAutoAdapter;
import com.skyworthauto.navi.base2.BaseViewHolder;
import com.skyworthauto.navi.views.NoScrollGridView;

class SearchMoreAdapter extends BaseAutoAdapter<SearchMoreItemData> {

    protected View.OnClickListener mHolderClickListener;

    public SearchMoreAdapter(View.OnClickListener listener) {
        mHolderClickListener = listener;
    }

    @Override
    public BaseViewHolder createHolder(int position) {
        return new SearchMoreViewHolder(R.layout.search_more_item, mHolderClickListener);
    }

    private static class SearchMoreViewHolder extends BaseViewHolder<SearchMoreItemData> {

        private TextView mTypeName;
        private NoScrollGridView mGridView;
        private SearchMoreChildAdapter mAdapter;

        public SearchMoreViewHolder(int layoutId, View.OnClickListener listener) {
            super(layoutId);
            mAdapter = new SearchMoreChildAdapter(listener);
        }

        @Override
        public void bindData(SearchMoreItemData data) {
            mTypeName.setText(data.getType());
            mAdapter.setAllData(data.getChildList());
            mGridView.setAdapter(mAdapter);
        }

        @Override
        public void initView(View v, View.OnClickListener listener) {
            mTypeName = (TextView) v.findViewById(R.id.auto_search_more_group_name);
            mGridView = (NoScrollGridView) v.findViewById(R.id.auto_search_more_child_gridview);
        }
    }
}
