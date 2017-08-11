package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.bean.StrategyStateBean;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;

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
        mAdapter.setAllData(NaviConfig.getNaviStrategyStates());
        mAdapter.setOnItemClickListener(new MultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                doItemClick(position);
            }
        });

        RecyclerView listView = findViewById(root, R.id.strategy_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(mAdapter);

        findViewById(root, R.id.btn_update_route_plan_id).setOnClickListener(this);
        findViewById(root, R.id.auto_route_preference_back_btn).setOnClickListener(this);
        findViewById(root, R.id.auto_route_preference_back_btn).requestFocus();
    }

    @Override
    public void onStart() {
        super.onStart();
        mStrategyFlag = NaviConfig.getStrategyFlag();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        doItemClick(position);
    }

    public void doItemClick(int position) {
        StrategyStateBean stateBean = mAdapter.getItem(position);

        stateBean.setOpen(!stateBean.isOpen());
        String strategyKey = stateBean.getKey();

        if (NaviConfig.KEY_HIGH_SPEED.equals(strategyKey)) {
            for (StrategyStateBean bean : mAdapter.getData()) {
                if (NaviConfig.KEY_AVOID_COST.equals(bean.getKey())
                        || NaviConfig.KEY_AVOID_HIGH_SPEED.equals(bean.getKey())) {
                    bean.setOpen(false);
                }
            }
        }

        if (NaviConfig.KEY_AVOID_COST.equals(strategyKey) || NaviConfig.KEY_AVOID_HIGH_SPEED
                .equals(strategyKey)) {
            for (StrategyStateBean bean : mAdapter.getData()) {
                if (NaviConfig.KEY_HIGH_SPEED.equals(bean.getKey())) {
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
            case R.id.auto_route_preference_back_btn:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    private void saveStrategy() {
        for (StrategyStateBean bean : mAdapter.getData()) {
            NaviConfig.getNaviPreference().putBoolean(bean.getKey(), bean.isOpen());
        }

        popBackStack();
        chechStrategyFlag();
    }

    private void chechStrategyFlag() {
        int StrategyFlag = NaviConfig.getStrategyFlag();
        if (StrategyFlag != mStrategyFlag) {
            RouteSelectFragment fragment = (RouteSelectFragment) getTargetFragment();
            fragment.onStrategyChanged();
        }
    }
}
