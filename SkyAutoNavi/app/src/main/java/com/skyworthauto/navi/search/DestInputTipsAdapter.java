package com.skyworthauto.navi.search;

import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseSingleTypeAdapter;
import com.skyworthauto.navi.base.ViewHolder;

public class DestInputTipsAdapter extends BaseSingleTypeAdapter<Tip> {

    public DestInputTipsAdapter() {
        super(R.layout.search_tip_listview_item, Tip.class);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, Tip item, int position) {
        viewHolder.setText(R.id.tip_name, item.getName());
    }

}
