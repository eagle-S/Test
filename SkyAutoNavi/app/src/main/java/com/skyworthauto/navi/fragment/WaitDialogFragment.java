package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyworthauto.navi.R;

public class WaitDialogFragment extends DialogFragment {

    public static WaitDialogFragment newInstance() {
        return new WaitDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.no_trans_dlg);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_progress_dlg, container);
        return v;
    }
}
