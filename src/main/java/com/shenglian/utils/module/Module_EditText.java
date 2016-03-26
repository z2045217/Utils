package com.shenglian.utils.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * ps:ios
 * Created by admin on 2016/1/25.
 */
public class Module_EditText extends EditText implements View.OnFocusChangeListener ,View.OnKeyListener {

//
//    <koc.common.module.Module_EditText
//    android:id="@+id/etSearch"
//    android:layout_width="match_parent"
//    android:layout_height="35dp"
//    android:layout_centerInParent="true"
//    android:drawableLeft="@drawable/icon_search"
//    android:drawablePadding="5dp"
//    android:background="@drawable/shape_white_frame_radius"
//    android:layout_marginLeft="20dp"
//    android:layout_marginRight="20dp"
//    android:singleLine="true"
//    android:textColorHint="@color/ui_gray_02"
//    android:textColor="@color/ui_gray_14"
//    android:textSize="12sp"/>
    /**
     */
    private boolean isLeft = false;
    /**
     */
    private boolean pressSearch = false;
    /**
     */
    private OnSearchClickListener listener;
    private OnFocusListener focusListener;

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public void addOnFocusListener(OnFocusListener listener) {
        this.focusListener = listener;
    }

    public Module_EditText(Context context) {
        this(context, null);
        init();
    }

    public Module_EditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        init();
    }

    public Module_EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isLeft) { //
            super.onDraw(canvas);
        } else { //
            Drawable[] drawables = getCompoundDrawables();
            if (drawables != null) {
                Drawable drawableLeft = drawables[0];
                if (drawableLeft != null) {
                    if(TextUtils.isEmpty(getHint())){
                        setHint("搜索");
                    }
                    float textWidth = getPaint().measureText(getHint().toString());
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableWidth = drawableLeft.getIntrinsicWidth();
                    float bodyWidth = textWidth + drawableWidth + drawablePadding;
                    canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
                }
            }
            super.onDraw(canvas);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //
        if (!pressSearch && TextUtils.isEmpty(getText().toString())) {
            isLeft = hasFocus;
            focusListener.onFocusChanage(v,hasFocus);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        pressSearch = (keyCode == KeyEvent.KEYCODE_ENTER);
        if (pressSearch && listener != null) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            listener.onSearchClick(v);
        }
        return false;
    }

    public interface OnSearchClickListener {
        void onSearchClick(View view);
    }

    public interface OnFocusListener {
       void onFocusChanage(View v, boolean hasFocus);
    }


}
