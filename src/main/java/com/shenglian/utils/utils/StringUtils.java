package com.shenglian.utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringUtils {
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^[1][0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isVerifyCode(String code) {
        Pattern p = Pattern.compile("^[0-9]{4,6}$");
        Matcher m = p.matcher(code);
        return m.matches();
    }

    public static JSONObject ToJSONObject(Object value) {
        try {
            value = value.toString().substring(value.toString().indexOf("{")).replace("\r\n", "\n");
            return new JSONObject(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray ToJSONArray(Object value) {
        try {
            return new JSONArray(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isCarLicense(String code) {
        if (!code.substring(0, 1).matches("[\u4e00-\u9fa5]{1}$")) {
            return false;
        }
        if (!code.substring(1).matches("^[A-Za-z0-9]{6}$")) {
            return false;
        }
        return true;

    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static int ToInt(Object value) {
        return ToInt(value, -1);
    }

    public static int ToInt(Object value, int defaultValue) {
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static boolean ToBoolean(Object value) {
        return ToBoolean(value, false);
    }

    public static boolean ToBoolean(Object value, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(String.valueOf(value));
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

	/*
     * public static String ToDate(Date value) { return ToDate(value,
	 * "yyyy-MM-dd HH:mm:ss.SSS"); }
	 * 
	 * public static String ToDate(Date value, String format) { try { return
	 * (new SimpleDateFormat(format, new Locale("zh", "CN"))) .format(value); }
	 * catch (Exception e) { e.printStackTrace(); return null; } }
	 * 
	 * public static Date ToDate(String value) { return ToDate(value,
	 * "yyyy-MM-dd HH:mm:ss.SSS"); }
	 * 
	 * public static Date ToDate(String value, String format) { try { return
	 * (new SimpleDateFormat(format, new Locale("zh", "CN"))) .parse(value); }
	 * catch (Exception e) { e.printStackTrace(); return null; } }
	 */
}
