package com.skyworthauto.navi.fragment.navi;

import android.os.Bundle;
import android.view.View;

import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.view.SettingView;

public class NaviSettingFragment extends BaseFragment implements View.OnClickListener {

    public static NaviSettingFragment newInstance() {
        return new NaviSettingFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.auto_navi_setting;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_setting_back;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
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
