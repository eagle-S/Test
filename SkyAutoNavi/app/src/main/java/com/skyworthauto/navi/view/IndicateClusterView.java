package com.skyworthauto.navi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.focus.FocusLinearLayout;


public class IndicateClusterView extends FocusLinearLayout {

    private ImageView mWifiTip;
    private ImageView mGpsTip;
    private ImageView mVolumeTip;
    private TextView mCurrentTime;
    private ImageButton mHomeBar;


    public IndicateClusterView(Context context) {
        super(context);
    }

    public IndicateClusterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicateClusterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mGpsTip = (ImageView) findViewById(R.id.iv_tip_wifi);
        mWifiTip = (ImageView) findViewById(R.id.iv_tip_wifi);
        mVolumeTip = (ImageView) findViewById(R.id.iv_tip_volume);
        mCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mHomeBar = (ImageButton) findViewById(R.id.ib_status_bar_home);

        apply();
    }

    private void apply() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
