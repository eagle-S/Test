package com.skyworthauto.navi.fragment.dest;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DestResultListView extends ListView {

    private int mCurPage;

    public DestResultListView(Context context) {
        super(context);
    }

    public DestResultListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DestResultListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {

    }

    public void scrollToNextPage() {

    }

    public void scrollToLastPage() {

    }

    private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> av, View view, int position, long id) {
            DestResultListView.this.onItemClick(view, id);
        }
    };

    private void onItemClick(View view, long id) {

    }
}
