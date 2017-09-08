package com.skyworthauto.navi.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skyworthauto.navi.BaseActivity;
import com.skyworthauto.navi.R;
import com.skyworthauto.navi.util.L;
import com.skyworthauto.navi.util.ResourceUtils;

import java.util.ArrayList;

public class NormalDialog extends Dialog {

    public static final String TAG = "NormalDialog";

    private static final int AUTO_HIDE_DELAY_MILLIS = 1000;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    private Resources mResources;

    protected Button mPositiveButton;
    private Button mNegativeButton;


    private final DialogParams mDialogParams;

    private final BaseActivity mActivity;

    Handler mHideHandler = new Handler();
    private SystemUiHider mSystemUiHider;

    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    public NormalDialog(Context context) {
        super(context, R.style.custom_dlg);
        mActivity = (BaseActivity) context;
        mDialogParams = new DialogParams(this);
        mResources = context.getResources();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.alert_dialog);
        setGravity(Gravity.CENTER);
        mDialogParams.apply();
    }

    public void setGravity(int gravity) {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = gravity;
        window.setAttributes(wlp);
    }

    private void setFullScreen() {
        L.d(TAG, "getWindow().getDecorView()=" + getWindow().getDecorView());
        mSystemUiHider =
                SystemUiHider.getInstance(mActivity, getWindow().getDecorView(), HIDER_FLAGS);
        mSystemUiHider.hide();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        L.d(TAG, "onWindowFocusChanged:" + hasFocus);
        if (hasFocus) {
            mSystemUiHider.hide();
        }
    }

    @Override
    public void show() {
        if (isContextInvalid() || isShowing()) {
            return;
        }

        super.show();

        setFullScreen();

        //Clear the not focusable flag from the window
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mNegativeButton.requestFocus();
    }

    @Override
    public void dismiss() {
        if (isContextInvalid() || !isShowing()) {
            return;
        }

        super.dismiss();
    }

    private boolean isContextInvalid() {
        return mActivity == null || mActivity.isFinishing();
    }

    public void setTitle(CharSequence title) {
        mDialogParams.mTitleText = title;
    }

    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }

    @Override
    public void setContentView(View contentView) {
        if (!mDialogParams.mIsListContent) {
            mDialogParams.mContentView = contentView;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater inflater = getLayoutInflater();
        View contentView = inflater.inflate(layoutResID, null);
        setContentView(contentView);
    }

    public void setContentViewAlign(int contentAlign) {
        mDialogParams.mContentAlign = contentAlign;
    }

    public void setMessage(int messageId) {
        setMessage(getContext().getString(messageId));
    }

    public void setMessage(CharSequence message) {
        mDialogParams.mMessageText = message;
    }

    public void setPositiveButton(int textId, DialogInterface.OnClickListener onClickListener) {
        setButton(mResources.getString(textId), onClickListener, DialogInterface.BUTTON_POSITIVE);
    }

    public void setPositiveButton(CharSequence text,
            DialogInterface.OnClickListener onClickListener) {
        setButton(text, onClickListener, DialogInterface.BUTTON_POSITIVE);
    }

    public void setNegativeButton(int textId, DialogInterface.OnClickListener onClickListener) {
        setButton(mResources.getString(textId), onClickListener, DialogInterface.BUTTON_NEGATIVE);
    }

    public void setNegativeButton(CharSequence text,
            DialogInterface.OnClickListener onClickListener) {
        setButton(text, onClickListener, DialogInterface.BUTTON_NEGATIVE);
    }

    private void setButton(CharSequence text, final OnClickListener onClickListener,
            final int which) {
        mDialogParams.mButtonParams.add(new SetButtonParam(text, onClickListener, which));
    }

    public class DialogParams {

        public ArrayList<SetButtonParam> mButtonParams = new ArrayList<SetButtonParam>();
        public CharSequence mTitleText;
        public CharSequence mMessageText;
        public View mContentView;
        public boolean mIsListContent = false;
        public int mContentAlign = RelativeLayout.CENTER_HORIZONTAL;

        private NormalDialog mDialog;

        public DialogParams(NormalDialog dialog) {
            mDialog = dialog;
        }

        private void apply() {
            TextView title = (TextView) mDialog.findViewById(R.id.alertTitle);
            if (mTitleText != null) {
                title.setText(mTitleText);
            } else {
                title.setVisibility(View.GONE);
            }

            if (mMessageText != null) {
                TextView message = (TextView) mDialog.findViewById(R.id.message);
                message.setVisibility(View.VISIBLE);
                message.setText(mMessageText);

                if (mTitleText == null) {
                    message.setTextColor(ResourceUtils.getColor(R.color.auto_color_212125));
                    message.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            ResourceUtils.getDimenPx(R.dimen.auto_font_size_30));
                }
            }


            FrameLayout contentLayout = (FrameLayout) mDialog.findViewById(R.id.custom);
            if (mContentAlign != RelativeLayout.CENTER_HORIZONTAL) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) contentLayout.getLayoutParams();
                layoutParams.addRule(mContentAlign);
                contentLayout.setLayoutParams(layoutParams);
            }
            if (mContentView != null) {
                FrameLayout.LayoutParams layoutParams =
                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT);
                contentLayout.addView(mContentView, layoutParams);
            }

            if (hasButton()) {
                mPositiveButton = (Button) mDialog.findViewById(R.id.confirm);
                mNegativeButton = (Button) mDialog.findViewById(R.id.cancel);
                LinearLayout buttonBarLayout =
                        (LinearLayout) mDialog.findViewById(R.id.buttonPanel);
                if (buttonBarLayout.getVisibility() != View.VISIBLE) {
                    buttonBarLayout.setVisibility(View.VISIBLE);
                }

                setButtons();
            }
        }

        private boolean hasButton() {
            return !mButtonParams.isEmpty();
        }

        private void setButtons() {
            for (SetButtonParam param : mButtonParams) {
                setButton(param.mText, param.mOnClickListener, param.mWhich);
            }
            mButtonParams.clear();
        }

        private void setButton(CharSequence text, final OnClickListener onClickListener,
                final int which) {
            Button targetBtn = getTargetBtn(which);
            targetBtn.setVisibility(View.VISIBLE);
            targetBtn.setText(text);
            targetBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onClickListener != null) {
                        onClickListener.onClick(NormalDialog.this, which);
                    }
                }
            });
        }

        private Button getTargetBtn(int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    return mPositiveButton;
                case DialogInterface.BUTTON_NEGATIVE:
                    return mNegativeButton;
                default:
                    return null;
            }
        }
    }

    private static class SetButtonParam {
        private CharSequence mText;
        private OnClickListener mOnClickListener;
        private int mWhich;

        public SetButtonParam(CharSequence text, OnClickListener onClickListener, int which) {
            this.mText = text;
            this.mOnClickListener = onClickListener;
            this.mWhich = which;
        }
    }
}

