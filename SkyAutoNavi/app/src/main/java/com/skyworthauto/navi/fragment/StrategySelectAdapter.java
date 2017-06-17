package com.skyworthauto.navi.fragment;


import android.content.Context;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseSingleTypeAdapter;
import com.skyworthauto.navi.base.ViewHolder;
import com.skyworthauto.navi.bean.StrategyStateBean;

public class StrategySelectAdapter extends BaseSingleTypeAdapter<StrategyStateBean> {
    public StrategySelectAdapter(Context context) {
        super(context, R.layout.strategy_item);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, StrategyStateBean item, int position) {
        viewHolder.setText(R.id.strategy_name, item.getName());
        viewHolder.setImageResource(R.id.strategy_statue,
                item.isOpen() ? R.drawable.auto_route_setting_check
                        : R.drawable.auto_route_setting_uncheck);
    }
}
