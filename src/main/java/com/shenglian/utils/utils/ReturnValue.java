package com.shenglian.utils.utils;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReturnValue {
    public boolean HasError = false;
    public Object ReturnObject;
    public JSONObject ReturnJSONObject;
    public JSONArray ReturnJSONArray;
    public String ErrorCode = "";
    public int ErrorInt = -1;
    public String Message = "";
    public ContentValues ReturnHash = new ContentValues();

    public ReturnValue() {
    }
}