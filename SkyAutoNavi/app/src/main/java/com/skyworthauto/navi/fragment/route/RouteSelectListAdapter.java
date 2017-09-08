package com.skyworthauto.navi.fragment.route;

import android.content.Context;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.SingleTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;

public class RouteSelectListAdapter extends SingleTypeAdapter<RoutePath> {
    private int mSelectedPos;

    public RouteSelectListAdapter(Context context) {
        super(context, R.layout.route_path_list_item);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, RoutePath item, int position) {
//        int itemBg = android.R.color.white;
//        if (mSelectedPos == position) {
//            itemBg = R.color.search_result_list_item_gas_label_blue_color;
//        }
//        viewHolder.setBackgroundColor(R.id.auto_panel_content,
//                ResourceUtils.getResources().getColor(itemBg));
        viewHolder.setSelected(R.id.auto_panel_content, mSelectedPos == position);

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
