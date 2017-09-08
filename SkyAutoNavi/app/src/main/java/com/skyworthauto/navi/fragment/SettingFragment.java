package com.skyworthauto.navi.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.offlinemap.OfflineMapFragment;
import com.skyworthauto.navi.util.Constant;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private View mAvoidCongestion;
    private View mAvoidCost;
    private View mAvoidHighSpeed;
    private View mUseHighSpeed;
    private View mAvoidCongestionCheck;
    private View mAvoidCostCheck;
    private View mAvoidHighSpeedCheck;
    private View mUseHighSpeedCheck;
    private View mNaviModeDetals;
    private View mNaviModeSimple;
    private View mNaviModeDetalsRadio;
    private View mNaviModeSimpleRadio;
    private View mAutoModeItem;
    private View mAutoModeRadio;
    private View mDayModeItem;
    private View mDayModeRadio;
    private View mNightModeItem;
    private View mNightModeRadio;
    private TextView mDayNightModeTip;
    private TextView mNaviModeTip;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_search_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initTitle(rootView);
        initOfflineMap(rootView);
        initRouteSetting(rootView);
        initVoiceSetting(rootView);
        initDayNightMode(rootView);
        initResetBtu(rootView);
    }

    private void initOfflineMap(View root) {
        findViewById(root, R.id.auto_setting_manage_offline_map).setOnClickListener(this);
    }

    private void initTitle(View root) {
        findViewById(root, R.id.auto_search_back_btn).setOnClickListener(this);
    }

    private void initRouteSetting(View root) {
        mAvoidCongestion = findViewById(root, R.id.auto_setting_avoid_jam_item);
        mAvoidCongestion.setOnClickListener(this);
        mAvoidCongestionCheck = findViewById(root, R.id.auto_setting_avoid_jam_check);

        mAvoidCost = findViewById(root, R.id.auto_setting_avoid_charge_item);
        mAvoidCost.setOnClickListener(this);
        mAvoidCostCheck = findViewById(root, R.id.auto_setting_avoid_charge_check);

        mAvoidHighSpeed = findViewById(root, R.id.auto_setting_avoid_highway_item);
        mAvoidHighSpeed.setOnClickListener(this);
        mAvoidHighSpeedCheck = findViewById(root, R.id.auto_setting_avoid_highway_check);

        mUseHighSpeed = findViewById(root, R.id.auto_setting_using_highway_item);
        mUseHighSpeed.setOnClickListener(this);
        mUseHighSpeedCheck = findViewById(root, R.id.auto_setting_using_highway_check);

        updateAvidCongestion();
        updateAvoidCost();
        updateAvoidHighSpeed();
        updateHighSpeed();
    }

    private void updateHighSpeed() {
        boolean isHighSpeed = NaviConfig.isHighSpeed();
        mUseHighSpeed.setSelected(isHighSpeed);
        mUseHighSpeedCheck.setVisibility(isHighSpeed ? View.VISIBLE : View.GONE);
    }

    private void updateAvoidHighSpeed() {
        boolean isAvoidHighSpeed = NaviConfig.isAvoidHighSpeed();
        mAvoidHighSpeed.setSelected(isAvoidHighSpeed);
        mAvoidHighSpeedCheck.setVisibility(isAvoidHighSpeed ? View.VISIBLE : View.GONE);
    }

    private void updateAvoidCost() {
        boolean isAvoidCost = NaviConfig.isAvoidCost();
        mAvoidCost.setSelected(isAvoidCost);
        mAvoidCostCheck.setVisibility(isAvoidCost ? View.VISIBLE : View.GONE);
    }

    private void updateAvidCongestion() {
        boolean isAvoidCongestion = NaviConfig.isAvoidCongestion();
        mAvoidCongestion.setSelected(isAvoidCongestion);
        mAvoidCongestionCheck.setVisibility(isAvoidCongestion ? View.VISIBLE : View.GONE);
    }

    private void initVoiceSetting(View root) {
        mNaviModeDetals = findViewById(root, R.id.auto_setting_navimode_detail_item);
        mNaviModeDetals.setOnClickListener(this);
        mNaviModeDetalsRadio = findViewById(root, R.id.auto_setting_navimode_detals_radio);
        mNaviModeSimple = findViewById(root, R.id.auto_setting_navimode_simple_item);
        mNaviModeSimple.setOnClickListener(this);
        mNaviModeSimpleRadio = findViewById(root, R.id.auto_setting_navimode_simple_radio);

        mNaviModeTip = findViewById(root, R.id.auto_setting_navimode_tip);

        updateDetailMode();
    }

    private void updateDetailMode() {
        boolean isNaviDetailMode = NaviConfig.isNaviDetailMode();
        mNaviModeDetals.setSelected(isNaviDetailMode);
        mNaviModeDetalsRadio.setVisibility(isNaviDetailMode ? View.VISIBLE : View.GONE);
        mNaviModeSimple.setSelected(!isNaviDetailMode);
        mNaviModeSimpleRadio.setVisibility(!isNaviDetailMode ? View.VISIBLE : View.GONE);

        mNaviModeTip.setText(isNaviDetailMode ? R.string.auto_string_setting_report_navi_detail_tip
                : R.string.auto_string_setting_report_navi_simple_tip);
    }

    private void initDayNightMode(View root) {
        mAutoModeItem = findViewById(root, R.id.auto_setting_auto_model_item);
        mAutoModeItem.setOnClickListener(this);
        mAutoModeRadio = findViewById(root, R.id.auto_setting_auto_model_radio);
        mDayModeItem = findViewById(root, R.id.auto_setting_day_model_item);
        mDayModeItem.setOnClickListener(this);
        mDayModeRadio = findViewById(root, R.id.auto_setting_day_model_radio);
        mNightModeItem = findViewById(root, R.id.auto_setting_night_model_item);
        mNightModeItem.setOnClickListener(this);
        mNightModeRadio = findViewById(root, R.id.auto_setting_night_model_radio);
        mDayNightModeTip = findViewById(root, R.id.auto_setting_model_tip);

        updateDayNightItems();
    }

    private void updateDayNightItems() {
        mAutoModeItem.setSelected(NaviConfig.isAutoMode());
        mAutoModeRadio.setVisibility(NaviConfig.isAutoMode() ? View.VISIBLE : View.GONE);
        mDayModeItem.setSelected(NaviConfig.isDayMode());
        mDayModeRadio.setVisibility(NaviConfig.isDayMode() ? View.VISIBLE : View.GONE);
        mNightModeItem.setSelected(NaviConfig.isNightMode());
        mNightModeRadio.setVisibility(NaviConfig.isNightMode() ? View.VISIBLE : View.GONE);

        mDayNightModeTip.setText(getDayNightModeTip());
    }

    private int getDayNightModeTip() {
        if (NaviConfig.isAutoMode()) {
            return R.string.auto_string_setting_model_daynight_auto_tip;
        }

        if (NaviConfig.isDayMode()) {
            return R.string.auto_string_setting_model_daynight_day_tip;
        }

        if (NaviConfig.isNightMode()) {
            return R.string.auto_string_setting_model_daynight_night_tip;
        }
        return 0;
    }

    private void initResetBtu(View rootView) {
        findViewById(rootView, R.id.auto_setting_reset_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.auto_setting_manage_offline_map:
                replaceFragmentToActivity(OfflineMapFragment.newInstance());
                break;
            case R.id.auto_setting_avoid_jam_item:
                NaviConfig.setAvoidCongestion(!NaviConfig.isAvoidCongestion());
                updateAvidCongestion();
                break;
            case R.id.auto_setting_avoid_charge_item:
                NaviConfig.setAvoidCost(!NaviConfig.isAvoidCost());
                updateAvoidCost();
                updateHighSpeed();
                break;
            case R.id.auto_setting_avoid_highway_item:
                NaviConfig.setAvoidhighspeed(!NaviConfig.isAvoidHighSpeed());
                updateAvoidHighSpeed();
                updateHighSpeed();
                break;
            case R.id.auto_setting_using_highway_item:
                NaviConfig.setHighSpeed(!NaviConfig.isHighSpeed());
                updateHighSpeed();
                updateAvoidCost();
                updateAvoidHighSpeed();
                break;
            case R.id.auto_setting_navimode_detail_item:
                if (!NaviConfig.isNaviDetailMode()) {
                    NaviConfig.setNaviDetailMode(true);
                    updateDetailMode();
                }
                break;
            case R.id.auto_setting_navimode_simple_item:
                if (NaviConfig.isNaviDetailMode()) {
                    NaviConfig.setNaviDetailMode(false);
                    updateDetailMode();
                }
                break;
            case R.id.auto_setting_auto_model_item:
                NaviConfig.setDayNightMode(NaviConfig.MODE_AUTO);
                updateDayNightItems();
                break;
            case R.id.auto_setting_day_model_item:
                NaviConfig.setDayNightMode(NaviConfig.MODE_DAY);
                updateDayNightItems();
                mMapController.setDayMode();
                break;
            case R.id.auto_setting_night_model_item:
                NaviConfig.setDayNightMode(NaviConfig.MODE_NIGHT);
                updateDayNightItems();
                mMapController.setNightMode();
                break;
            case R.id.auto_setting_reset_btn:
                showConfimDialog();
                break;
            default:
                break;
        }
    }

    private void showConfimDialog() {
        NormalDialogFragment.newInstance().setMessage(R.string.conform_to_reset_default_setting)
                .show(this, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE_FOR_ACK) {
            if (requestCode == Activity.RESULT_OK) {
                resetSettings();
            }
        }
    }

    private void resetSettings() {
        NaviConfig.reset();

        updateAvidCongestion();
        updateAvoidCost();
        updateAvoidHighSpeed();
        updateHighSpeed();
        updateDetailMode();
        updateDayNightItems();
    }
}
