package com.skyworthauto.navi.fragment;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;

import java.util.ArrayList;

public class SearchResult {
    public String mKeyword;
    public String mQuery;
    public String mCtgr;
    public String mCity;
    public int mCurPage;
    public int mTotalPage;
    public ArrayList<PoiItem> mResultList;


    public SearchResult(String keyWord, int curpage, int totalPage, ArrayList resultList) {
        mKeyword = keyWord;
        mCurPage = curpage;
        mTotalPage = totalPage;
        mResultList = resultList;
    }
}
