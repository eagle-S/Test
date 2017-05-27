package com.skyworthauto.navi.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base2.BaseAutoAdapter;
import com.skyworthauto.navi.base2.BaseViewHolder;
import com.skyworthauto.navi.util.ResourceUtils;

public class SearchMoreChildAdapter extends BaseAutoAdapter<SearchMoreChildItemData> {

    protected View.OnClickListener mHolderClickListener;


    public SearchMoreChildAdapter(View.OnClickListener listener) {
        mHolderClickListener = listener;
    }

    @Override
    protected View.OnClickListener getClickListener() {
        return mHolderClickListener;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        SearchMoreChildItemData data = getItem(position);
        if (data.hasPic()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public BaseViewHolder createHolder(int position) {
        SearchMoreChildItemData data = getItem(position);
        if (data.hasPic()) {
            return new PicViewHolder(R.layout.search_more_child_image_item);
        } else {
            return new SearchMoreChildItemViewHolder(R.layout.search_more_child_text_item);
        }
    }

    public static class SearchMoreChildItemViewHolder extends
            BaseViewHolder<SearchMoreChildItemData> {
        protected TextView mName;
        protected SearchMoreChildItemData mItemData;

        public SearchMoreChildItemViewHolder(int layoutId) {
            super(layoutId);
        }

        @Override
        public void bindData(SearchMoreChildItemData data) {
            mItemData = data;
            mName.setText(data.getName());
        }

        @Override
        public void initView(View v, View.OnClickListener listener) {
            mName = (TextView) v.findViewById(R.id.auto_search_more_child_item_text);
            mName.setOnClickListener(listener);
        }

        public SearchMoreChildItemData getChildItemData() {
            return mItemData;
        }
    }


    public static class PicViewHolder extends SearchMoreChildItemViewHolder {
        private static final String TAG = "PicViewHolder";
        private ImageView mImageView;
        private View mContentView;

        public PicViewHolder(int layoutId) {
            super(layoutId);
        }

        @Override
        public void bindData(SearchMoreChildItemData data) {
            mItemData = data;
            mName.setText(data.getName());
            mImageView.setImageResource(ResourceUtils.getDrawableId(data.getPicName()));
        }

        @Override
        public void initView(View v, View.OnClickListener listener) {
            mContentView = v;
            mContentView.setOnClickListener(listener);

            mName = (TextView) v.findViewById(R.id.auto_search_more_child_item_text);
            mImageView = (ImageView) v.findViewById(R.id.auto_search_more_child_item_ic);
        }
    }


}
