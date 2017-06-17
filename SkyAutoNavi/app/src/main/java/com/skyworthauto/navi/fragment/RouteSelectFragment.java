package com.skyworthauto.navi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.NaviConfig;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.DumpUtils;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.views.MapInteractiveView;

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
    private LatLonPoint mDestPoint;
    private View mRouteDetailLayout;
    private ListView mRouteDetailListView;
    private RouteDetailListAdapter mRouteDetailListAdapter;

    public static RouteSelectFragment newInstance(int[] ids, LatLonPoint destPoint) {
        Bundle bundle = new Bundle();
        bundle.putIntArray("ids", ids);
        bundle.putParcelable("destPoint", destPoint);

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
            mDestPoint = args.getParcelable("destPoint");
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_car_result_map_fragment, container, false);
        initView(v);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView(View v) {
        initTitle(v);
        initMapInteractiveView(v);
        initRouteListView(v);
        initWayView(v);
        initRouteDetailView(v);
    }

    private void initRouteDetailView(View v) {
        mRouteDetailLayout = findViewById(v, R.id.route_detail_id);
        mRouteDetailListView = findViewById(v, R.id.car_detail_List);
        mRouteDetailListAdapter = new RouteDetailListAdapter(getActivity());
        mRouteDetailListView.setAdapter(mRouteDetailListAdapter);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.car_detail_item_start, null);
        findViewById(headerView, R.id.start_sim_navi).setOnClickListener(this);
        mRouteDetailListView.addHeaderView(headerView);
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.car_detail_item_end, null);
        mRouteDetailListView.addFooterView(footerView);
        updateDetailView(DEFAULT_SELECTED_POS);
    }

    private void initWayView(View v) {
        findViewById(v, R.id.route_along_search_open_btn).setOnClickListener(this);
    }

    private void initTitle(View v) {
        findViewById(v, R.id.auto_route_panel_back_btn).setOnClickListener(this);

        mstrategyDes = findViewById(v, R.id.auto_route_panel_prefer_setting_tv);
        mstrategyDes.setOnClickListener(this);
        mstrategyDes.setText(NaviConfig.getStrategyDescribe());
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
        });

        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        NaviLatLng destLocation =
                new NaviLatLng(mDestPoint.getLatitude(), mDestPoint.getLongitude());
        mNaviController.calculateDriveRoute(myLocation, destLocation);
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
        if (NaviConfig.isAvoidHighspeed()) {
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

    private void initRouteListView(View v) {
        findViewById(v, R.id.btn_startnavi).setOnClickListener(this);

        RouteSelectListView listView = findViewById(v, R.id.dest_path_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        mRoutePathListAdapter = new RouteSelectListAdapter(getActivity());
        mRoutePathListAdapter.setAllData(getRoutePath(mIds));
        mRoutePathListAdapter.setSelectedPos(DEFAULT_SELECTED_POS);
        listView.setAdapter(mRoutePathListAdapter);
    }

    private void updateDetailView(int position) {
        AMapNavi aMapNavi = AMapNavi.getInstance(GlobalContext.getContext());
        aMapNavi.selectRouteId(mIds[position]);

        List<AMapNaviGuide> guideList = aMapNavi.getNaviGuideList();

        for (AMapNaviGuide guide : guideList) {
            DumpUtils.dumpNaviGuide(guide);
        }

        mRouteDetailListAdapter.setAllData(guideList);
        mRouteDetailListAdapter.notifyDataSetChanged();
    }

    private void showOrHideDetailView() {
        boolean isVisible = (mRouteDetailLayout.getVisibility() == View.VISIBLE);
        mRouteDetailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private void initMapInteractiveView(View v) {
        mMapInteractiveView = (MapInteractiveView) v.findViewById(R.id.map_interactive_view);
        mMapInteractiveView.setMapController(mMapController);
        mMapInteractiveView.showTrafficLineBtn(true);
        mMapInteractiveView.showVisualModeBtn(false);
        mMapInteractiveView.showZoomLayout(true);
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
                replaceFragmentToActivity(NaviFragment.newInstance(NaviType.EMULATOR, getSelectedPathId()));
                break;
            case R.id.btn_startnavi:
                replaceFragmentToActivity(NaviFragment.newInstance(NaviType.GPS, getSelectedPathId()));
                break;
            case R.id.auto_route_panel_prefer_setting_tv:
                BaseFragment fragment = StrategySettingFragment.newInstance();
                fragment.setTargetFragment(this, REQUEST_CODE_STRATEGY);
                int indexAdd = addFragmentToActivity(fragment);
                L.d(TAG, "indexAdd=" + indexAdd);
                break;
            case R.id.route_along_search_open_btn:
                replaceFragmentToActivity(
                        MinorSearchFragment.newInstance(BaseSearchFragment.ACTION_SEARCH_PASS_POI));
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
