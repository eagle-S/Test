package com.skyworthauto.navi.focus;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.skyworthauto.navi.R;

import java.util.ArrayList;
import java.util.List;

public class SearchMoreView extends FocusRelativeLayout {
    private View mTitlePanel;
    private View mSearchMoreList;

    public SearchMoreView(Context context) {
        super(context);
    }

    public SearchMoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitlePanel = findViewById(R.id.search_more_title_panel);
        mSearchMoreList = findViewById(R.id.auto_search_more_listview);
    }

    @Override
    protected List<View> getSubRoots() {
        List<View> subRoots = new ArrayList<>();
        subRoots.add(mTitlePanel);
        subRoots.add(mSearchMoreList);
        return subRoots;
    }
}
