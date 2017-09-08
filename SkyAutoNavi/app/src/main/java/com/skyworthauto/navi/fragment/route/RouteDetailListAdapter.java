package com.skyworthauto.navi.fragment.route;

import android.content.Context;

import com.amap.api.navi.model.AMapNaviGuide;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.SingleTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.util.MapUtils;

class RouteDetailListAdapter extends SingleTypeAdapter<AMapNaviGuide> {

    private static final String TAG = "RouteDetailListAdapter";

    public RouteDetailListAdapter(Context context) {
        super(context, R.layout.car_detail_item_group);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, AMapNaviGuide item, int position) {
        viewHolder.setImageResource(R.id.group_icon, MapUtils.getDriveActionID(item.getIconType()));
        viewHolder.setText(R.id.section_name, item.getName());
        viewHolder.setText(R.id.group_des, MapUtils.getFriendlyDistance(item.getLength()));
    }

}
