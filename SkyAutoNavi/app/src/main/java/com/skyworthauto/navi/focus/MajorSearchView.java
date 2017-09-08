package com.skyworthauto.navi.focus;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class MajorSearchView extends FocusRelativeLayout {
    private View mHomeTitle;
    private View mDestinationHistory;
    private View mDestHotLayout;

    public MajorSearchView(Context context) {
        super(context);
    }

    public MajorSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MajorSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHomeTitle = findViewById(R.id.auto_home_title_layout);
        mDestinationHistory = findViewById(R.id.auto_destination_history);
        mDestHotLayout = findViewById(R.id.auto_dest_hot_layout);
    }

    @Override
    public View focusSearch(int direction) {
        return super.focusSearch(direction);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> subRoots = new ArrayList<>();
        subRoots.add(mHomeTitle);
        subRoots.add(mDestinationHistory);
        subRoots.add(mDestHotLayout);
        return subRoots;
    }
}
