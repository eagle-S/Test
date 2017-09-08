package com.skyworthauto.navi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.fragment.NormalDialogFragment;
import com.skyworthauto.navi.map.MapController;


public class SettingView extends LinearLayout implements View.OnClickListener {

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

    private MapController mMapController;
    private View mResetItem;
    private TextView mResetBtn;
    private BaseFragment mFragment;

    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.setting_content, this);
        initRouteSetting(rootView);
        initVoiceSetting(rootView);
        initDayNightMode(rootView);
        initResetPanel(rootView);
    }

    public void init(BaseFragment fragment, MapController mapController) {
        mFragment = fragment;
        mMapController = mapController;
    }

    public void setResetPanelVisiable(boolean visiable) {
        mResetItem.setVisibility(visiable ? VISIBLE : GONE);
    }

    private void initRouteSetting(View root) {
        mAvoidCongestion = root.findViewById(R.id.auto_setting_avoid_jam_item);
        mAvoidCongestion.setOnClickListener(this);
        mAvoidCongestionCheck = root.findViewById(R.id.auto_setting_avoid_jam_check);

        mAvoidCost = root.findViewById(R.id.auto_setting_avoid_charge_item);
        mAvoidCost.setOnClickListener(this);
        mAvoidCostCheck = root.findViewById(R.id.auto_setting_avoid_charge_check);

        mAvoidHighSpeed = root.findViewById(R.id.auto_setting_avoid_highway_item);
        mAvoidHighSpeed.setOnClickListener(this);
        mAvoidHighSpeedCheck = root.findViewById(R.id.auto_setting_avoid_highway_check);

        mUseHighSpeed = root.findViewById(R.id.auto_setting_using_highway_item);
        mUseHighSpeed.setOnClickListener(this);
        mUseHighSpeedCheck = root.findViewById(R.id.auto_setting_using_highway_check);

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
        mNaviModeDetals = root.findViewById(R.id.auto_setting_navimode_detail_item);
        mNaviModeDetals.setOnClickListener(this);
        mNaviModeDetalsRadio = root.findViewById(R.id.auto_setting_navimode_detals_radio);
        mNaviModeSimple = root.findViewById(R.id.auto_setting_navimode_simple_item);
        mNaviModeSimple.setOnClickListener(this);
        mNaviModeSimpleRadio = root.findViewById(R.id.auto_setting_navimode_simple_radio);

        mNaviModeTip = (TextView) root.findViewById(R.id.auto_setting_navimode_tip);

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
        mAutoModeItem = root.findViewById(R.id.auto_setting_auto_model_item);
        mAutoModeItem.setOnClickListener(this);
        mAutoModeRadio = root.findViewById(R.id.auto_setting_auto_model_radio);
        mDayModeItem = root.findViewById(R.id.auto_setting_day_model_item);
        mDayModeItem.setOnClickListener(this);
        mDayModeRadio = root.findViewById(R.id.auto_setting_day_model_radio);
        mNightModeItem = root.findViewById(R.id.auto_setting_night_model_item);
        mNightModeItem.setOnClickListener(this);
        mNightModeRadio = root.findViewById(R.id.auto_setting_night_model_radio);
        mDayNightModeTip = (TextView) root.findViewById(R.id.auto_setting_model_tip);

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

    private void initResetPanel(View rootView) {
        mResetItem = rootView.findViewById(R.id.auto_setting_reset_item);
        mResetBtn = (TextView) rootView.findViewById(R.id.auto_setting_reset_btn);
        mResetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                showResetDialog();
                break;
            default:
                break;
        }
    }

    private void showResetDialog() {
        NormalDialogFragment.newInstance().setMessage(R.string.conform_to_reset_default_setting)
                .show(mFragment, null);
    }
}
