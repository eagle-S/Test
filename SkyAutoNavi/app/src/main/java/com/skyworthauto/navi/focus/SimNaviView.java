package com.skyworthauto.navi.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class SimNaviView extends FocusRelativeLayout {
    private View mExitSpeedPanel;
    private View mNaviPreview;
    private View mZoomPanel;
    private View mStatusTopbar;

    public SimNaviView(Context context) {
        super(context);
    }

    public SimNaviView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimNaviView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mExitSpeedPanel = findViewById(R.id.ll_exit_speed_continue);
        mNaviPreview = findViewById(R.id.iv_navigation_preview);
        mZoomPanel = findViewById(R.id.ll_zoom);
        mStatusTopbar = findViewById(R.id.control_status_top_bar);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return super.focusSearch(focused, direction);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> list = new ArrayList<>();
        list.add(mExitSpeedPanel);
        list.add(mStatusTopbar);
        list.add(mZoomPanel);
        list.add(mNaviPreview);
        return list;
    }

}
