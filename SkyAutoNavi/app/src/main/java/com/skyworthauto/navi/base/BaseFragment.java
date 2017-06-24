package com.skyworthauto.navi.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.skyworthauto.navi.util.L;


public class BaseFragment extends Fragment implements IKeyEvent, AMap.OnCameraChangeListener {

    private static final String TAG = "BaseFragment";
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
        L.d(TAG, "replaceFragmentToActivity index=" + index);
        return index;
    }

    public int addFragmentToActivity(BaseFragment fragment) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.add(R.id.map_fragment_container, fragment);
        ft.addToBackStack(null);
        int index = ft.commit();
        L.d(TAG, "addFragmentToActivity index=" + index);
        return index;
    }

    public void popBackStack() {
        GlobalContext.getFragmentManager().popBackStack();
    }

    public int removeFragmentToActivity(BaseFragment fragment) {
        FragmentTransaction ft = GlobalContext.getFragmentManager().beginTransaction();
        ft.remove(fragment);
        int index = ft.commit();
        L.d(TAG, "removeFragmentToActivity index=" + index);
        return index;
    }

    @Override
    public void onAttach(Context context) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onCreate");
        setMapController(((MainActivity) getActivity()).getMapController());
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "getLayoutInflater");
        return super.getLayoutInflater(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onViewStateRestored");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onStart() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        L.d(TAG, getClass().getSimpleName() + ": " + this + " , " + "onDetach");
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
        L.d(TAG, "onKeyDown:" + keyCode);
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
