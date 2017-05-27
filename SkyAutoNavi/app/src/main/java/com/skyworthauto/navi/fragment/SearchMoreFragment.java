package com.skyworthauto.navi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.skyworthauto.navi.R;

public class SearchMoreFragment extends BaseSearchFragment implements View.OnClickListener {

    private static final String TAG = "SearchMoreFragment";
    private SearchMoreAdapter mSearchMoreAdapter;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String searchType = ((SearchMoreChildAdapter.SearchMoreChildItemViewHolder) v.getTag())
                    .getChildItemData().getName();
            searchAround(searchType);
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
        mSearchMoreAdapter = new SearchMoreAdapter(mOnClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_more_fragment, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        findViewById(root, R.id.auto_search_back_btn).setOnClickListener(this);
        ListView listView = findViewById(root, R.id.auto_search_more_listview);

        mSearchMoreAdapter.setAllData(SearchConfig.parseConfig());
        listView.setAdapter(mSearchMoreAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_back_btn:
                getFragmentManager().popBackStack();
                break;
        }
    }

}
