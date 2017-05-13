package com.skyworthauto.navi.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviException;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DestResultFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEYWORD = "keyword";
    public static final String CUR_PAGE = "curPage";
    public static final String PAGE_LIST = "pageList";
    private String mKeyword;
    private int mCurPage;
    private ArrayList<PoiItem> mResult;
    private DestResultAdapter mDestResultAdapter;

    public static DestResultFragment newInstance(DestResult result) {
        DestResultFragment fragment = new DestResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEYWORD, result.mKeyword);
        bundle.putInt(CUR_PAGE, result.mCurPage);
        bundle.putParcelableArrayList(PAGE_LIST, result.mResult.getPois());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mKeyword = args.getString(KEYWORD);
        mCurPage = args.getInt(CUR_PAGE);
        mResult = args.getParcelableArrayList(PAGE_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_dest_result_fragment, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        findViewById(v, R.id.auto_loading_left_bar_top).setOnClickListener(this);
        findViewById(v, R.id.search_result_navi).setOnClickListener(this);

        TextView title = findViewById(v, R.id.auto_search_keyword);
        title.setText(mKeyword);

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
                mMapController.onMarkerClick(item);
            }
        });
        mDestResultAdapter = new DestResultAdapter();
        mDestResultAdapter.setAllData(mResult);
        listView.setAdapter(mDestResultAdapter);
    }

    private void onHeaderItemClick() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_loading_left_bar_top:
                getFragmentManager().popBackStack();
                break;
            case R.id.search_result_navi:
                calculateDriveRoute();
                break;
            default:
                break;
        }
    }

    private void calculateDriveRoute() {
        Log.d(GlobalContext.TAG, "calculateDriveRoute");

        NaviController naviController = new NaviController();
        naviController.setCalculateListenner(new NaviController.OnCalculateSuccessListenner() {

            @Override
            public void onCalculateSuccess(int[] ids) {
                Log.d(GlobalContext.TAG, "onCalculateSuccess");
                replaceFragmentToActivity(RouteChooseFragment.newInstance(ids));
            }
        });

        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        LatLonPoint destPoint =
                mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos()).getLatLonPoint();
        NaviLatLng destLocation = new NaviLatLng(destPoint.getLatitude(), destPoint.getLongitude());
        naviController.calculateDriveRoute(myLocation, destLocation);
    }


}
