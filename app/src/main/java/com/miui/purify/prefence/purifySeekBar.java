package com.miui.purify.prefence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class purifySeekBar extends SeekBar implements OnSeekBarChangeListener {
    public static final int HINT_FIXED = 1;
    public static final int HINT_FOLLOW = 0;
    public purifyCoreSettings CoreSettings = new purifyCoreSettings();
    private OnSeekBarChangeListener mExternalListener;
    private PopupWindow mHint;
    private int mHintStyle;
    private TextView mHintTextView;
    private int mHintWidth;
    private OnSeekBarChangeListener mInternalListener;
    public int mMax;
    public int mMin;
    private View mPopupView;
    public int mProgress;
    private OnSeekBarHintProgressChangeListener mProgressChangeListener;
    public int mStep;
    public int mYOffset;

    public interface OnSeekBarHintProgressChangeListener {
        String onHintTextChanged(purifySeekBar miuiSeekBar, int i);

        void onMiuiSeekBarProgress(int i);
    }

    public purifySeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialization(context, attributeSet);
        this.CoreSettings.initialization(context, attributeSet, null);
    }

    private int getFollowPosition(int i) {
        return (int) (((float) (((getWidth() - getPaddingLeft()) - getPaddingRight()) * i)) / ((float) getMax()));
    }

    private int getHorizontalOffset(int i) {
        return (getFollowPosition(i) - (this.mPopupView.getMeasuredWidth() / 2)) + getHeight();
    }

    private int getVerticalOffset() {
        return -(getHeight() + this.mPopupView.getMeasuredHeight() + this.mYOffset);
    }

    private void hideHintPopup() {
        if (this.mHint.isShowing()) {
            this.mHint.dismiss();
        }
    }

    private void initialization(Context context, AttributeSet attributeSet) {
        setOnSeekBarChangeListener(this);
    }

    @SuppressLint("WrongConstant")
    private void initializationHintPopup() {
        String str = "";
        this.mPopupView = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(this.CoreSettings.LayoutToID("seekbar_hint_layout"), null);
        this.mPopupView.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        this.mHint = new PopupWindow(this.mPopupView, this.mHintWidth, -2, false);
        this.mHintTextView = (TextView) this.mPopupView.findViewById(this.CoreSettings.IDtoID("HintText"));
        this.mHintTextView.setText("" != null ? str : String.valueOf(getProgress()));
        this.mHint.setAnimationStyle(this.CoreSettings.StyleToID("Animation_PopupWindow_ActionMode"));
    }

    private int setSHintStyle(String str) {
        return (str == null || !str.equals("fixed")) ? 0 : 1;
    }

    private void showHintPopup() {
        Point fixedHintOffset;
        switch (this.mHintStyle) {
            case 0:
                fixedHintOffset = getFollowHintOffset();
                break;
            case 1:
                fixedHintOffset = getFixedHintOffset();
                break;
            default:
                fixedHintOffset = null;
                break;
        }
        this.mHint.showAtLocation(this, 0, 0, 0);
        this.mHint.update(this, fixedHintOffset.x, fixedHintOffset.y, -1, -1);
    }

    /* access modifiers changed from: protected */
    public Point getFixedHintOffset() {
        return new Point((getWidth() / 2) - this.mPopupView.getMeasuredWidth(), getVerticalOffset());
    }

    /* access modifiers changed from: protected */
    public Point getFollowHintOffset() {
        return new Point(getHorizontalOffset(getProgress()), getVerticalOffset());
    }

    public int getHintStyle() {
        return this.mHintStyle;
    }

    public int getHintWidth() {
        return this.mHintWidth;
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        String str = null;
        if (this.mProgressChangeListener != null) {
            str = this.mProgressChangeListener.onHintTextChanged(this, (getProgress() * this.mStep) + this.mMin);
        }
        if (this.mExternalListener != null) {
            this.mExternalListener.onProgressChanged(seekBar, i, z);
        }
        TextView textView = this.mHintTextView;
        if (str == null) {
            str = String.valueOf((this.mStep * i) + this.mMin);
        }
        textView.setText(str);
        if (this.mHintStyle == 0) {
            Point followHintOffset = getFollowHintOffset();
            this.mHint.update(this, followHintOffset.x, followHintOffset.y, -1, -1);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        if (this.mExternalListener != null) {
            this.mExternalListener.onStartTrackingTouch(seekBar);
        }
        showHintPopup();
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.mExternalListener != null) {
            this.mExternalListener.onStopTrackingTouch(seekBar);
        }
        this.mProgressChangeListener.onMiuiSeekBarProgress(getProgress());
        hideHintPopup();
    }

    public void setHintStyle(String str) {
        this.mHintStyle = setSHintStyle(str);
    }

    public void setHintView(View view, SeekBar seekBar) {
        initializationHintPopup();
    }

    public void setHintWidth(int i) {
        this.mHintWidth = i;
    }

    public void setOnProgressChangeListener(OnSeekBarHintProgressChangeListener onSeekBarHintProgressChangeListener) {
        this.mProgressChangeListener = onSeekBarHintProgressChangeListener;
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        if (this.mInternalListener == null) {
            this.mInternalListener = onSeekBarChangeListener;
            super.setOnSeekBarChangeListener(onSeekBarChangeListener);
            return;
        }
        this.mExternalListener = onSeekBarChangeListener;
    }
}
