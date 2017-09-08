package com.skyworthauto.navi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.maps.model.MyLocationStyle;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.map.MapController;
import com.skyworthauto.navi.util.L;

public class MapInteractiveView extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "MapInteractiveView";
    private MapController mMapController;

    private ImageView mTrafficToggle;
    private View mVisualModeBtn;
    private ImageButton mMapZoomInBtn;
    private ImageButton mMapZoomOutBtn;

    public MapInteractiveView(Context context) {
        super(context);
    }

    public MapInteractiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapInteractiveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTrafficToggle = (ImageView) findViewById(R.id.iv_auto_overview_tmc);
        mTrafficToggle.setOnClickListener(this);
        mVisualModeBtn = findViewById(R.id.fl_visual_mode_btn);
        mVisualModeBtn.setOnClickListener(this);
        mMapZoomInBtn = (ImageButton) findViewById(R.id.map_zoom_in);
        mMapZoomInBtn.setOnClickListener(this);
        mMapZoomOutBtn = (ImageButton) findViewById(R.id.map_zoom_out);
        mMapZoomOutBtn.setOnClickListener(this);
    }

    public void setMapController(MapController controller) {
        mMapController = controller;
    }

    public void showZoomLayout(boolean visiable) {
        findViewById(R.id.map_zoom_layout).setVisibility(visiable ? VISIBLE : GONE);
    }

    public void showVisualModeBtn(boolean visiable) {
        findViewById(R.id.fl_visual_mode_btn).setVisibility(visiable ? VISIBLE : GONE);
    }

    public void showTrafficLineBtn(boolean visiable) {
        mTrafficToggle.setVisibility(visiable ? VISIBLE : GONE);
    }

    private void setVisiable(int resId, boolean visiable) {
        findViewById(resId).setVisibility(visiable ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_auto_overview_tmc:
                changeTrafficLine();
                break;
            case R.id.fl_visual_mode_btn:
                changeVisualMode();
                break;
            case R.id.map_zoom_in:
                mMapController.zoomIn();
                break;
            case R.id.map_zoom_out:
                mMapController.zoomOut();
                break;
            default:
                break;
        }
    }

    protected void changeTrafficLine() {
        if (mMapController.isTrafficLine()) {
            mMapController.setTrafficLine(false);
            mTrafficToggle.setImageResource(R.drawable.auto_ic_roads_close);
        } else {
            mMapController.setTrafficLine(true);
            mTrafficToggle.setImageResource(R.drawable.auto_ic_roads_open);
        }
    }

    protected void changeVisualMode() {
        int curMode = mMapController.getNaviMode();
        L.d(TAG, "curMode=" + curMode);
        if (curMode == MyLocationStyle.LOCATION_TYPE_LOCATE) {
            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        } else if (curMode == MyLocationStyle.LOCATION_TYPE_FOLLOW) {
            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        } else if ((curMode == MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)) {
            mMapController.setNaviMode(MyLocationStyle.LOCATION_TYPE_LOCATE);
        }
    }
}
