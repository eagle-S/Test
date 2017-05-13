package com.skyworthauto.navi.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.Polyline;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.util.ToastUtil;
import com.skyworthauto.navi.util.Utils;

import java.util.List;

public class DestSearchFragment extends BaseFragment implements TextWatcher,
        Inputtips.InputtipsListener, AdapterView.OnItemClickListener, PoiSearch.OnPoiSearchListener,
        View.OnClickListener {

    private static final String TAG = "DestSearchFragment";
    private ListView mListView;
    private DestInputTipsAdapter mDestInputTipsAdapter;
    private TextView mNoHistory;
    private PoiSearch.Query mQuery;
    private int mCurrentPage;
    private PoiSearch mPoiSearch;
    private PoiResult mPoiResult;

    private EditText mSearchEdit;
    private boolean mIsUserInput = false;
    private String mSearchKeyword;

    private View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
        }

    };

    private TextView.OnEditorActionListener mEditorActionListener =
            new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        onSearchClick(getCurInputText());
                        return true;
                    }
                    return false;
                }
            };


    private void onSearchClick(String keyword) {
        keyword = keyword.trim();
        if (TextUtils.isEmpty(keyword)) {
            mSearchEdit.setText(keyword);
            ToastUtil.show(R.string.about);
            return;
        }

        setEditCursor(false);
        mIsUserInput = false;
        mSearchEdit.setText(keyword);
        mSearchKeyword = keyword;
        Utils.hideInputMethod(mSearchEdit);
        SearchHistoryManager.add(keyword);
    }

    private void setEditCursor(boolean isEditFocus) {
        mSearchEdit.setCursorVisible(isEditFocus);
    }

    private Polyline polyline;

    public static DestSearchFragment newInstance() {
        DestSearchFragment fragment = new DestSearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_dest_layout, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        v.findViewById(R.id.btn_search_back).setOnClickListener(this);
        mSearchEdit = (EditText) v.findViewById(R.id.search_search_layout);
        mSearchEdit.addTextChangedListener(this);
        mSearchEdit.setOnEditorActionListener(mEditorActionListener);
        mSearchEdit.requestFocus();

        mNoHistory = (TextView) v.findViewById(R.id.tv_nohistory);

        mListView = (ListView) v.findViewById(R.id.lv_search_his);

        mDestInputTipsAdapter = new DestInputTipsAdapter();
        mListView.setAdapter(mDestInputTipsAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = getCurInputText().trim();
        if (TextUtils.isEmpty(newText)) {
            mNoHistory.setVisibility(hasHistory() ? View.GONE : View.VISIBLE);
            return;
        }

        mNoHistory.setVisibility(View.GONE);
        InputtipsQuery inputquery = new InputtipsQuery(newText, "");
        Inputtips inputTips = new Inputtips(getActivity(), inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    private boolean hasHistory() {
        return false;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            for (Tip tip : tipList) {
                Log.d(TAG, "tip: " + "name:" + tip.getName() + ",district:" + tip.getDistrict()
                        + ",adcode:" + tip.getAdcode() + ",getAddress:" + tip.getAddress()
                        + ",PoiID:" + tip.getPoiID() + ",TypeCode: " + tip.getTypeCode() + ",point"
                        + tip.getPoint());
            }
            mDestInputTipsAdapter.setAllData(tipList);
            mDestInputTipsAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentPage = 0;
        mQuery = new PoiSearch.Query(mDestInputTipsAdapter.getItem(position).getName(), "", "");
        mQuery.requireSubPois(true);
        mQuery.setPageSize(10);
        mQuery.setPageNum(mCurrentPage);

        Log.e(TAG, "mMapController=" + mMapController);

        mPoiSearch = new PoiSearch(getActivity(), mQuery);
        mPoiSearch.setBound(
                new PoiSearch.SearchBound(mMapController.getMyLocation().getLocation(), 1000000,
                        false));
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode != AMapException.CODE_AMAP_SUCCESS) {
            ToastUtil.showerror(getActivity(), rCode);
            return;
        }

        if (result == null || result.getQuery() == null) {
            ToastUtil.show(getActivity(), R.string.alipay_fail);
            return;
        }

        if (result.getQuery().equals(mQuery)) {// 是否是同一条
            mPoiResult = result;
            // 取得搜索到的poiitems有多少页
            List<PoiItem> poiItems = mPoiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
            List<SuggestionCity> suggestionCities =
                    mPoiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

            if (poiItems != null && poiItems.size() > 0) {
                addMarks(poiItems);

                replaceFragmentToStack(DestResultFragment
                        .newInstance(new DestResult(mSearchKeyword, mCurrentPage, mPoiResult)));
            } else if (suggestionCities != null && suggestionCities.size() > 0) {
                //                showSuggestCity(suggestionCities);
            } else {
                ToastUtil.show(getActivity(), R.string.alipay_fail);
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.d(TAG, "onPoiItemSearched");
    }

//    private AMap getAMap() {
//        return ((MainActivity) getActivity()).getAMap();
//    }

    protected void addMarks(List<PoiItem> poiItems) {
        mMapController.addMarks(poiItems);
    }

    public void queryNextPage() {
        if (mQuery != null && mPoiSearch != null && mPoiResult != null) {
            if (mPoiResult.getPageCount() - 1 > mCurrentPage) {
                mCurrentPage++;
                mQuery.setPageNum(mCurrentPage);// 设置查后一页
                mPoiSearch.searchPOIAsyn();
            } else {
                ToastUtil.show(getActivity(), R.string.alipay_fail);
            }
        }
    }

    private String getCurInputText() {
        return mSearchEdit.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_back:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }
}
