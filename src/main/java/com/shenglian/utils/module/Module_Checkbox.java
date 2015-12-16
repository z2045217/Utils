package com.shenglian.utils.module;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
/*
* checkbox
* */
public class Module_Checkbox extends IconTextView {


    //region
    private boolean boolCheck = false;
    private String strIcon = "{fa-check-square}";
    private String strIcon_Default = "{fa-square}";
    private ColorStateList colorText;
    private int intDefalutColor = Color.parseColor("#DDDDDD");
    //endregion

    //region
    public Module_Checkbox(Context context) {
        super(context);
        init();
    }

    public Module_Checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Module_Checkbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    //endregion

    //region init（）
    private void init() {
        this.setClickable(true);
        colorText = this.getTextColors();
        String strText = this.getText().toString();
        if (strText.startsWith("true:")) {
            boolCheck = true;
            strText = strText.substring(5);
        }
        switch (strText) {
            case "check-square":
                strIcon = "{fa-check-square}";
                strIcon_Default = "{fa-square-o}";
                break;
            case "check-square-o":
                strIcon = "{fa-check-square-o}";
                strIcon_Default = "{fa-square-o}";
                break;
            case "check-circle":
                strIcon = "{fa-check-circle}";
                strIcon_Default = "{fa-circle-o}";
                break;
            case "check-circle-o":
                strIcon = "{fa-check-circle-o}";
                strIcon_Default = "{fa-circle-o}";
                break;
        }
        this.setGravity(Gravity.CENTER);
        if (boolCheck) {
            this.setText(strIcon);
            this.setTextColor(colorText);
        } else {
            this.setText(strIcon_Default);
            this.setTextColor(intDefalutColor);
        }

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolCheck = !boolCheck;
                if (boolCheck) {
                    setText(strIcon);
                    setTextColor(colorText);
                } else {
                    setText(strIcon_Default);
                    setTextColor(intDefalutColor);
                }
            }
        });
    }
    //endregion

    //region setChecked(boolean check)
    public void setChecked(boolean check) {
        boolCheck = check;
        if (boolCheck) {
            setText(strIcon);
            setTextColor(colorText);
        } else {
            setText(strIcon_Default);
            setTextColor(intDefalutColor);
        }
    }
    //endregion

    //region getChecked()
    public boolean getChecked() {
        return boolCheck;
    }
    //endregion

    //region setOnChangeListener（）
    public void setOnChangeListener() {

    }
    //endregion
}
