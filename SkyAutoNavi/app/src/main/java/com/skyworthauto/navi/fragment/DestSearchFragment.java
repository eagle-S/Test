package com.skyworthauto.navi.fragment;


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

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.util.ToastUtil;
import com.skyworthauto.navi.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class DestSearchFragment extends BaseSearchFragment implements Inputtips.InputtipsListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "DestSearchFragment";
    private ListView mListView;
    private DestInputTipsAdapter mDestInputTipsAdapter;
    private TextView mNoHistory;

    private EditText mSearchEdit;
    private TextView mSearchButton;
    private View mFooterView;


    private boolean mIsUserInput = false;
    private String mSearchKeyword;

    private View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.d(TAG, "onFocusChange:" + hasFocus);
            if (hasFocus) {
                Utils.showInputMethod();
            } else {
                Utils.hideInputMethod(v);
            }
        }

    };

    private TextView.OnEditorActionListener mEditorActionListener =
            new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        onSearchClick();
                        return true;
                    }
                    return false;
                }
            };

    private TextWatcher mWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged");
            if (TextUtils.isEmpty(getCurInputText())) {
                mSearchButton.setEnabled(false);
                showList(SearchHistoryManager.getHistoryList(), true);
                return;
            }

            mSearchButton.setEnabled(true);

            InputtipsQuery inputquery = new InputtipsQuery(getCurInputText(),
                    mMapController.getMyLocation().getCityCode());
            Inputtips inputTips = new Inputtips(getActivity(), inputquery);
            inputTips.setInputtipsListener(DestSearchFragment.this);
            inputTips.requestInputtipsAsyn();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setEditCursor(boolean isEditFocus) {
        mSearchEdit.setCursorVisible(isEditFocus);
    }

    public static DestSearchFragment newInstance() {
        DestSearchFragment fragment = new DestSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initTitle(v);
        initContentViews(v);
    }

    private void initTitle(View v) {
        v.findViewById(R.id.btn_search_back).setOnClickListener(this);
        mSearchEdit = (EditText) v.findViewById(R.id.search_search_layout);
        mSearchEdit.addTextChangedListener(mWatcher);
        mSearchEdit.setOnEditorActionListener(mEditorActionListener);
        mSearchEdit.setOnFocusChangeListener(mFocusChangeListener);
        mSearchEdit.setOnClickListener(this);
        mSearchEdit.requestFocus();

        mSearchButton = (TextView) v.findViewById(R.id.btn_search);
        mSearchButton.setEnabled(hasInput());
        mSearchButton.setOnClickListener(this);
    }

    private void initContentViews(View v) {
        mNoHistory = (TextView) v.findViewById(R.id.tv_nohistory);
        mListView = (ListView) v.findViewById(R.id.lv_search_his);

        mDestInputTipsAdapter = new DestInputTipsAdapter();
        mDestInputTipsAdapter.setAllData(SearchHistoryManager.getHistoryList());
        mFooterView = createFootView();
        mListView.setAdapter(mDestInputTipsAdapter);
        mListView.setOnItemClickListener(this);
        showList(SearchHistoryManager.getHistoryList(), true);
    }

    private View createFootView() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.search_del_history_footer, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchHistoryManager.clear();
                showList(new ArrayList<SearchHistoryData>(), false);
            }
        });

        return view;
    }

    private void showList(List<SearchHistoryData> listData, boolean isHistory) {
        boolean isListEmpty = listData.isEmpty();
        mListView.setVisibility(isListEmpty ? View.GONE : View.VISIBLE);
        mNoHistory.setVisibility(isListEmpty ? View.VISIBLE : View.GONE);

        mDestInputTipsAdapter.setAllData(listData);
        if (isHistory) {
            mListView.addFooterView(mFooterView);
        } else {
            mListView.removeFooterView(mFooterView);
        }

        mDestInputTipsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<SearchHistoryData> list = new ArrayList<>();
            for (Tip tip : tipList) {

                Log.d(TAG, "tip: " + "name:" + tip.getName() + ",district:" + tip.getDistrict()
                        + ",adcode:" + tip.getAdcode() + ",getAddress:" + tip.getAddress()
                        + ",PoiID:" + tip.getPoiID() + ",TypeCode: " + tip.getTypeCode() + ",point"
                        + tip.getPoint());
                list.add(new TipSearchHistoryData(tip));
            }

            showList(list, false);
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tip tip = (Tip) mDestInputTipsAdapter.getItem(position).getData();
        SearchHistoryManager.add(tip);
        searchPOIId(tip.getPoiID());
    }

    private String getCurInputText() {
        return mSearchEdit.getText().toString().trim();
    }

    private boolean hasInput() {
        String newText = getCurInputText();
        return !TextUtils.isEmpty(newText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_search:
                onSearchClick();
                break;
            default:
                break;
        }
    }

    private void onSearchClick() {
        String keyword = getCurInputText();
        setEditCursor(false);
        mIsUserInput = false;
        mSearchEdit.setText(keyword);
        mSearchKeyword = keyword;
        Utils.hideInputMethod(mSearchEdit);

        SearchHistoryManager.add(keyword);
        searchDest(keyword);
    }

}
