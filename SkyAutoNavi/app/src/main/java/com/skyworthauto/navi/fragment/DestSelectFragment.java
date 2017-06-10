package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.MapUtils;
import com.skyworthauto.navi.util.SharePreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class DestSelectFragment extends BaseSearchFragment implements View.OnClickListener {

    public static final String KEYWORD = "keyword";
    public static final String CUR_PAGE = "curPage";
    public static final String PAGE_LIST = "pageList";
    private static final String TAG = "DestSelectFragment";
    private String mKeyword;
    private int mCurPage;
    private ArrayList<PoiItem> mResult;
    private DestResultAdapter mDestResultAdapter;
    private NaviController mNaviController;

    public static DestSelectFragment newInstance(int searchType, SearchResult result) {
        DestSelectFragment fragment = new DestSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, searchType);
        bundle.putString(KEYWORD, result.mKeyword);
        bundle.putInt(CUR_PAGE, result.mCurPage);
        bundle.putParcelableArrayList(PAGE_LIST, result.mResultList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mSearchType = args.getInt(TYPE, TYPE_SEARCH_NORMAL);
        mKeyword = args.getString(KEYWORD);
        mCurPage = args.getInt(CUR_PAGE);
        mResult = args.getParcelableArrayList(PAGE_LIST);

        mNaviController = new NaviController();
        mNaviController.setCalculateListenner(new NaviController.OnCalculateSuccessListenner() {

            @Override
            public void onCalculateSuccess(int[] ids) {
                Log.d(TAG, "onCalculateSuccess aaa");
                replaceFragmentToActivity(RouteSelectFragment.newInstance(ids));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_dest_result_fragment, container, false);
        initViews(v);
        addMarks(mResult);
        return v;
    }

    private void initViews(View v) {
        findViewById(v, R.id.auto_loading_left_bar_top).setOnClickListener(this);
        findViewById(v, R.id.search_result_navi).setOnClickListener(this);

        TextView title = findViewById(v, R.id.auto_search_keyword);
        title.setText(mKeyword);

        initSearchNavi(v);

        DestResultListView listView = findViewById(v, R.id.dest_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0) {
                    onHeaderItemClick();
                    return;
                }

                mDestResultAdapter.setSelectedPos(position);
                mDestResultAdapter.notifyDataSetChanged();

                PoiItem item = mDestResultAdapter.getItem(position);
                mMapController.onMarkerClick(position);
            }
        });
        mDestResultAdapter = new DestResultAdapter(getActivity());
        mDestResultAdapter.setAllData(mResult);
        listView.setAdapter(mDestResultAdapter);
    }

    private void initSearchNavi(View v) {
        ImageView naviImage = findViewById(v, R.id.search_result_navi_image);
        TextView naviText = findViewById(v, R.id.search_result_navi_text);

        switch (mSearchType) {
            case TYPE_SEARCH_HOME:
                naviImage.setImageResource(R.drawable.auto_ic_button_home_normal);
                naviText.setText(R.string.common_add_home_btn_text);
                break;
            case TYPE_SEARCH_COMPANY:
                naviImage.setImageResource(R.drawable.auto_ic_button_company_normal);
                naviText.setText(R.string.common_add_company_btn_text);
                break;
            case TYPE_SEARCH_PASS_POI:
                naviImage.setImageResource(R.drawable.auto_ic_button_home_normal);
                naviText.setText(R.string.search_result_map_add_waypoi);
                break;
            case TYPE_SEARCH_NORMAL:
            default:
                naviImage.setImageResource(R.drawable.auto_route_start_navi_icon);
                naviText.setText(R.string.go_here);
                break;
        }

    }

    private void onHeaderItemClick() {

    }

    protected void addMarks(List<PoiItem> poiItems) {
        mMapController.showDestSelectMap(poiItems);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_loading_left_bar_top:
                getFragmentManager().popBackStack();
                break;
            case R.id.search_result_navi:
                onNaviBtnClick();
                break;
            default:
                break;
        }
    }

    private void onNaviBtnClick() {
        switch (mSearchType) {
            case TYPE_SEARCH_HOME:
                LatLonPoint destPoint =
                        mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos())
                                .getLatLonPoint();
                FavoritePoiManager.saveHomeAddress(destPoint);
                GlobalContext.getFragmentManager().popBackStackImmediate("setting_home",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case TYPE_SEARCH_COMPANY:
                LatLonPoint point = mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos())
                        .getLatLonPoint();
                FavoritePoiManager.saveCompanyAddress(point);
                GlobalContext.getFragmentManager().popBackStackImmediate("setting_company",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case TYPE_SEARCH_PASS_POI:
                break;
            case TYPE_SEARCH_NORMAL:
            default:
                calculateDriveRoute();
                break;
        }
    }


    private void calculateDriveRoute() {
        Log.d(TAG, "calculateDriveRoute");

        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        LatLonPoint destPoint =
                mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos()).getLatLonPoint();
        NaviLatLng destLocation = new NaviLatLng(destPoint.getLatitude(), destPoint.getLongitude());
        mNaviController.calculateDriveRoute(myLocation, destLocation);
    }


}
