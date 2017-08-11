package com.skyworthauto.navi.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.common.adapter.rv.HeaderAndFooterWrapper;
import com.skyworthauto.navi.common.adapter.rv.MultiTypeAdapter;
import com.skyworthauto.navi.common.adapter.rv.ViewHolder;
import com.skyworthauto.navi.map.NaviController;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.ui.views.NormalDialogFragment;

import java.util.List;

public class MajorSearchFragment extends BaseSearchFragment implements View.OnClickListener {


    private static final String TAG = "MainSearchFragment";
    private DestHistoryListAdapter mHistoryListAdapter;
    private NaviController mNaviController;
    private RecyclerView mHistorylistView;
    private View mSearchHintView;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_home_layout, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        initTitleView(v);
        initHotDestView(v);
        initDestinationHistory(v);
    }

    private void initDestinationHistory(View v) {
        mHistorylistView = (RecyclerView) v.findViewById(R.id.auto_destination_history);
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

        mSearchHintView = v.findViewById(R.id.auto_destination_no_history);
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
        TextView searchEdit = (TextView) v.findViewById(R.id.auto_search_home_title);
        searchEdit.setOnClickListener(this);

        v.findViewById(R.id.auto_search_back_btn).setOnClickListener(this);
        v.findViewById(R.id.auto_search_back_btn).requestFocus();
    }

    private void initHotDestView(View v) {
        v.findViewById(R.id.auto_dest_home).setOnClickListener(this);
        v.findViewById(R.id.auto_dest_company).setOnClickListener(this);
        v.findViewById(R.id.auto_dest_favor).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auto_search_home_title:
                replaceFragmentToActivity(
                        MinorSearchFragment.newInstance(MinorSearchFragment.ACTION_SEARCH_DEST));
                break;
            case R.id.auto_search_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.auto_dest_home:
                LatLonPoint homePoint = FavoritePoiManager.getHomeAddress();
                if (homePoint == null) {
                    replaceFragmentToActivity(
                            MinorSearchFragment.newInstance(MinorSearchFragment.ACTION_SEARCH_HOME),
                            "setting_home");
                } else {
                    calculateDriveRoute(homePoint);
                }
                break;
            case R.id.auto_dest_company:
                LatLonPoint companyPoint = FavoritePoiManager.getCompanyAddress();
                if (companyPoint == null) {
                    replaceFragmentToActivity(MinorSearchFragment
                                    .newInstance(MinorSearchFragment.ACTION_SEARCH_COMPANY),
                            "setting_company");
                } else {
                    calculateDriveRoute(companyPoint);
                }
                break;
            case R.id.auto_dest_favor:
                break;
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

    private boolean goToHome() {
        return FavoritePoiManager.getHomeAddress() != null;
    }

    private boolean goToCompany() {
        return FavoritePoiManager.getCompanyAddress() != null;
    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (position >= mHistoryListAdapter.getCount()) {
//            showDeleteDialog();
//        } else {
//            Tip tip = (Tip) mHistoryListAdapter.getItem(position).getData();
//            calculateDriveRoute(tip.getPoint());
//        }
//    }

    private void calculateDriveRoute(LatLonPoint point) {
        L.d(TAG, "calculateDriveRoute");
        LatLonPoint myLatLonPoint = mMapController.getMyLocation().getLocation();
        NaviLatLng myLocation =
                new NaviLatLng(myLatLonPoint.getLatitude(), myLatLonPoint.getLongitude());
        NaviLatLng destLocation = new NaviLatLng(point.getLatitude(), point.getLongitude());
        mRoutePlanInfo.setStartPoint(myLocation);
        mRoutePlanInfo.setEndPoint(destLocation);
        mNaviController.calculateDriveRoute(mRoutePlanInfo);
    }
}
