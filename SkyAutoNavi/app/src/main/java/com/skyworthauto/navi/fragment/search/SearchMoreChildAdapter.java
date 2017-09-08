package com.skyworthauto.navi.fragment.search;

import android.content.Context;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ItemViewDelegate;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.util.ResourceUtils;

public class SearchMoreChildAdapter extends MultiTypeAdapter<SearchMoreChildItemData> {

    public SearchMoreChildAdapter(Context contxt, MultiTypeAdapter.OnItemClickListener listener) {
        super(contxt);
        addItemViewDelegate(new SearchMoreChildTextItem());
        addItemViewDelegate(new SearchMoreChildPicItem());

        setOnItemClickListener(listener);
    }

    public static class SearchMoreChildTextItem implements
            ItemViewDelegate<SearchMoreChildItemData> {

        public SearchMoreChildTextItem() {

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
            holder.setText(R.id.auto_search_more_child_item_text, data.getName());
        }
    }


    public static class SearchMoreChildPicItem extends SearchMoreChildTextItem {

        public SearchMoreChildPicItem() {
            super();
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
