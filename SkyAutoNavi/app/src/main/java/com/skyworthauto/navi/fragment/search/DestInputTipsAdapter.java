package com.skyworthauto.navi.fragment.search;

import android.content.Context;

import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.SingleTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;

public class DestInputTipsAdapter extends SingleTypeAdapter<SearchHistoryData> {

    public DestInputTipsAdapter(Context context) {
        super(context, R.layout.search_tip_listview_item);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, SearchHistoryData item, int position) {
        if (item instanceof KeySearchHistoryData) {
            viewHolder.setText(R.id.text, (String) item.getData());
            viewHolder.setVisible(R.id.addr_info_layout, false);
            viewHolder.setImageResource(R.id.img_view, R.drawable.auto_search_list_type_history);
        } else if (item instanceof TipSearchHistoryData) {
            Tip tip = (Tip) item.getData();
            viewHolder.setText(R.id.text, tip.getName());
            viewHolder.setText(R.id.addr, tip.getDistrict() + tip.getAddress());
            viewHolder.setImageResource(R.id.img_view, R.drawable.auto_search_list_type_dest);
            viewHolder.setVisible(R.id.addr_info_layout, true);
        }
    }

}
