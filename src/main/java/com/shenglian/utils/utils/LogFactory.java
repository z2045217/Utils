package com.shenglian.utils.utils;

public class LogFactory {
	private static final String TAG = "KOC.CommonLog";
	private static LogUtils log = null;

	public static LogUtils createLog() {
		if (log == null) {
			log = new LogUtils();
		}

		log.setTag(TAG);
		return log;
	}

	public static LogUtils createLog(String tag) {
		if (log == null) {
			log = new LogUtils();
		}

		if (tag == null || tag.length() < 1) {
			log.setTag(TAG);
		} else {
			log.setTag(tag);
		}
		return log;
	}
}