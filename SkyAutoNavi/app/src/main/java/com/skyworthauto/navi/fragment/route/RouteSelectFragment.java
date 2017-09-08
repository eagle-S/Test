package com.skyworthauto.navi.fragment.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.HeaderAndFooterWrapper;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.fragment.navi.NaviFragment;
import com.skyworthauto.navi.fragment.navi.SimNaviFragment;
import com.skyworthauto.navi.fragment.search.BaseSearchFragment;
import com.skyworthauto.navi.fragment.search.MinorSearchFragment;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.DumpUtils;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.view.MapInteractiveView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteSelectFragment extends BaseFragment implements View.OnClickListener {

    public static final int DEFAULT_SELECTED_POS = 0;
    private static final String TAG = "RouteSelectFragment";
    private static final int REQUEST_CODE_STRATEGY = 100;
    private int[] mIds;
    private RouteSelectListView mListView;

    private RouteSelectListAdapter mRoutePathListAdapter;
    private MapInteractiveView mMapInteractiveView;
    private TextView mstrategyDes;
    private int mStrategyFlag;
    private NaviController mNaviController;
    //    private LatLonPoint mDestPoint;
    private View mRouteDetailLayout;
    private RecyclerView mRouteDetailListView;
    private RouteDetailListAdapter mRouteDetailListAdapter;
    private View mRightLayout;
    private HeaderAndFooterWrapper mWrappedAdapter;

    public static RouteSelectFragment newInstance(int[] ids) {
        Bundle bundle = new Bundle();
        bundle.putIntArray("ids", ids);
        //        bundle.putParcelable("destPoint", destPoint);

        RouteSelectFragment fragment = new RouteSelectFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mIds = args.getIntArray("ids");
            //            mDestPoint = args.getParcelable("destPoint");
        }

    }

    private ArrayList<RoutePath> getRoutePath(int[] ids) {
        ArrayList<RoutePath> pathList = new ArrayList<>();
        AMapNavi aMapNavi = AMapNavi.getInstance(getActivity());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();

        for (int i = 0; i < ids.length; i++) {
            AMapNaviPath path = paths.get(ids[i]);
            if (path != null) {
                pathList.add(new RoutePath(ids[i], path));
            }
        }

        return pathList;
    }

    @Override
    public int getLayoutId() {
        return R.layout.route_car_result_map_fragment;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_route_panel_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initTitle(rootView);
        initLeftLayout(rootView);
        initRightLayout(rootView);
        initRouteDetailView(rootView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initTitle(View v) {
        findViewById(v, R.id.auto_route_panel_back_btn).setOnClickListener(this);

        mstrategyDes = findViewById(v, R.id.auto_route_panel_prefer_setting_tv);
        mstrategyDes.setOnClickListener(this);
        mstrategyDes.setText(NaviConfig.getStrategyDescribe());
    }

    private void initLeftLayout(View v) {
        findViewById(v, R.id.btn_startnavi).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(v, R.id.dest_path_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRoutePathListAdapter = new RouteSelectListAdapter(getActivity());
        mRoutePathListAdapter.setAllData(getRoutePath(mIds));
        mRoutePathListAdapter.setSelectedPos(DEFAULT_SELECTED_POS);
        mRoutePathListAdapter.setOnItemClickListener(new MultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                L.d(TAG, "mRoutePathListAdapter.getSelectedPos()=" + mRoutePathListAdapter
                        .getSelectedPos() + "position==" + position);
                if (mRoutePathListAdapter.getSelectedPos() == position) {
                    showOrHideDetailView();
                } else {
                    mRoutePathListAdapter.setSelectedPos(position);
                    mRoutePathListAdapter.notifyDataSetChanged();
                    mMapController.setSelectedIndex(position);

                    updateDetailView(position);
                }
            }
        });

        recyclerView.setAdapter(mRoutePathListAdapter);
    }

    private void initRightLayout(View v) {
        mRightLayout = findViewById(v, R.id.right_layout);
        initMapInteractiveView(v);
        initWayView(v);
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(true);
        mMapInteractiveView.showVisualModeBtn(false);
        mMapInteractiveView.showZoomLayout(true);
    }

    private void initWayView(View v) {
        findViewById(v, R.id.route_along_search_open_btn).setOnClickListener(this);
    }

    private void initRouteDetailView(View v) {
        mRouteDetailLayout = findViewById(v, R.id.route_detail_id);
        mRouteDetailListView = findViewById(v, R.id.car_detail_list);
        mRouteDetailListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRouteDetailListAdapter = new RouteDetailListAdapter(getActivity());
        mWrappedAdapter = new HeaderAndFooterWrapper(mRouteDetailListAdapter);

        View headerView = getActivity().getLayoutInflater()
                .inflate(R.layout.car_detail_item_start, mRouteDetailListView, false);
        findViewById(headerView, R.id.start_sim_navi).setOnClickListener(this);
        mWrappedAdapter.addHeaderView(headerView);

        View footerView = getActivity().getLayoutInflater()
                .inflate(R.layout.car_detail_item_end, mRouteDetailListView, false);
        mWrappedAdapter.addFootView(footerView);

        mRouteDetailListView.setAdapter(mWrappedAdapter);

        updateDetailView(DEFAULT_SELECTED_POS);
    }

    public void onStrategyChanged() {
        mstrategyDes.setText(NaviConfig.getStrategyDescribe());

        reCalculateRoute();
    }

    private void reCalculateRoute() {
        calculateDriveRoute();
    }

    private void calculateDriveRoute() {
        L.d(TAG, "calculateDriveRoute");
        mNaviController = new NaviController();
        mNaviController.setCalculateListenner(new NaviController.OnCalculateSuccessListenner() {

            @Override
            public void onCalculateSuccess(int[] ids) {
                L.d(TAG, "onCalculateSuccess aaa");
                mIds = ids;
                updateList(ids);
            }

            @Override
            public void onCalculateFailure(int errorInfo) {

            }
        });

        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLastLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        mRoutePlanInfo.setStartPoint(myLocation);
        mNaviController.calculateDriveRoute(mRoutePlanInfo);
    }

    private void updateList(int[] ids) {
        mRoutePathListAdapter.setAllData(getRoutePath(ids));
        mRoutePathListAdapter.setSelectedPos(DEFAULT_SELECTED_POS);
        mRoutePathListAdapter.notifyDataSetChanged();

        mMapController.drawRoutes(ids);
        mMapController.setSelectedIndex(DEFAULT_SELECTED_POS);
    }

    private String getStrategyDescribe() {
        StringBuilder builder = new StringBuilder();
        if (NaviConfig.isAvoidCongestion()) {
            builder.append("躲避拥堵");
        }
        if (NaviConfig.isAvoidCost()) {
            builder.append("避免收费");
        }
        if (NaviConfig.isAvoidHighSpeed()) {
            builder.append("不走高速");
        }
        if (NaviConfig.isHighSpeed()) {
            builder.append("高速优先");
        }
        String describe = builder.toString();
        if (TextUtils.isEmpty(describe)) {
            describe = "路线偏好";
        }

        return describe;
    }


    private void updateDetailView(int position) {
        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        aMapNavi.selectRouteId(mIds[position]);

        List<AMapNaviGuide> guideList = aMapNavi.getNaviGuideList();

        for (AMapNaviGuide guide : guideList) {
            DumpUtils.dumpNaviGuide(guide);
        }

        mRouteDetailListAdapter.setAllData(guideList);
        mWrappedAdapter.notifyDataSetChanged();
    }

    private void showOrHideDetailView() {
        boolean isVisible = (mRouteDetailLayout.getVisibility() == View.VISIBLE);
        mRouteDetailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        mRightLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();
        mMapController.drawRoutes(mIds);
        mMapController.setSelectedIndex(DEFAULT_SELECTED_POS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_route_panel_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.start_sim_navi:
                replaceFragmentToActivity(
                        SimNaviFragment.newInstance(NaviType.EMULATOR, getSelectedPathId()));
                break;
            case R.id.btn_startnavi:
                replaceFragmentToActivity(
                        NaviFragment.newInstance(NaviType.GPS, getSelectedPathId()));
                break;
            case R.id.auto_route_panel_prefer_setting_tv:
                BaseFragment fragment = StrategySettingFragment.newInstance();
                fragment.setTargetFragment(this, REQUEST_CODE_STRATEGY);
                int indexAdd = replaceFragmentToActivity(fragment);
                L.d(TAG, "indexAdd=" + indexAdd);
                break;
            case R.id.route_along_search_open_btn:
                replaceFragmentToActivity(MinorSearchFragment
                                .newInstance(BaseSearchFragment.ACTION_SEARCH_PASSWAY_POI),
                        "search_padd_way");
                break;
            default:
                break;
        }

    }

    private int getSelectedPathId() {
        int pos = mRoutePathListAdapter.getSelectedPos();
        RoutePath path = mRoutePathListAdapter.getItem(pos);
        return path.getId();
    }

}
