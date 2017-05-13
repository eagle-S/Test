package com.skyworthauto.navi.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviPath;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RouteChooseFragment extends BaseFragment implements View.OnClickListener {

    public static final int DEFAULT_SELECTED_POS = 0;
    private int[] mIds;
    private List<RoutePath> mRoutePathList;
    private RouteChooseListView mListView;

    private RouteChooseListAdapter mRoutePathListAdapter;

    public static RouteChooseFragment newInstance(int[] ids) {
        Bundle bundle = new Bundle();
        bundle.putIntArray("ids", ids);

        RouteChooseFragment fragment = new RouteChooseFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mIds = args.getIntArray("ids");
        }

        mRoutePathList = getRoutePath();
    }

    private ArrayList<RoutePath> getRoutePath() {
        ArrayList<RoutePath> pathList = new ArrayList<>();
        AMapNavi aMapNavi = AMapNavi.getInstance(getActivity());
        HashMap<Integer, AMapNaviPath> paths = aMapNavi.getNaviPaths();

        for (int i = 0; i < mIds.length; i++) {
            AMapNaviPath path = paths.get(mIds[i]);
            if (path != null) {
                pathList.add(new RoutePath(mIds[i], path));
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

    private void initView(View v) {
        findViewById(v, R.id.auto_route_panel_back_btn).setOnClickListener(this);
        findViewById(v, R.id.btn_startnavi).setOnClickListener(this);

        RouteChooseListView listView = findViewById(v, R.id.dest_path_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRoutePathListAdapter.setSelectedPos(position);
                mRoutePathListAdapter.notifyDataSetChanged();

                RoutePath item = mRoutePathListAdapter.getItem(position);
                mMapController.setSelectedPathId(item.getId());
            }
        });

        mRoutePathListAdapter = new RouteChooseListAdapter();
        mRoutePathListAdapter.setAllData(mRoutePathList);
        mRoutePathListAdapter.setSelectedPos(DEFAULT_SELECTED_POS);
        listView.setAdapter(mRoutePathListAdapter);

        mMapController.setSelectedPathId(mRoutePathList.get(DEFAULT_SELECTED_POS).getId());

    }


    @Override
    public void onStart() {
        super.onStart();
        mMapController.cleanMap();
        mMapController.drawRoutes(mIds);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_route_panel_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_startnavi:
                replaceFragmentToActivity(NaviFragment.newInstance(getSelectedPathId()));
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
