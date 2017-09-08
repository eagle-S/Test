package com.skyworthauto.navi.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class NaviView extends FocusRelativeLayout {

    private View mExitSpeedPanel;
    private View mNaviPreview;
    private View mInteractiveView;
    private View mKeepOnNavi;


    public NaviView(Context context) {
        super(context);
    }

    public NaviView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mExitSpeedPanel = findViewById(R.id.ll_exit_setting);
        mNaviPreview = findViewById(R.id.iv_navigation_preview);
        mInteractiveView = findViewById(R.id.map_interactive_view);
        mKeepOnNavi = findViewById(R.id.keep_on_navigation_caption);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return super.focusSearch(focused, direction);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> list = new ArrayList<>();
        list.add(mExitSpeedPanel);
        list.add(mInteractiveView);
        list.add(mNaviPreview);
        list.add(mKeepOnNavi);
        return list;
    }
}
