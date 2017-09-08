package com.skyworthauto.navi.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;

public class SearchMoreFragment extends BaseSearchFragment implements View.OnClickListener {

    private static final String TAG = "SearchMoreFragment";
    private SearchMoreAdapter mSearchMoreAdapter;

    private  MultiTypeAdapter.OnItemClickListener mOnClickListener = new MultiTypeAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, ViewHolder holder, int position) {
            SearchMoreChildItemData data = (SearchMoreChildItemData) holder.getData();
            searchAround(data.getName());
        }
    };

    public static SearchMoreFragment newInstance() {
        return new SearchMoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();
    }

    private void initDate() {
        mSearchMoreAdapter = new SearchMoreAdapter(getActivity(), mOnClickListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_more_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_search_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        findViewById(rootView, R.id.auto_search_back_btn).setOnClickListener(this);

        RecyclerView  recyclerView = findViewById(rootView, R.id.auto_search_more_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSearchMoreAdapter.setAllData(SearchMoreConfig.parseConfig());
        recyclerView.setAdapter(mSearchMoreAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_back_btn:
                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

}
