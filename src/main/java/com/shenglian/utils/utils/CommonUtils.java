package com.shenglian.utils.utils;

import java.io.File;
import java.security.MessageDigest;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CommonUtils {

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/";// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:
																				// /data/data/
		}
	}

	public static String getAbsolutePathFromNoStandardUri(final Uri mUri) {
		String filePath = null;

		String mUriString = mUri.toString();
		mUriString = Uri.decode(mUriString);

		String pre1 = "file:///sdcard" + File.separator;
		String pre2 = "file:///mnt/sdcard" + File.separator;

		if (mUriString.startsWith(pre1)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre1.length());
		} else if (mUriString.startsWith(pre2)) {
			filePath = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + mUriString.substring(pre2.length());
		}
		return filePath;
	}

	public static boolean checkNetState(final Context context) {
		return getNetState(context) > 0;
	}

	public static int getNetState(final Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return -1;
		}
		if (!networkInfo.isAvailable()) {
			return -1;
		}
		switch (networkInfo.getType()) {
		case ConnectivityManager.TYPE_WIFI:
			return networkInfo.getState() == NetworkInfo.State.CONNECTED ? 2
					: -1;
		case ConnectivityManager.TYPE_MOBILE:
			return networkInfo.getState() == NetworkInfo.State.CONNECTED ? 1
					: -1;
		}

		/*
		 * ConnectivityManager connectivity = (ConnectivityManager) context
		 * .getSystemService(Context.CONNECTIVITY_SERVICE); boolean netstate =
		 * false; if (connectivity != null) { NetworkInfo[] info =
		 * connectivity.getAllNetworkInfo(); if (info != null) { for (int i = 0;
		 * i < info.length; i++) { info[i].getType() ==
		 * ConnectivityManager.ty.TYPE_WIFI; if (info[i].getState() ==
		 * NetworkInfo.State.CONNECTED) { netstate = true; break; } } } } return
		 * netstate;
		 */
		return -1;
	}

	public static String getVersionName(final Context context) {
		String strVversionName = "";
		try {
			strVversionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
			if (strVversionName == null || strVversionName.length() <= 0) {
				strVversionName = "";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return strVversionName;
	}

	public static int getVersionCode(final Context context) {
		int intVersionCode = -1;
		try {
			intVersionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return intVersionCode;
	}

	public static void showToask(final Context context, final String tip) {
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(final Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(final Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static int dip2px(final Context context, final float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(final Context context, final float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static void setListViewHeightBasedOnChildren(final ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static String MD5(final String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

}