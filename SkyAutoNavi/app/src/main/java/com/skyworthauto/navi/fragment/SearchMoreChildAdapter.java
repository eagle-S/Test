package com.skyworthauto.navi.fragment;

import android.content.Context;
import android.view.View;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseMultiTypeAdapter;
import com.skyworthauto.navi.base.ItemViewDelegate;
import com.skyworthauto.navi.base.ViewHolder;
import com.skyworthauto.navi.util.ResourceUtils;

public class SearchMoreChildAdapter extends BaseMultiTypeAdapter<SearchMoreChildItemData> {

    public SearchMoreChildAdapter(Context contxt, View.OnClickListener listener) {
        super(contxt);
        addItemViewDelegate(new SearchMoreChildTextItem(listener));
        addItemViewDelegate(new SearchMoreChildPicItem(listener));
    }

    public static class SearchMoreChildTextItem implements
            ItemViewDelegate<SearchMoreChildItemData> {
        protected View.OnClickListener mClickListener;

        public SearchMoreChildTextItem(View.OnClickListener listener) {
            mClickListener = listener;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.search_more_child_text_item;
        }

        @Override
        public boolean isForViewType(SearchMoreChildItemData item, int position) {
            return !item.hasPic();
        }

        @Override
        public void bindData(ViewHolder holder, SearchMoreChildItemData data, int position) {
            holder.setData(data);
            holder.getConvertView().setOnClickListener(mClickListener);
            holder.setText(R.id.auto_search_more_child_item_text, data.getName());
        }
    }


    public static class SearchMoreChildPicItem extends SearchMoreChildTextItem {

        public SearchMoreChildPicItem(View.OnClickListener listener) {
            super(listener);
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.search_more_child_image_item;
        }

        @Override
        public boolean isForViewType(SearchMoreChildItemData item, int position) {
            return item.hasPic();
        }

        @Override
        public void bindData(ViewHolder holder, SearchMoreChildItemData data, int position) {
            super.bindData(holder, data, position);
            holder.setImageResource(R.id.auto_search_more_child_item_ic,
                    ResourceUtils.getDrawableId(data.getPicName()));
        }
    }


}
