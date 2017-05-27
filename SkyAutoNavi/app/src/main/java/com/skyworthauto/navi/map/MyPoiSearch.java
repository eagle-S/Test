package com.skyworthauto.navi.map;

import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseActivity;
import com.skyworthauto.navi.fragment.SearchResult;
import com.skyworthauto.navi.util.ErrorInfo;
import com.skyworthauto.navi.util.MapUtils;
import com.skyworthauto.navi.util.ResourceUtils;
import com.skyworthauto.navi.util.ToastUtil;
import com.amap.api.maps.AMapUtils;

import java.util.ArrayList;
import java.util.List;

public class MyPoiSearch implements PoiSearch.OnPoiSearchListener {

    private static final String TAG = "SkyPoiSearch";
    protected final BaseActivity mActivity;
    protected final MyLocation mMyLocation;
    private int mCurrentPage;
    private String mSearchKeyword;
    private PoiSearch.Query mQuery;
    private PoiSearch mPoiSearch;
    private PoiResult mPoiResult;
    private OnPoiSearchListener mOnPoiSearchListener;

    public interface OnPoiSearchListener {
        void onSearchSuccess(SearchResult result);

        void onSearchFailed(String reason);
    }

    public MyPoiSearch(BaseActivity activity, MyLocation myLocation) {
        mActivity = activity;
        mMyLocation = myLocation;
    }

    public void searchPOIId(String poiID, OnPoiSearchListener listener) {
        onSearchBegin();

        mOnPoiSearchListener = listener;
        mPoiSearch = new PoiSearch(mActivity, null);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIIdAsyn(poiID);
    }

    public void searchDest(String keyword, OnPoiSearchListener listener) {
        mOnPoiSearchListener = listener;
        mQuery = createQuery(keyword, "", mMyLocation.getCityCode(), 10);
        searchPoi(mQuery, mMyLocation.getLocation(), false);
    }

    public void searchAround(String ctgr, OnPoiSearchListener listener) {
        mOnPoiSearchListener = listener;
        mQuery = createQuery("", ctgr, mMyLocation.getCityCode(), 10);
        searchPoi(mQuery, mMyLocation.getLocation(), true);
    }

    private void searchPoi(PoiSearch.Query query, LatLonPoint center, boolean searchAround) {
        onSearchBegin();
        mCurrentPage = 0;
        query.setPageNum(mCurrentPage);

        mPoiSearch = new PoiSearch(mActivity, query);
        if (searchAround) {
            mPoiSearch.setBound(new PoiSearch.SearchBound(center, 5000, false));
        }
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIAsyn();
    }

    private PoiSearch.Query createQuery(String keyword, String ctgr, String city, int pageSize) {
        PoiSearch.Query query = new PoiSearch.Query(keyword, ctgr, city);
        query.requireSubPois(true);
        query.setPageSize(pageSize);
        return query;
    }

