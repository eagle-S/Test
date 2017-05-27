package com.skyworthauto.navi.fragment;

import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseSingleTypeAdapter;
import com.skyworthauto.navi.base.ViewHolder;
import com.skyworthauto.navi.util.ResourceUtils;

public class DestResultAdapter extends BaseSingleTypeAdapter<PoiItem> {
    private int mSelectedPos;

    public DestResultAdapter() {
        super(R.layout.auto_result_item_layout, PoiItem.class);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, PoiItem item, int position) {

        int itemBg = android.R.color.white;
        if (mSelectedPos == position) {
            itemBg = R.color.search_result_list_item_gas_label_blue_color;
        }

        viewHolder
                .setBackgroundColor(R.id.root_layout_parent, ResourceUtils.getResources().getColor(itemBg));
        
        viewHolder.setText(R.id.poi_name, String.valueOf(position + 1) + "." + item.getTitle());
        viewHolder.setText(R.id.distance, String.valueOf(item.getDistance()));
        viewHolder.setText(R.id.poi_addr, item.getSnippet());
    }

    public void setSelectedPos(int position) {
        mSelectedPos = position;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }
}
