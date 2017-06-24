package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.skyworthauto.navi.AutoConfig;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.bean.StrategyStateBean;

public class StrategySettingFragment extends BaseFragment implements
        AdapterView.OnItemClickListener, View.OnClickListener {

    private StrategySelectAdapter mAdapter;
    private int mStrategyFlag;

    public static StrategySettingFragment newInstance() {
        return new StrategySettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auto_car_route_proference_setting, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mAdapter = new StrategySelectAdapter(getActivity());
        mAdapter.setAllData(AutoConfig.getNaviStrategyStates());

        ListView listView = findViewById(root, R.id.strategy_list);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(this);

        findViewById(root, R.id.btn_update_route_plan_id).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mStrategyFlag = AutoConfig.getStrategyFlag();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StrategyStateBean stateBean = mAdapter.getItem(position);

        stateBean.setOpen(!stateBean.isOpen());
        String strategyKey = stateBean.getKey();

        if (AutoConfig.KEY_HIGH_SPEED.equals(strategyKey)) {
            for (StrategyStateBean bean : mAdapter.getData()) {
                if (AutoConfig.KEY_AVOID_COST.equals(bean.getKey())
                        || AutoConfig.KEY_AVOID_HIGH_SPEED.equals(bean.getKey())) {
                    bean.setOpen(false);
                }
            }
        }

        if (AutoConfig.KEY_AVOID_COST.equals(strategyKey) || AutoConfig.KEY_AVOID_HIGH_SPEED
                .equals(strategyKey)) {
            for (StrategyStateBean bean : mAdapter.getData()) {
                if (AutoConfig.KEY_HIGH_SPEED.equals(bean.getKey())) {
                    bean.setOpen(false);
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_route_plan_id:
                saveStrategy();
                break;
            default:
                break;
        }
    }

    private void saveStrategy() {
        for (StrategyStateBean bean : mAdapter.getData()) {
            AutoConfig.getNaviPreference().putBoolean(bean.getKey(), bean.isOpen());
        }

        popBackStack();
        chechStrategyFlag();
    }

    private void chechStrategyFlag() {
        int StrategyFlag = AutoConfig.getStrategyFlag();
        if (StrategyFlag != mStrategyFlag) {
            RouteSelectFragment fragment = (RouteSelectFragment) getTargetFragment();
            fragment.onStrategyChanged();
        }
    }
}
