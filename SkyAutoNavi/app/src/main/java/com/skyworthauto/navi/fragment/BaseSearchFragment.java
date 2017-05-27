package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.map.MyPoiSearch;
import com.skyworthauto.navi.map.MyPoiSearchWithDialog;
import com.skyworthauto.navi.util.ToastUtil;

public class BaseSearchFragment extends BaseFragment implements MyPoiSearch.OnPoiSearchListener {

    private static final String TAG = "BaseSearchFragment";
    protected MyPoiSearch mPointSearch;

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
        Log.d(TAG, "onSearchSuccess:" + result);
        replaceFragmentToActivity(DestSelectFragment.newInstance(result));
    }

    @Override
    public void onSearchFailed(String reason) {
        Log.d(TAG, "onSearchFailed:" + reason);
        ToastUtil.show(getActivity(), reason);
    }


}
