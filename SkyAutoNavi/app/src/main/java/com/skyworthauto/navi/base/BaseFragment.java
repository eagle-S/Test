package com.skyworthauto.navi.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CameraPosition;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.MainActivity;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.map.MapController;


public class BaseFragment extends Fragment implements IKeyEvent, AMap.OnCameraChangeListener {

    protected MapController mMapController;
    private View mCurrentFocus;

    private void setMapController(MapController mapController) {
        mMapController = mapController;
        mapController.setOnCameraChangeListener(this);
    }

    public int replaceFragmentToActivity(BaseFragment fragment) {
        return replaceFragmentToActivity(fragment, null);
    }

    public int replaceFragmentToActivity(BaseFragment fragment, String name) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.replace(R.id.map_fragment_container, fragment);
        ft.addToBackStack(name);
        int index = ft.commit();
        Log.d(GlobalContext.TAG, "replaceFragmentToActivity index=" + index);
        return index;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMapController(((MainActivity) getActivity()).getMapController());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected <V extends View> V findViewById(View v, int id) {
        return (V) v.findViewById(id);
    }

    @Override
    public boolean onBackPressed() {
        if (!isRootFragment()) {
            GlobalContext.getFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("yqwkey", "onKeyDown:" + keyCode);
        //
        //        Focus action = getCurrentFocusAction();
        //
        //        switch (keyCode) {
        //            case KeyEvent.KEYCODE_DPAD_DOWN_LEFT:
        //                action.focusLeftView();
        //                break;
        //            case KeyEvent.KEYCODE_DPAD_RIGHT:
        //                action.focusRightView();
        //                break;
        //            default:
        //                break;
        //        }
        //        updateCurrentFocus();

        return false;
    }

    private View findNextFocus() {
        mCurrentFocus = getView().findFocus();
        //        View nextView = findNextFocus(mCurrentFocus);
        return mCurrentFocus.focusSearch(View.FOCUS_RIGHT);

    }

    private View findNextFocus(View currentFocus) {

        return null;
    }

    private View findPreFocus() {
        mCurrentFocus = getView().findFocus();
        return mCurrentFocus.focusSearch(View.FOCUS_RIGHT);
    }

    protected boolean isRootFragment() {
        return false;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }
}
