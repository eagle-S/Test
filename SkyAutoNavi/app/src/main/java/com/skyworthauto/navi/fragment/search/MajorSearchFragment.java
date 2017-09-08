package com.skyworthauto.navi.fragment.search;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.HeaderAndFooterWrapper;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.fragment.dest.FavoritePoiManager;
import com.skyworthauto.navi.fragment.route.RouteSelectFragment;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.fragment.NormalDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MajorSearchFragment extends BaseSearchFragment implements View.OnClickListener {


    private static final String TAG = "MainSearchFragment";
    private DestHistoryListAdapter mHistoryListAdapter;
    private NaviController mNaviController;

    @BindView(R.id.auto_destination_history)
    RecyclerView mHistorylistView;
    @BindView(R.id.auto_destination_no_history)
    View mSearchHintView;

    public static MajorSearchFragment newInstance() {
        MajorSearchFragment fragment = new MajorSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNaviController = new NaviController();
        mNaviController.setCalculateListenner(new NaviController.OnCalculateSuccessListenner() {

            @Override
            public void onCalculateSuccess(int[] ids) {
                L.d(TAG, "onCalculateSuccess aaa");
                replaceFragmentToActivity(RouteSelectFragment.newInstance(ids));
            }

            @Override
            public void onCalculateFailure(int errorInfo) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_home_layout;
    }

    @Override
    public int getFirstFocusViewId() {
        return R.id.auto_search_back_btn;
    }

    @Override
    public void init(View rootView, Bundle savedInstanceState) {
        initTitleView(rootView);
        initHotDestView(rootView);
        initDestinationHistory(rootView);
    }

    private void initDestinationHistory(View v) {
        mHistorylistView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mHistoryListAdapter = new DestHistoryListAdapter(getActivity());
        mHistoryListAdapter.setOnItemClickListener(new MultiTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                Tip tip = (Tip) mHistoryListAdapter.getItem(position).getData();
                calculateDriveRoute(tip.getPoint());
            }
        });

        HeaderAndFooterWrapper wrapperAdapter = new HeaderAndFooterWrapper(mHistoryListAdapter);
        wrapperAdapter.addFootView(createFootView());

        mHistorylistView.setAdapter(wrapperAdapter);

        List<SearchHistoryData> list = SearchHistoryManager.getDestHistoryList();
        updateList(list);
    }

    private void updateList(List<SearchHistoryData> list) {
        boolean isListEmpty = (list == null) ? true : list.isEmpty();

        if (isListEmpty) {
            mSearchHintView.setVisibility(View.VISIBLE);
            mHistorylistView.setVisibility(View.GONE);
        } else {
            mSearchHintView.setVisibility(View.GONE);
            mHistorylistView.setVisibility(View.VISIBLE);

            mHistoryListAdapter.setAllData(list);
            mHistoryListAdapter.notifyDataSetChanged();
        }
    }

    private View createFootView() {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.search_del_history_footer, mHistorylistView, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        return view;
    }

    private void showDeleteDialog() {
        NormalDialogFragment fragment = NormalDialogFragment.newInstance();
        fragment.setMessage(R.string.auto_search_dest_dialog_clear_all);
        fragment.show(this, "confirm_dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUEST_CODE_FOR_ACK) {
            if (resultCode == Activity.RESULT_OK) {
                SearchHistoryManager.clear();
                updateList(null);
            }
        }
    }


    private void initTitleView(View v) {
    }

    private void initHotDestView(View v) {

        initHotPointItem(v, R.id.auto_dest_hot_item_toilet, R.drawable.around_icon_toilet,
                R.string.auto_dest_logword_toilet);

        initHotPointItem(v, R.id.auto_dest_hot_item_gas_station, R.drawable.around_icon_gas_station,
                R.string.auto_dest_logword_gas_station);

        initHotPointItem(v, R.id.auto_dest_hot_item_toilet, R.drawable.around_icon_toilet,
                R.string.auto_dest_logword_toilet);

        initHotPointItem(v, R.id.auto_dest_hot_item_parking_lot, R.drawable.around_icon_parking_lot,
                R.string.auto_dest_logword_parking_lot);

        initHotPointItem(v, R.id.auto_dest_hot_item_car_wash, R.drawable.around_icon_car_wash,
                R.string.auto_dest_logword_car_washing);

        initHotPointItem(v, R.id.auto_dest_hot_item_car_repair, R.drawable.around_icon_car_repair,
                R.string.auto_dest_logword_car_repair);

        initHotPointItem(v, R.id.auto_dest_hot_item_more, R.drawable.around_icon_more,
                R.string.more);
    }

    private void initHotPointItem(View v, int itemId, int iconId, int nameId) {
        View item = v.findViewById(itemId);
        item.setOnClickListener(this);

        ImageView icon = (ImageView) item.findViewById(R.id.auto_dest_hot_item_iv);
        icon.setImageResource(iconId);

        TextView name = (TextView) item.findViewById(R.id.auto_dest_hot_item_tv);
        name.setText(nameId);
    }

    @OnClick(R.id.auto_search_home_title)
    public void goToMinorSearch() {
        replaceFragmentToActivity(
                MinorSearchFragment.newInstance(MinorSearchFragment.ACTION_SEARCH_DEST));
    }

    @OnClick(R.id.auto_search_back_btn)
    public void goBack() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.auto_dest_home)
    public void driveHome() {
        LatLonPoint homePoint = FavoritePoiManager.getHomeAddress();
        if (homePoint == null) {
            replaceFragmentToActivity(
                    MinorSearchFragment.newInstance(MinorSearchFragment.ACTION_SEARCH_HOME),
                    "setting_home");
        } else {
            calculateDriveRoute(homePoint);
        }
    }

    @OnClick(R.id.auto_dest_company)
    public void driveCompany() {
        LatLonPoint companyPoint = FavoritePoiManager.getCompanyAddress();
        if (companyPoint == null) {
            replaceFragmentToActivity(MinorSearchFragment
                            .newInstance(MinorSearchFragment.ACTION_SEARCH_COMPANY),
                    "setting_company");
        } else {
            calculateDriveRoute(companyPoint);
        }
    }

    @OnClick(R.id.auto_dest_favor)
    public void goToFavor() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_dest_hot_item_toilet:
                searchAround("公共厕所");
                break;
            case R.id.auto_dest_hot_item_gas_station:
                searchAround("加油站");
                break;
            case R.id.auto_dest_hot_item_parking_lot:
                searchAround("停车场");
                break;
            case R.id.auto_dest_hot_item_car_wash:
                searchAround("洗车场");
                break;
            case R.id.auto_dest_hot_item_car_repair:
                searchAround("汽车维修");
                break;
            case R.id.auto_dest_hot_item_more:
                replaceFragmentToActivity(SearchMoreFragment.newInstance());
                break;
            default:
                break;
        }

    }

    private void calculateDriveRoute(LatLonPoint point) {
        L.d(TAG, "calculateDriveRoute");
        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLastLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        NaviLatLng destLocation = new NaviLatLng(point.getLatitude(), point.getLongitude());
        mRoutePlanInfo.setStartPoint(myLocation);
        mRoutePlanInfo.setEndPoint(destLocation);
        mNaviController.calculateDriveRoute(mRoutePlanInfo);
    }
}
