package com.skyworthauto.navi.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skyworthauto.navi.MainActivity;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.base.BaseFragment;

public class HomeSearchFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = "HomeSearchFragment";

    public static HomeSearchFragment newInstance() {
        HomeSearchFragment fragment = new HomeSearchFragment();
        return fragment;
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
    }

    private void initTitleView(View v) {
        TextView searchEdit = (TextView) v.findViewById(R.id.auto_search_home_title);
        searchEdit.setOnClickListener(this);

        v.findViewById(R.id.btn_search_back).setOnClickListener(this);
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
                replaceFragmentToActivity(DestSearchFragment.newInstance());
                break;
            case R.id.btn_search_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.auto_dest_home:
                break;
            case R.id.auto_dest_company:
                break;
            case R.id.auto_dest_favor:
                break;
            case R.id.auto_dest_hot_item_toilet:
                break;
            case R.id.auto_dest_hot_item_gas_station:
                break;
            case R.id.auto_dest_hot_item_parking_lot:
                break;
            case R.id.auto_dest_hot_item_car_wash:
                break;
            case R.id.auto_dest_hot_item_car_repair:
                break;
            case R.id.auto_dest_hot_item_more:
                break;
            default:
                break;
        }

    }
}
