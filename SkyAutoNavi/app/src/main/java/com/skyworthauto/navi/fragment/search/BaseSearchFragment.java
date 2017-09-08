package com.skyworthauto.navi.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyworthauto.navi.BaseActivity;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.fragment.dest.DestSelectFragment;
import com.skyworthauto.navi.map.MyPoiSearch;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ResourceUtils;
import com.skyworthauto.navi.util.ToastUtils;
import com.skyworthauto.navi.fragment.WaitDialogFragment;

public abstract class BaseSearchFragment extends BaseFragment implements MyPoiSearch.OnPoiSearchListener {

    private static final String TAG = "BaseSearchFragment";

    public static final int ACTION_SEARCH_DEST = 0;
    public static final int ACTION_SEARCH_HOME = 1;
    public static final int ACTION_SEARCH_COMPANY = 2;
    public static final int ACTION_SEARCH_PASSWAY_POI = 3;

    public static final String TYPE = "type";

    protected MyPoiSearch mPointSearch;
    protected SearchParams mSearchParams;

    protected int mSearchAction = ACTION_SEARCH_DEST;
    private WaitDialogFragment mDialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPointSearch = creatPoiSearch();
    }

    protected MyPoiSearch creatPoiSearch() {
        BaseActivity topActivity = GlobalContext.getTopActivity();
        return new MyPoiSearch(topActivity, mMapController.getMyLocation());
    }

    protected void searchAround(String aroundType) {
        showWaitDialog();
        mPointSearch.searchAround(aroundType, this);
    }

    protected void searchDest(String dest) {
        showWaitDialog();
        mPointSearch.searchDest(dest, this);
    }

    protected void searchPOIId(String id) {
        showWaitDialog();
        mPointSearch.searchPOIId(id, this);
    }

    private void showWaitDialog() {
        mDialogFragment =
                WaitDialogFragment.newInstance(ResourceUtils.getString(R.string.searching));
        mDialogFragment.show(GlobalContext.getFragmentManager(), "waitDialog");
    }

    @Override
    public void onSearchSuccess(SearchResult result) {
        L.d(TAG, "onSearchSuccess:" + result);
        dismissWaitDialog();
        replaceFragmentToActivity(DestSelectFragment.newInstance(mSearchAction, result));
    }

    protected void dismissWaitDialog() {
        if (mDialogFragment != null) {
            mDialogFragment.dismiss();
        }
    }

    @Override
    public void onSearchFailed(String reason) {
        L.d(TAG, "onSearchFailed:" + reason);
        dismissWaitDialog();
        ToastUtils.show(getActivity(), reason);
    }

}
