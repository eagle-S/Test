package com.skyworthauto.navi.fragment.search;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.SingleTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;

public class SearchMoreAdapter extends SingleTypeAdapter<SearchMoreItemData> {

    protected MultiTypeAdapter.OnItemClickListener mHolderClickListener;

    public SearchMoreAdapter(Context context, MultiTypeAdapter.OnItemClickListener listener) {
        super(context, R.layout.search_more_item);
        mHolderClickListener = listener;
    }

    @Override
    protected void bindData(ViewHolder viewHolder, SearchMoreItemData item, int position) {
        viewHolder.setText(R.id.auto_search_more_group_name, item.getType());
        SearchMoreChildAdapter mAdapter =
                new SearchMoreChildAdapter(mContext, mHolderClickListener);
        mAdapter.setAllData(item.getChildList());
        RecyclerView recyclerView = viewHolder.getView(R.id.auto_search_more_child_gridview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        gridLayoutManager.setAutoMeasureEnabled(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
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
