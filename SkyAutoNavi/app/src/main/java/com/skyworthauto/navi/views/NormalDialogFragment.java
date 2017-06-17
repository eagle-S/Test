package com.skyworthauto.navi.views;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.skyworthauto.navi.R;

public class NormalDialogFragment extends DialogFragment {

    public static NormalDialogFragment newInstance() {
        return new NormalDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_layout, container);
        return view;
    }

    public void setPositiveButtonListener() {

    }

    public void setNegativeButtonListener() {

    }
}
