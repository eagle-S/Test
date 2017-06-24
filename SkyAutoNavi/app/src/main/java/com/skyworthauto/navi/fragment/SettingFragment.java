package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyworthauto.navi.AutoConfig;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.setting_fragment, null);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        initTitle(root);
        initRouteSetting(root);
        initVoiceSetting(root);
        initDayNightMode(root);
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
        boolean isHighSpeed = AutoConfig.isHighSpeed();
        mUseHighSpeed.setSelected(isHighSpeed);
        mUseHighSpeedCheck.setVisibility(isHighSpeed ? View.VISIBLE : View.GONE);
    }

    private void updateAvoidHighSpeed() {
        boolean isAvoidHighSpeed = AutoConfig.isAvoidHighSpeed();
        mAvoidHighSpeed.setSelected(isAvoidHighSpeed);
        mAvoidHighSpeedCheck.setVisibility(isAvoidHighSpeed ? View.VISIBLE : View.GONE);
    }

    private void updateAvoidCost() {
        boolean isAvoidCost = AutoConfig.isAvoidCost();
        mAvoidCost.setSelected(isAvoidCost);
        mAvoidCostCheck.setVisibility(isAvoidCost ? View.VISIBLE : View.GONE);
    }

    private void updateAvidCongestion() {
        boolean isAvoidCongestion = AutoConfig.isAvoidCongestion();
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
        boolean isNaviDetailMode = AutoConfig.isNaviDetailMode();
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
        mAutoModeItem.setSelected(AutoConfig.isAutoMode());
        mAutoModeRadio.setVisibility(AutoConfig.isAutoMode() ? View.VISIBLE : View.GONE);
        mDayModeItem.setSelected(AutoConfig.isDayMode());
        mDayModeRadio.setVisibility(AutoConfig.isDayMode() ? View.VISIBLE : View.GONE);
        mNightModeItem.setSelected(AutoConfig.isNightMode());
        mNightModeRadio.setVisibility(AutoConfig.isNightMode() ? View.VISIBLE : View.GONE);

        mDayNightModeTip.setText(getDayNightModeTip());
    }

    private int getDayNightModeTip() {
        if (AutoConfig.isAutoMode()) {
            return R.string.auto_string_setting_model_daynight_auto_tip;
        }

        if (AutoConfig.isDayMode()) {
            return R.string.auto_string_setting_model_daynight_day_tip;
        }

        if (AutoConfig.isNightMode()) {
            return R.string.auto_string_setting_model_daynight_night_tip;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.auto_setting_avoid_jam_item:
                AutoConfig.setAvoidCongestion(!AutoConfig.isAvoidCongestion());
                updateAvidCongestion();
                break;
            case R.id.auto_setting_avoid_charge_item:
                AutoConfig.setAvoidCost(!AutoConfig.isAvoidCost());
                updateAvoidCost();
                updateHighSpeed();
                break;
            case R.id.auto_setting_avoid_highway_item:
                AutoConfig.setAvoidhighspeed(!AutoConfig.isAvoidHighSpeed());
                updateAvoidHighSpeed();
                updateHighSpeed();
                break;
            case R.id.auto_setting_using_highway_item:
                AutoConfig.setHighSpeed(!AutoConfig.isHighSpeed());
                updateHighSpeed();
                updateAvoidCost();
                updateAvoidHighSpeed();
                break;
            case R.id.auto_setting_navimode_detail_item:
                if (!AutoConfig.isNaviDetailMode()) {
                    AutoConfig.setNaviDetailMode(true);
                    updateDetailMode();
                }
                break;
            case R.id.auto_setting_navimode_simple_item:
                if (AutoConfig.isNaviDetailMode()) {
                    AutoConfig.setNaviDetailMode(false);
                    updateDetailMode();
                }
                break;
            case R.id.auto_setting_auto_model_item:
                AutoConfig.setDayNightMode(AutoConfig.MODE_AUTO);
                updateDayNightItems();
                break;
            case R.id.auto_setting_day_model_item:
                AutoConfig.setDayNightMode(AutoConfig.MODE_DAY);
                updateDayNightItems();
                mMapController.setDayMode();
                break;
            case R.id.auto_setting_night_model_item:
                AutoConfig.setDayNightMode(AutoConfig.MODE_NIGHT);
                updateDayNightItems();
                mMapController.setNightMode();
                break;
            default:
                break;
        }
    }
}
