package com.skyworthauto.navi.fragment.dest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.fragment.route.RouteSelectFragment;
import com.skyworthauto.navi.fragment.search.SearchResult;
import com.skyworthauto.navi.fragment.search.BaseSearchFragment;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.view.MapInteractiveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class DestSelectFragment extends BaseSearchFragment {

    public static final String KEYWORD = "keyword";
    public static final String CUR_PAGE = "curPage";
    public static final String PAGE_LIST = "pageList";
    private static final String TAG = "DestSelectFragment";
    private String mKeyword;
    private int mCurPage;
    private ArrayList<PoiItem> mResult;
    private DestResultAdapter mDestResultAdapter;
    private NaviController mNaviController;
    private PoiDetailViewHelper mPoiDetailViewHelper;
    private RecyclerView mDestResultListView;
    private MapInteractiveView mMapInteractiveView;

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
        mSearchAction = args.getInt(TYPE, ACTION_SEARCH_DEST);
        mKeyword = args.getString(KEYWORD);
        mCurPage = args.getInt(CUR_PAGE);
        mResult = args.getParcelableArrayList(PAGE_LIST);

        mNaviController = new NaviController();
        mNaviController.setCalculateListenner(new NaviController.OnCalculateSuccessListenner() {

            @Override
            public void onCalculateSuccess(int[] ids) {
                L.d(TAG, "onCalculateSuccess aaa");
                //                LatLonPoint destPoint =
                //                        mDestResultAdapter.getItem(mDestResultAdapter
                // .getSelectedPos())
                //                                .getLatLonPoint();
                //                mRoutePlanInfo.setEndPoint(
                //                        new NaviLatLng(destPoint.getLatitude(), destPoint
                // .getLongitude()));
                replaceFragmentToActivity(RouteSelectFragment.newInstance(ids));
            }

            @Override
            public void onCalculateFailure(int errorInfo) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_dest_result_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_search_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initViews(rootView);
        addMarks(mResult);
    }

    private void initViews(View v) {
        TextView title = findViewById(v, R.id.auto_search_keyword);
        title.setText(mKeyword);

        initMapInteractiveView(v);

        initSearchAction(v);
        initListView(v);
        initDetailView(v);
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(false);
        mMapInteractiveView.showVisualModeBtn(false);
        mMapInteractiveView.showZoomLayout(true);
    }

    private void initDetailView(View v) {
        View detailViewRoot = findViewById(v, R.id.auto_poi_detail_root_view);
        mPoiDetailViewHelper = new PoiDetailViewHelper(detailViewRoot, mSearchAction);
    }

    private void initListView(View v) {
        mDestResultListView = findViewById(v, R.id.dest_listview);
        mDestResultListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDestResultAdapter = new DestResultAdapter(getActivity());
        mDestResultAdapter.setOnItemClickListener(new MultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                if (position < 0) {
                    onHeaderItemClick();
                    return;
                }

                if (mDestResultAdapter.getSelectedPos() == position) {
                    mPoiDetailViewHelper.updateUI(mDestResultAdapter.getItem(position));
                    showDetailView();
                } else {
                    mDestResultAdapter.setSelectedPos(position);
                    mDestResultAdapter.notifyDataSetChanged();

                    mMapController.onMarkerClick(position);
                }
            }
        });

        mDestResultAdapter.setAllData(mResult);
        mDestResultListView.setAdapter(mDestResultAdapter);
    }

    private void showDetailView() {
        mPoiDetailViewHelper.show();
        mPoiDetailViewHelper.requestFocus();
        mDestResultListView.setVisibility(View.GONE);
    }

    private void showDestList() {
        mPoiDetailViewHelper.hide();
        mDestResultListView.setVisibility(View.VISIBLE);
    }

    private void initSearchAction(View v) {
        ImageView naviImage = findViewById(v, R.id.search_result_navi_image);
        TextView naviText = findViewById(v, R.id.search_result_navi_text);

        switch (mSearchAction) {
            case ACTION_SEARCH_HOME:
                naviImage.setImageResource(R.drawable.auto_ic_button_home_normal);
                naviText.setText(R.string.common_add_home_btn_text);
                break;
            case ACTION_SEARCH_COMPANY:
                naviImage.setImageResource(R.drawable.auto_ic_button_company_normal);
                naviText.setText(R.string.common_add_company_btn_text);
                break;
            case ACTION_SEARCH_PASSWAY_POI:
                naviImage.setImageResource(R.drawable.auto_ic_button_home_normal);
                naviText.setText(R.string.search_result_map_add_waypoi);
                break;
            case ACTION_SEARCH_DEST:
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
        mMapController.onMarkerClick(0);
    }

    @OnClick(R.id.auto_search_back_btn)
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public boolean onBackPressed() {
        if (mPoiDetailViewHelper.isShow()) {
            showDestList();
            return true;
        }
        return super.onBackPressed();
    }

    @OnClick(R.id.search_result_navi)
    public void onNaviBtnClick() {
        switch (mSearchAction) {
            case ACTION_SEARCH_HOME:
                LatLonPoint destPoint =
                        mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos())
                                .getLatLonPoint();
                FavoritePoiManager.saveHomeAddress(destPoint);
                GlobalContext.getFragmentManager().popBackStackImmediate("setting_home",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case ACTION_SEARCH_COMPANY:
                LatLonPoint point = mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos())
                        .getLatLonPoint();
                FavoritePoiManager.saveCompanyAddress(point);
                GlobalContext.getFragmentManager().popBackStackImmediate("setting_company",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case ACTION_SEARCH_PASSWAY_POI:
                setPassWayPoi();
                break;
            case ACTION_SEARCH_DEST:
            default:
                calculateDriveRoute();
                break;
        }
    }

    private void setPassWayPoi() {
        LatLonPoint wayPoint =
                mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos()).getLatLonPoint();
        NaviLatLng wayLocation = new NaviLatLng(wayPoint.getLatitude(), wayPoint.getLongitude());
        mRoutePlanInfo.addWayPoint(wayLocation);

        GlobalContext.getFragmentManager()
                .popBackStack("search_padd_way", FragmentManager.POP_BACK_STACK_INCLUSIVE);


        mNaviController.calculateDriveRoute(mRoutePlanInfo);
    }


    private void calculateDriveRoute() {
        L.d(TAG, "calculateDriveRoute");
        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLastLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        LatLonPoint destPoint =
                mDestResultAdapter.getItem(mDestResultAdapter.getSelectedPos()).getLatLonPoint();
        NaviLatLng destLocation = new NaviLatLng(destPoint.getLatitude(), destPoint.getLongitude());
        mRoutePlanInfo.setStartPoint(myLocation);
        mRoutePlanInfo.setEndPoint(destLocation);
        mNaviController.calculateDriveRoute(mRoutePlanInfo);
    }

}
