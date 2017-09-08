package com.skyworthauto.navi.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class MainMapView extends FocusRelativeLayout {
    private static final String TAG = "MainFragmentView";

    private View mGoSearchBtn;
    private View mMoreSetting;
    private View mStatusTopBar;
    private View mInteractiveView;

    public MainMapView(Context context) {
        super(context);
    }

    public MainMapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mGoSearchBtn = findViewById(R.id.ib_go_search);
        mMoreSetting = findViewById(R.id.iv_more_setting);
        mStatusTopBar = findViewById(R.id.control_status_top_bar);
        mInteractiveView = findViewById(R.id.map_interactive_view);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> subRoots = new ArrayList();
        addSubRoot(subRoots, mGoSearchBtn);
        addSubRoot(subRoots, mMoreSetting);
        addSubRoot(subRoots, mStatusTopBar);
        addSubRoot(subRoots, mInteractiveView);
        return subRoots;
    }

    private void addSubRoot(List<View> list, View root) {
        if (root.getVisibility() == VISIBLE) {
            list.add(root);
        }
    }
}
