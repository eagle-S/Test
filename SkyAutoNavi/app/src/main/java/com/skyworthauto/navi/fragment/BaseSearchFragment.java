package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.map.MyPoiSearch;
import com.skyworthauto.navi.map.MyPoiSearchWithDialog;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ToastUtils;

public class BaseSearchFragment extends BaseFragment implements MyPoiSearch.OnPoiSearchListener {

    private static final String TAG = "BaseSearchFragment";

    public static final int ACTION_SEARCH_DEST = 0;
    public static final int ACTION_SEARCH_HOME = 1;
    public static final int ACTION_SEARCH_COMPANY = 2;
    public static final int ACTION_SEARCH_PASS_POI = 3;

    public static final String TYPE = "type";

    protected MyPoiSearch mPointSearch;
    protected SearchParams mSearchParams;

    protected int mSearchAction = ACTION_SEARCH_DEST;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPointSearch = creatPoiSearch();
    }

    protected MyPoiSearchWithDialog creatPoiSearch() {
        BaseActivity topActivity = GlobalContext.getTopActivity();
        return new MyPoiSearchWithDialog(topActivity, mMapController.getMyLocation());
    }

    protected void searchAround(String aroundType) {
        mPointSearch.searchAround(aroundType, this);
    }

    protected void searchDest(String dest) {
        mPointSearch.searchDest(dest, this);
    }

    protected void searchPOIId(String id) {
        mPointSearch.searchPOIId(id, this);
    }

    @Override
    public void onSearchSuccess(SearchResult result) {
        L.d(TAG, "onSearchSuccess:" + result);
        replaceFragmentToActivity(DestSelectFragment.newInstance(mSearchAction, result));
    }

    @Override
    public void onSearchFailed(String reason) {
        L.d(TAG, "onSearchFailed:" + reason);
        ToastUtils.show(getActivity(), reason);
    }

}
