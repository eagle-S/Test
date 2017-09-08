package com.skyworthauto.navi.focus;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class DestSelectView extends FocusRelativeLayout {
    private View mTitlePanel;
    private View mDestList;
    private View mNaviBtn;
    private View mInteractiveView;
    private View mDestDetailScrollview;

    public DestSelectView(Context context) {
        super(context);
    }

    public DestSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DestSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitlePanel = findViewById(R.id.left_layout_title_panel);
        mDestList = findViewById(R.id.dest_listview);
        mDestDetailScrollview = findViewById(R.id.auto_poi_detail_scrollview);
        mNaviBtn = findViewById(R.id.search_result_navi);
        mInteractiveView = findViewById(R.id.map_interactive_view);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        return super.focusSearch(focused, direction);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> roots = new ArrayList<>();
        roots.add(mTitlePanel);
        roots.add(mDestList);
        roots.add(mDestDetailScrollview);
        roots.add(mNaviBtn);
        roots.add(mInteractiveView);
        return roots;
    }
}
