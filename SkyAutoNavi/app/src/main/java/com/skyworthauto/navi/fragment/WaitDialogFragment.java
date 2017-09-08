package com.skyworthauto.navi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skyworthauto.navi.R;

public class WaitDialogFragment extends DialogFragment {

    private String mMessageStr;

    public static WaitDialogFragment newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        WaitDialogFragment fragment = new WaitDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.no_trans_dlg);
        mMessageStr = getArguments().getString("message");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.widget_progress_dlg, container);
        TextView message = (TextView) v.findViewById(R.id.msg);
        message.setText(mMessageStr);
        return v;
    }
}
