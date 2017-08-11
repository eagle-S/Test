package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.ui.views.SettingView;

public class NaviSettingFragment extends BaseFragment implements View.OnClickListener {

    public static NaviSettingFragment newInstance() {
        return new NaviSettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.auto_navi_setting, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        findViewById(rootView, R.id.auto_setting_back).setOnClickListener(this);
        SettingView settingView = findViewById(rootView, R.id.setting_view);
        settingView.init(this, mMapController);
        settingView.setResetPanelVisiable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_setting_back:
                getFragmentManager().popBackStack();
                break;
            default:
                break;

        }
    }
}
