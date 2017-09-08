package com.skyworthauto.navi.fragment.search;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.JsonConstants;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ToastUtils;
import com.skyworthauto.navi.util.Utils;
import com.skyworthauto.navi.fragment.NormalDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MinorSearchFragment extends BaseSearchFragment implements Inputtips.InputtipsListener {

    private static final String TAG = "DestSearchFragment";

    @BindView(R.id.lv_search_his)
    RecyclerView mListView;
    @BindView(R.id.tv_nohistory)
    TextView mNoHistory;
    @BindView(R.id.search_search_layout)
    EditText mSearchEdit;
    @BindView(R.id.btn_search)
    TextView mSearchButton;

    private DestInputTipsAdapter mDestInputTipsAdapter;
    private View mFooterView;

    private boolean mIsUserInput = false;
    private String mSearchKeyword;

    private View.OnFocusChangeListener mFocusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            L.d(TAG, "onFocusChange:" + hasFocus);
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
            L.d(TAG, "onTextChanged");
            if (TextUtils.isEmpty(getCurInputText())) {
                mSearchButton.setEnabled(false);
                mSearchButton.setFocusable(false);
                showList(SearchHistoryManager.getHistoryList(), true);
                return;
            }

            mSearchButton.setEnabled(true);
            mSearchButton.setFocusable(true);

            InputtipsQuery inputquery = new InputtipsQuery(getCurInputText(),
                    mMapController.getMyLocation().getCityCode());
            Inputtips inputTips = new Inputtips(getActivity(), inputquery);
            inputTips.setInputtipsListener(MinorSearchFragment.this);
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

    public static MinorSearchFragment newInstance(int type) {
        MinorSearchFragment fragment = new MinorSearchFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchAction = args.getInt(TYPE, ACTION_SEARCH_DEST);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_dest_layout;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_search_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initTitle(rootView);
        initContentViews(rootView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initTitle(View v) {
        mSearchEdit.addTextChangedListener(mWatcher);
        mSearchEdit.setOnEditorActionListener(mEditorActionListener);
        mSearchEdit.setOnFocusChangeListener(mFocusChangeListener);
        mSearchEdit.setHint(getEditHint());

        mSearchButton.setEnabled(hasInput());
        mSearchButton.setFocusable(hasInput());
    }

    private int getEditHint() {
        switch (mSearchAction) {
            case ACTION_SEARCH_HOME:
                return R.string.auto_search_home_position;
            case ACTION_SEARCH_COMPANY:
                return R.string.auto_search_company_position;
            case ACTION_SEARCH_PASSWAY_POI:
                return R.string.search_add_waypoi_edit_hint;
            case ACTION_SEARCH_DEST:
            default:
                return R.string.save_search_hint;
        }
    }

    private void initContentViews(View v) {
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDestInputTipsAdapter = new DestInputTipsAdapter(getActivity());
        mFooterView = createFootView();
        mListView.setAdapter(mDestInputTipsAdapter);
        mDestInputTipsAdapter.setOnItemClickListener(new MultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                SearchHistoryData data = mDestInputTipsAdapter.getItem(position);
                if (JsonConstants.Value.KEYWORD.equals(data.getType())) {
                    String keyword = (String) data.getData();
                    SearchHistoryManager.add(keyword);
                    searchDest(keyword);
                } else if (JsonConstants.Value.TIP.equals(data.getType())) {
                    Tip tip = (Tip) data.getData();
                    SearchHistoryManager.add(tip);
                    searchPOIId(tip.getPoiID());
                }
            }
        });
        showList(SearchHistoryManager.getHistoryList(), true);
    }

    private View createFootView() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.search_del_history_footer, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        return view;
    }

    private void showConfirmDialog() {
        NormalDialogFragment fragment = NormalDialogFragment.newInstance();
        fragment.setMessage(R.string.auto_search_dest_dialog_clear_all);
        fragment.show(this, "confirm_dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.d(TAG, "onActivityResult");
        if (requestCode == Constant.REQUEST_CODE_FOR_ACK) {
            if (resultCode == Activity.RESULT_OK) {
                clearHistory();
            }
        }
    }

    protected void clearHistory() {
        L.d(TAG, "clearHistory");
        SearchHistoryManager.clear();
        showList(new ArrayList<SearchHistoryData>(), false);
    }

    private void showList(List<SearchHistoryData> listData, boolean isHistory) {
        boolean isListEmpty = (listData == null) ? true : listData.isEmpty();
        mListView.setVisibility(isListEmpty ? View.GONE : View.VISIBLE);
        mNoHistory.setVisibility(isListEmpty ? View.VISIBLE : View.GONE);


        //        if (isHistory) {
        //            mListView.addFooterView(mFooterView);
        //        } else {
        //            mListView.removeFooterView(mFooterView);
        //        }

        if (!isListEmpty) {
            mDestInputTipsAdapter.setAllData(listData);
            mDestInputTipsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<SearchHistoryData> list = new ArrayList<>();
            for (Tip tip : tipList) {

                L.d(TAG, "tip: " + "name:" + tip.getName() + ",district:" + tip.getDistrict()
                        + ",adcode:" + tip.getAdcode() + ",getAddress:" + tip.getAddress()
                        + ",PoiID:" + tip.getPoiID() + ",TypeCode: " + tip.getTypeCode() + ",point"
                        + tip.getPoint());
                list.add(new TipSearchHistoryData(tip));
            }

            showList(list, false);
        } else {
            ToastUtils.showerror(getActivity(), rCode);
        }
    }

    private String getCurInputText() {
        return mSearchEdit.getText().toString().trim();
    }

    private boolean hasInput() {
        String newText = getCurInputText();
        return !TextUtils.isEmpty(newText);
    }

    @OnClick({R.id.auto_search_back_btn, R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_back_btn:
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
