package com.shenglian.utils.cache;

import java.io.File;

import android.content.Context;
import android.util.Log;

import com.shenglian.utils.utils.FileUtils;

/*
* AbstractFileCache
* */
public abstract class AbstractFileCache {
	private String dirString;
	private static final String TAG = "KOC.AbstractFileCache";

	//region	AbstractFileCache
	public AbstractFileCache(Context context) {

		dirString = getCacheDir();
		boolean ret = FileUtils.createDirectory(dirString);
		Log.i(TAG, "FileHelper.createDirectory:" + dirString + ", ret = " + ret);
	}
	//endregion

	//region	getFile(String url)
	public File getFile(String url) {
		File f = new File(getSavePath(url));
		return f;
	}
	//endregion
	public abstract String getSavePath(String url);

	public abstract String getCacheDir();

	//region	clear（）
	public void clear() {
		FileUtils.deleteDirectory(dirString);
	}
	//endregion
}