    public void searchNextPage() {
        if (mQuery != null && mPoiSearch != null && mPoiResult != null) {
            if (mPoiResult.getPageCount() - 1 > mCurrentPage) {
                mCurrentPage++;
                mQuery.setPageNum(mCurrentPage);// 设置查后一页
                mPoiSearch.searchPOIAsyn();
            } else {
                ToastUtil.show(mActivity, R.string.no_result_found);
            }
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        onSearchEnd();

        Log.d(TAG, "onPoiSearched, rcode:" + rCode);
        if (rCode != AMapException.CODE_AMAP_SUCCESS) {
            onSearchFailed(ErrorInfo.getSearchError(rCode));
            return;
        }

        if (result == null || result.getQuery() == null || !result.getQuery().equals(mQuery)) {

            Log.d(TAG, "onPoiSearched, result aaaa");
            onSearchFailed(ResourceUtils.getResources().getString(R.string.no_result_found));
            return;
        }

        mPoiResult = result;
        // 取得搜索到的poiitems有多少页
        List<PoiItem> poiItems = mPoiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
        List<SuggestionCity> suggestionCities =
                mPoiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

        if (poiItems != null && poiItems.size() > 0) {
            for (PoiItem item : poiItems) {
                checkDistance(item);
                dump(item);
            }
            onSearchSuccess(
                    new SearchResult(mSearchKeyword, mCurrentPage, mPoiResult.getPageCount(),
                            mPoiResult.getPois()));
        } else if (suggestionCities != null && suggestionCities.size() > 0) {
            showSuggestCity(suggestionCities);
        } else {
            Log.d(TAG, "onPoiSearched, result bbbb");
            onSearchFailed(ResourceUtils.getResources().getString(R.string.no_result_found));
        }
    }

    protected void checkDistance(PoiItem item) {
        if (item.getDistance() < 0) {
            item.setDistance((int) AMapUtils.calculateLineDistance(
                    MapUtils.convertToLatLng(mMyLocation.getLocation()),
                    MapUtils.convertToLatLng(item.getLatLonPoint())));
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem item, int rCode) {
        onSearchEnd();
        Log.d(TAG, "onPoiItemSearched, rcode:" + rCode + "item="+ item);
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (item != null) {
                checkDistance(item);
                dump(item);

                ArrayList<PoiItem> list = new ArrayList<>();
                list.add(item);
                onSearchSuccess(
                        new SearchResult("asdads", 0, 1, list));
            }
        } else {
            onSearchFailed(ErrorInfo.getSearchError(rCode));
        }

    }

    protected void onSearchSuccess(SearchResult result) {
        if (mOnPoiSearchListener != null) {
            mOnPoiSearchListener.onSearchSuccess(result);
        }
    }

    protected void onSearchFailed(String searchError) {
        if (mOnPoiSearchListener != null) {
            mOnPoiSearchListener.onSearchFailed(searchError);
        }
    }

    private void dump() {
        Log.d(TAG, "dest dump:");
        List<PoiItem> poiItems = mPoiResult.getPois();
        for (PoiItem poiItem : poiItems) {
            dump(poiItem);
        }
    }

    private void dump(PoiItem poiItem) {
        Log.d(TAG, "getAdCode=" + poiItem.getAdCode() + "getAdName=" + poiItem.getAdName()
                + "getBusinessArea=" + poiItem.getBusinessArea() + "getCityCode=" + poiItem
                .getCityCode() + "getCityName=" + poiItem.getCityName() + "getDirection="
                + poiItem.getDirection() + "getDistance=" + poiItem.getDistance() + "getEmail="
                + poiItem.getEmail() + "getEnter=" + poiItem.getEnter() + "getExit=" + poiItem
                .getExit() + "getIndoorData=" + poiItem.getIndoorData() + "getLatLonPoint="
                + poiItem.getLatLonPoint() + "getParkingType=" + poiItem.getParkingType()
                + "getPhotos=" + poiItem.getPhotos() + "getPoiExtension=" + poiItem
                .getPoiExtension() + "getPoiId=" + poiItem.getPoiId() + "getPostcode=" + poiItem
                .getPostcode() + "getProvinceCode=" + poiItem.getProvinceCode()
                + "getProvinceName=" + poiItem.getProvinceName() + "getSnippet=" + poiItem
                .getSnippet() + "getSubPois=" + poiItem.getSubPois() + "getTel=" + poiItem
                .getTel() + "getTitle=" + poiItem.getTitle() + "getTypeCode=" + poiItem
                .getTypeCode() + "getTypeDes=" + poiItem.getTypeDes() + "getWebsite=" + poiItem
                .getWebsite());
    }

    private void showSuggestCity(List<SuggestionCity> cities) {
        for (int i = 0; i < cities.size(); i++) {
            SuggestionCity city = cities.get(i);
            Log.d(TAG, "getCityName:" + city.getCityName() + ", getCityCode:" + city.getCityCode()
                    + ", getAdCode:" + city.getAdCode() + ", getSuggestionNum:" + city
                    .getSuggestionNum());
        }
    }

    protected void onSearchBegin() {

    }

    protected void onSearchEnd() {

    }
}
