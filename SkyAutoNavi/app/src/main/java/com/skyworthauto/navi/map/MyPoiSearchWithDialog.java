package com.skyworthauto.navi.map;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.fragment.SearchResult;
import com.skyworthauto.navi.util.ResourceUtils;
import com.skyworthauto.navi.views.WaitDialogFragment;

public class MyPoiSearchWithDialog extends MyPoiSearch {

    private WaitDialogFragment mDialogFragment;

    public interface OnPoiSearchListener {
        void onSearchSuccess(SearchResult result);

        void onSearchFailed(String reason);
    }

    public MyPoiSearchWithDialog(BaseActivity activity, MyLocation myLocation) {
        super(activity, myLocation);
    }


    protected void onSearchBegin() {
        mDialogFragment = WaitDialogFragment.newInstance(ResourceUtils.getString(R.string.search));
        mDialogFragment.show(mActivity.getSupportFragmentManager(), "waitDialog");

    }

    protected void onSearchEnd() {
        if (mDialogFragment != null && mDialogFragment.isVisible()) {
            mDialogFragment.dismiss();
        }
    }
}
