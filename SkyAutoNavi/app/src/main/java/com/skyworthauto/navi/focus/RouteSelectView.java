package com.skyworthauto.navi.focus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;


public class RouteSelectView extends FocusRelativeLayout {
    private View mTitlePanel;
    private View mPathList;
    private View mStartNaviBtn;
    private View mPassWayBtn;
    private View mInteractiveView;
    private View mDetailList;

    public RouteSelectView(Context context) {
        super(context);
    }

    public RouteSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RouteSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitlePanel = findViewById(R.id.route_panel_title_content);
        mPathList = findViewById(R.id.dest_path_listview);
        mStartNaviBtn = findViewById(R.id.btn_startnavi);
        mPassWayBtn = findViewById(R.id.route_along_search_open_btn);
        mInteractiveView = findViewById(R.id.map_interactive_view);
        mDetailList = findViewById(R.id.car_detail_list);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> list = new ArrayList<>();
        list.add(mTitlePanel);
        list.add(mPathList);
        list.add(mStartNaviBtn);
        list.add(mPassWayBtn);
        list.add(mInteractiveView);
        list.add(mDetailList);
        return list;
    }
}
