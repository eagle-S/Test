package com.skyworthauto.navi.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;

import com.skyworthauto.navi.BaseFragment;
import com.skyworthauto.navi.GlobalContext;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.util.Constant;
import com.skyworthauto.navi.util.ResourceUtils;
import com.skyworthauto.navi.view.NormalDialog;

public class NormalDialogFragment extends DialogFragment {

    protected static final String TITLE = "title";
    protected static final String MESSAGE = "message";
    protected static final String POSITIVE_BUTTON_TEXT = "positiveButtonText";
    protected static final String NEGATIVE_BUTTON_TEXT = "negativeButtonText";
    protected static final String NEUTRAL_BUTTON_TEXT = "neutralButtonText";
    protected static final String ICON_RESOURCE = "iconResource";
    protected static final String CANCELABLE = "cancelable";
    protected static final String THEME = "theme";
    protected static final String HTML = "html";

    public void show(BaseFragment fragment, String tag) {
        setTargetFragment(fragment, -1);
        super.show(GlobalContext.getFragmentManager(), tag);
    }

    public static NormalDialogFragment newInstance() {
        return new NormalDialogFragment();
    }

    public NormalDialogFragment() {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
        }
        setArguments(args);
    }

    protected final NormalDialogFragment setArg(String key, boolean value) {
        getArguments().putBoolean(key, value);
        return this;
    }

    protected final NormalDialogFragment setArg(String key, String value) {
        getArguments().putString(key, value);
        return this;
    }

    protected final NormalDialogFragment setArg(String key, int value) {
        getArguments().putInt(key, value);
        return this;
    }

    protected final NormalDialogFragment setArg(String key, long value) {
        getArguments().putLong(key, value);
        return this;
    }

    public NormalDialogFragment setTitle(String title) {
        return setArg(TITLE, title);
    }

    public NormalDialogFragment setTitle(@StringRes int titleResourceId) {
        return setTitle(ResourceUtils.getString(titleResourceId));
    }

    public NormalDialogFragment setMessage(String message) {
        return setArg(MESSAGE, message);
    }

    public NormalDialogFragment setMessage(@StringRes int messageResourceId) {
        return setMessage(ResourceUtils.getString(messageResourceId));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        NormalDialog dialog = new NormalDialog(getActivity());
        String title = getArguments().getString(TITLE);
        if (title != null) {
            dialog.setTitle(title);
        }

        String message = getArguments().getString(MESSAGE);
        if (message != null) {
            dialog.setMessage(message);
        }

        dialog.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveButtonClick();
            }
        });
        dialog.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onNegativeButtonClick();
            }
        });
        return dialog;
    }

    protected void onPositiveButtonClick() {
        getTargetFragment().onActivityResult(Constant.REQUEST_CODE_FOR_ACK, Activity.RESULT_OK, null);
    }

    protected void onNegativeButtonClick() {
    }
}
