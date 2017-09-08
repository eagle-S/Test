package com.skyworthauto.navi.fragment.dest;

import android.content.Context;

import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.SingleTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.util.ResourceUtils;

public class DestResultAdapter extends SingleTypeAdapter<PoiItem> {
    private int mSelectedPos;

    public DestResultAdapter(Context context) {
        super(context, R.layout.auto_result_item_layout);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, PoiItem item, int position) {
        viewHolder.setSelected(R.id.poi_item_layout_rl, mSelectedPos == position);
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
