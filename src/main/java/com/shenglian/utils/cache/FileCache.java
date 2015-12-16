package com.shenglian.utils.cache;

import android.content.Context;

import com.shenglian.utils.utils.CommonUtils;

/*
* FileCache
* */
public class FileCache extends AbstractFileCache {

	public FileCache(Context context) {
		super(context);
	}

	//region getSavePath
	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getCacheDir() + filename;
	}
	//endregion

	//region getCacheDir()
	@Override
	public String getCacheDir() {

		if (CommonUtils.hasSDCard()) {
			return CommonUtils.getRootFilePath() + "koc.cache/files/";
		} else {
			return CommonUtils.getRootFilePath() + "koc.cache/files/";
		}
	}
	//endregion
}
