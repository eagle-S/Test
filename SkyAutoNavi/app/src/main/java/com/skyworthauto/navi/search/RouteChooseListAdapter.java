package com.skyworthauto.navi.search;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseSingleTypeAdapter;
import com.skyworthauto.navi.base.ViewHolder;

public class RouteChooseListAdapter extends BaseSingleTypeAdapter<RoutePath> {
    private int mSelectedPos;

    public RouteChooseListAdapter() {
        super(R.layout.route_path_list_item, RoutePath.class);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, RoutePath item, int position) {
        viewHolder.setText(R.id.auto_panel_content_scheme_tag, item.mPathLabels);
        viewHolder.setText(R.id.auto_panel_content_time, item.mAllTimeTip);
        viewHolder.setText(R.id.auto_panel_content_distance, item.mAllLengthTip);
        viewHolder.setText(R.id.auto_panel_content_traffic, item.trafficLightNumberTip);

    }

    public void setSelectedPos(int position) {
        mSelectedPos = position;
    }

    public int getSelectedPos() {
        return mSelectedPos;
    }
}
