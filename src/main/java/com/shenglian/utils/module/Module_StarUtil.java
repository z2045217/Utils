package com.shenglian.utils.module;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;

/**
 * Created by anzhuo on 2015/4/22.
 */
public class Module_StarUtil extends IconTextView {

    private String strIcon = "{fa-star}";
    private String strIcon_Null = "{fa-star-o}";
    private String strIcon_Half = "{fa-star-half-o}";

    private StringBuffer str;

    public Module_StarUtil(Context context) {
        super(context);
        init();
    }

    public Module_StarUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Module_StarUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        String strText = this.getText().toString();
    }


    public String getStr(double point) {
        str = new StringBuffer();
        for (int i = 1; i <= point; i++) {
            str.append(strIcon + " ");
        }
        if (point % 1 != 0) {
            str.append(strIcon_Half + " ");
        }
        for (int i = 1; i <= 5 - (point % 1 == 0 ? point : (int) point + 1); i++) {
            str.append(strIcon_Null + " ");
        }
        return str.toString();
    }

}
