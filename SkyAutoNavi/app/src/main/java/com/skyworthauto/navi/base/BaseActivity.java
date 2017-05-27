package com.skyworthauto.navi.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.util.TTSController;

import java.util.List;


public class BaseActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";

    protected AMapNaviView mAMapNaviView;
    protected AMapNavi mAMapNavi;
    protected TTSController mTtsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        GlobalContext.addActivity(this);
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();

        //
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(mTtsManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAMapNaviView.onPause();

        //        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
        mTtsManager.stopSpeaking();
        //
        //        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
        //        mAMapNavi.stopNavi();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAMapNaviView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalContext.removeActivity(this);

        mAMapNaviView.onDestroy();
        //since 1.6.0 不再在naviview destroy的时候自动执行AMapNavi.stopNavi();请自行执行
        mAMapNavi.stopNavi();
        mAMapNavi.destroy();

        mTtsManager.destroy();
    }

    @Override
    public void onBackPressed() {
        if (!handleBackPress()) {
            super.onBackPressed();
        }
    }

    public boolean handleBackPress() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            return false;
        }

        for (Fragment fragment : fragmentList) {
            if (isFragmentVisible(fragment)) {
                return ((BaseFragment) fragment).onBackPressed();
            }
        }

        return false;
    }


    public boolean isFragmentVisible(Fragment fragment) {
        boolean isFragmentVisible =
                fragment != null && fragment.isVisible() && fragment.getUserVisibleHint();
        Log.d(TAG, "Fragment=" + fragment + ", isFragmentVisible=" + isFragmentVisible);
        return isFragmentVisible;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (handleKeyDown(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean handleKeyDown(int keyCode, KeyEvent event) {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList == null) {
            return false;
        }

        for (Fragment fragment : fragmentList) {
            if (isFragmentVisible(fragment)) {
                return ((BaseFragment) fragment).onKeyDown(keyCode, event);
            }
        }

        return false;
    }
}
